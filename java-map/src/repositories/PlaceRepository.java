/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositories;

import models.Place;
import java.util.List;

/**
 *
 * @author Madriz
 */
public interface PlaceRepository {
   Boolean placeExists(String name);
   List<String> getAllPlaces();
   Boolean createRoad(String startingPlace, String arrivalPlace);
   List<String> getRoad();
}
