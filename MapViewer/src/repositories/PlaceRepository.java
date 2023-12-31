/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositories;

import models.Place;
import java.util.List;
import models.Street;
import models.StreetPoint;

/**
 *
 * @author Madriz
 */
public interface PlaceRepository {
   Boolean placeExists(String name);
   
   List<Place> getAllPlaces();
   
   Boolean createRoad(String startingPlace, String arrivalPlace);
   List<StreetPoint> getRoad();
   
   StreetPoint getStreetPoint(String name);
   
   List<StreetPoint> getAllPoints();
   
   Boolean conectionExists(String startingPlace, String arrivalPlace);
   List<Street> getAllStreet();
   
   void chopStreet();
}
