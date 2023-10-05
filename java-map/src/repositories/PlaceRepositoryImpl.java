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
    public List<String> getRoad() {
        List<String> roadList = new ArrayList<String>();
        
        Variable X = new Variable("X");
        Query places = 
        new Query( 
          "eslabon", 
          X 
        );
        
        while ( places.hasMoreSolutions() ){ 
            Map<String, Term> binding = places.nextSolution();
            roadList.add( binding.get("X").toString());  
        }
        
        return roadList;
    }

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
        
        int n = streetList.length;
        n=1; //TODO: Quitar esto despues
        StreetPoint[][] matriz = new StreetPoint[n][n+1];
        
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if( i == j){
                    matriz[i][j] = new StreetPoint(
                    streetList[i].getName()+"_End",
                    streetList[i].getX2(),
                    streetList[i].getY2()
                );
                }
                
                if( i < j){
                    matriz[i][j] = getIntersection( streetList[i], streetList[j]);
                }
                if( i > j){
                    matriz[i][j] = matriz[j][i];
                }
            }
        }
        
        for (int i = 0; i < n; i++) {
            StreetPoint init = new StreetPoint(
                    streetList[i].getName()+"_Init",
                    streetList[i].getX1(),
                    streetList[i].getY1()
            );
            
            matriz[i][n] = init;
            Arrays.sort( matriz[i], new PointCmp(init));
            
            init = null;
        }
        
        for (int i = 0; i < n; i++){
            
            insertPoint(streetList[i].getName(), matriz[i][0]);
            
            for (int j = 1; j < n; j++){
                insertPoint(streetList[i].getName(), matriz[i][j]);
                //TODO: Hay que ver como optimizar que un mismo punto(intersección) no se agrege varias veces por la misma calle.
                
                Query createConection = 
                        new Query( 
                            "\"assert(irDesdeHacia("+
                                    matriz[i][j-1].getName()+","+
                                    matriz[i][j].getName()+","+
                            "))\"" 
                );
            }
        }
        
        Query tell = new Query("\"tell(puntos.pl)\"");
        tell.hasSolution();
        tell = new Query("\"listing(puntoCalle)\"");
        tell.hasSolution();
        tell = new Query("\"listing(irDesdeHacia)\"");
        tell.hasSolution();
        tell = new Query("\"told()\"");
        tell.hasSolution(); 
    }
    
    public Boolean insertPoint(String street, StreetPoint point){
        Query insertPoint = 
            new Query( 
                "\"assert(puntoCalle("+
                        street +","+
                        point.getName() + ","+
                        point.getX() + ","+
                        point.getY() + ","+
                "))\"" 
            );
        return insertPoint.hasSolution();
    }
    
    private StreetPoint getIntersection(Street s1, Street s2){      
            double S1X1 = s1.getX1(), S1X2 = s1.getX2(), S1Y1 = s1.getY1(), S1Y2 = s1.getY2();
            double S2X1 = s2.getX1(), S2X2 = s2.getX2(), S2Y1 = s2.getY1(), S2Y2 = s2.getY2();
        
            double m1 = (S1Y2-S1Y1)/(S1X2-S1X1);
            double b1 = -(m1*S1X2 + S1Y2);
            
            double m2 = (S2Y2-S2Y1)/(S2X2-S2X1);
            double b2 = -(m2*S2X2 + S2Y2);
            
            double x = (b2-b1)/(m1-m1);
            double y = m1*x + b1;
            
            if( between(x, S1X1, S1X2) && between(x, S2X1, S2X2)
                && between(y, S1Y1, S1Y2) && between(y, S2Y1, S2Y2)) {
                return new StreetPoint(
                        s1.getName() + "X" + s2.getName(),
                        x,
                        y
                );
            }
            
            return null;
        }
    
    private Boolean between(double number, double n1, double n2){
        double min = (n1 < n2) ? n1 : n2;
        double max = (n1 > n2) ? n1 : n2;
        
        return (number > min && number < max );
    }
}

class PointCmp implements Comparator<StreetPoint> {
        
        StreetPoint init;

        public PointCmp(StreetPoint init) {
            this.init = init;
        }
        
        
        public int compare(StreetPoint a, StreetPoint b) {
            double d1 = a.distanceTo(init);
            double d2 = b.distanceTo(init);
            
            return (d1 < d2) ? -1 : (d1 > d2) ? 1 : 0;
    }
}
