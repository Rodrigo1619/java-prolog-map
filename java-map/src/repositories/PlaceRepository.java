/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositories;

import models.Place;
import java.util.List;
import models.Street;

/**
 *
 * @author Madriz
 */
public interface PlaceRepository {
   Boolean placeExists(String name);
   
   List<Place> getAllPlaces();
   
   Boolean createRoad(String startingPlace, String arrivalPlace);
   List<String> getRoad();
   
   Boolean conectionExists(String startingPlace, String arrivalPlace);
   List<Street> getAllStreet();
   
   void chopStreet();
}
