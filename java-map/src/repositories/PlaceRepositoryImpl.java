/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.Place;
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
    public List<String> getAllPlaces() {
     
        List<String> placeList = new ArrayList<String>();
        Variable X = new Variable("X");
        Query places = 
        new Query( 
          "lugar", 
          X 
        );
        
        while ( places.hasMoreSolutions() ){ 
            Map<String, Term> binding = places.nextSolution();
            placeList.add( binding.get("X").toString());  
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

    
}
