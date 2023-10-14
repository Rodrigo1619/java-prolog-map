/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import models.Place;
import models.Street;
import models.StreetPoint;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

/**
 *
 * @author Madriz
 */
public class PlaceRepositoryImpl implements PlaceRepository {

    @Override
    public Boolean placeExists(String name) {
        
        Atom placeName = new Atom(name);
        Query place = 
              new Query(
                "lugar",
                placeName
              );
        
        return place.hasNext();
    }

    @Override
    public List<Place> getAllPlaces() {
     
        List<Place> placeList = new ArrayList<Place>();
        Variable P = new Variable("P");
        Variable X = new Variable("X");
        Variable Y = new Variable("Y");
        
        Query places = 
        new Query( 
          "lugar", 
          new Term[] {P, X, Y} 
        );
        
        while ( places.hasMoreSolutions() ){ 
            Map<String, Term> binding = places.nextSolution();
            placeList.add( new Place(
                    binding.get("P").toString(),
                    binding.get("X").floatValue(),
                    binding.get("Y").floatValue()
            ));  
        }
        
        return placeList;
    }

    @Override
    public Boolean createRoad(String startingPlace, String arrivalPlace) {
        
        
        Atom startingPlaceAtom = new Atom(startingPlace);
        Atom arrivalPlaceAtom = new Atom(arrivalPlace);
        
        Query place = 
              new Query(
                "ir_hacia",
                new Term[] { startingPlaceAtom, arrivalPlaceAtom}
              );
        
        return place.hasNext();
    }

    @Override
    public List<StreetPoint> getRoad() {
        List<StreetPoint> pointList = new ArrayList<StreetPoint>();
        
        Variable N = new Variable("N");
        Query points = 
        new Query( 
          "eslabon", 
          N
        );
        
        while ( points.hasMoreSolutions() ){ 
            Map<String, Term> binding1 = points.nextSolution();
            
            Query p = new Query("puntoCalle("+binding1.get("N") + ", X,Y)");
            
            Map<String, Term> binding = p.oneSolution();
            
            if(binding != null){
                pointList.add( new StreetPoint(
                    binding1.get("N").toString(),
                    binding.get("X").floatValue(),
                    binding.get("Y").floatValue()
                ));
            }
        }  
        
        return pointList;
    }
    
    
    @Override
    public StreetPoint getStreetPoint(String name) {
        
        Variable X = new Variable("X");
        Variable Y = new Variable("Y");
        Atom pointname = new Atom(name);
        Query point = 
        new Query( 
          "puntoCalle",
          new Term[] { pointname, X, Y}
        );
        
        if(point.hasSolution()){
            Map<String, Term> binding = point.nextSolution();
            return new StreetPoint(
                    pointname.toString(),
                    binding.get("X").floatValue(),
                    binding.get("Y").floatValue()
            );
        }
        
        return null;
    }

    @Override
    public List<StreetPoint> getAllPoints() {
        List<StreetPoint> pointList = new ArrayList<StreetPoint>();
        
        
        Variable N = new Variable("N");
        Variable X = new Variable("X");
        Variable Y = new Variable("Y");
        
        Query points = 
        new Query( 
          "puntoCalle",
          new Term[] { N, X, Y}
        );
        
        while ( points.hasMoreSolutions() ){ 
            Map<String, Term> binding = points.nextSolution();
            
            StreetPoint p = getStreetPoint(binding.get("X").toString());
            
            if(p != null)
                pointList.add( p);  
        }
        
        return pointList;}

    @Override
    public Boolean conectionExists(String startingPlace, String arrivalPlace) {
        
        Atom startingPlaceAtom = new Atom(startingPlace);
        Atom arrivalPlaceAtom = new Atom(arrivalPlace);
        
        Query place = 
              new Query(
                "irDesdeHacia",
                new Term[] { startingPlaceAtom, arrivalPlaceAtom}
              );
        
        return place.hasNext();
    }

    @Override
    public List<Street> getAllStreet() {
        List<Street> streetList = new ArrayList<Street>();
        Variable S = new Variable("S");
        Variable X1 = new Variable("X1");
        Variable Y1 = new Variable("Y1");
        Variable X2 = new Variable("X2");
        Variable Y2 = new Variable("Y2");
        
        Query places = 
        new Query( 
          "calle", 
          new Term[] {S, X1, Y1, X2, Y2} 
        );
        
        while ( places.hasMoreSolutions() ){ 
            Map<String, Term> binding = places.nextSolution();
            streetList.add(
                    new Street (
                        binding.get("S").toString(),
                        binding.get("X1").floatValue(),
                        binding.get("X2").floatValue(),
                        binding.get("Y1").floatValue(),
                        binding.get("Y2").floatValue()
                    )
            );  
        }
        
        return streetList;
    }

    @Override
    public void chopStreet() {
        
        Street[] streetList = this.getAllStreet().toArray(new Street[0]);
        Place[] placeList = this.getAllPlaces().toArray(new Place[0]);
        
        int n = streetList.length;
        int m = placeList.length;
        
        StreetPoint[][] matriz = new StreetPoint[n][n+m+1];
        
        
        for (int i = 0; i < m; i++) {
            int index = 0;
            StreetPoint nearestPoint = streetList[0].nearestPoint(placeList[i]);
            double distance = nearestPoint.distanceTo(placeList[i]);
            
            for (int j = 1; j < n; j++) {
                StreetPoint temp = streetList[j].nearestPoint(placeList[i]);
                
                if(temp != null){
                    if(temp.distanceTo(placeList[i]) < distance){
                        nearestPoint = temp;
                        index = j;
                        distance = temp.distanceTo(placeList[i]);
                    }
                }
                
            }
                if(i==35)
                    i=35;
                
            matriz[index][n+i] = nearestPoint;
            createConnection(nearestPoint.getName(), placeList[i].getName());
            createConnection(placeList[i].getName(), nearestPoint.getName());
        }
        
        for (int i = 0; i < n; i++) {
            
            matriz[i][i] = streetList[i].getInit();
            
            for (int j = 0; j < n; j++) {
                if( i < j)
                    matriz[i][j] = streetList[i].getIntersection( streetList[j]);
                
                if( i > j)
                    matriz[i][j] = matriz[j][i];
            }
            
            matriz[i][n+m] = streetList[i].getEnd();
        }
        
        
        for (int i = 0; i < n; i++) {
            Arrays.sort( matriz[i], new PointCmp(streetList[i].getInit()));
        }
        
        for (int i = 0; i < n; i++){
  
            insertPoint(matriz[i][0]);
            //createRelation(streetList[i].getName(), matriz[i][0].getName());
            
            for (int j = 1; j < (n+m+1); j++){
                if(insertPoint(matriz[i][j])){
                    //TODO: Hay que ver como optimizar que un mismo punto(intersección) no se agrege varias veces por la misma calle.

                    //createConnection(matriz[i][j-1].getName(), matriz[i][j].getName());
                    //createRelation(streetList[i].getName(), matriz[i][j].getName());
                }
            }
        }
        
        Query tell = new Query("tell('puntos.pl')");
        tell.hasSolution();
        tell = new Query("listing(lugar)");
        tell.hasSolution();
        tell = new Query("listing(calle)");
        tell.hasSolution();
        tell = new Query("listing(puntoCalle)");
        tell.hasSolution();
        tell = new Query("listing(perteneceA)");
        tell.hasSolution();
        tell = new Query("listing(irDesdeHacia)");
        tell.hasSolution();
        tell = new Query("listing(ir_hacia)");
        tell.hasSolution();
        tell = new Query("listing(ir_hacia_rec)");
        tell.hasSolution();
        tell = new Query("told()");
        tell.hasSolution(); 
    }
    
    public Boolean insertPoint( StreetPoint point){
        
        if(point == null)
            return false;
        
        Query exists = 
            new Query( 
                "puntoCalle("+point.getName() + ",_,_)" 
            );
        
        if(exists.hasSolution())
            return true;
        
        Query insertPoint = 
            new Query( 
                "assert(puntoCalle("+
                        point.getName() + ","+
                        point.getX() + ","+
                        point.getY()+
                "))" 
            );
        return insertPoint.hasSolution();
    }
    
    public Boolean createConnection(String name1, String name2) {
        
        if(name1.equals(name2))
            return false;
        
        
        Query exists = new Query( "irDesdeHacia(" + name1 + "," + name2 + ")" );
        
        if(exists.hasSolution())
            return true;
        
        Query createConnection = 
                new Query( 
                    "assert(irDesdeHacia("+
                            name1+","+
                            name2+
                    "))" 
        );
        return createConnection.hasSolution();
    }
    
    public Boolean createRelation(String name1, String name2) {
        
        if(name1.equals(name2))
            return false;
        
        Query exists = new Query( "perteneceA(" + name1 + "," + name2 + ")" );
        
        if(exists.hasSolution())
            return true;
         
        Query createConnection = 
                new Query( 
                    "assert(perteneceA("+
                            name1+","+
                            name2+
                    "))" 
        );
        return createConnection.hasSolution();
    }
}

class PointCmp implements Comparator<StreetPoint> {
        
        StreetPoint init;

        public PointCmp(StreetPoint init) {
            this.init = init;
        }
        
        
        public int compare(StreetPoint a, StreetPoint b) {
            
            if (a == null && b == null) {
                return 0; // Ambos son nulos, son iguales
            } else if (a == null) {
                return +1; // a es nulo, b es no nulo, a es mayor
            } else if (b == null) {
                return -1; // a es no nulo, b es nulo, b es mayor
            };
            
            double d1 = a.distanceTo(init);
            double d2 = b.distanceTo(init);
            
            return (d1 < d2) ? -1 : (d1 > d2) ? 1 : 0;
    }
}
