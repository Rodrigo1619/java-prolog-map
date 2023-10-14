/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import java.util.List;
import models.Place;
import models.StreetPoint;
import models.Street;

/**
 *
 * @author Madriz
 */
public interface PlaceService {
    void printPlaces();
    void printRoad(String from, String towards);
    List<Place> getAllPlaces();
    List<StreetPoint> getAllStreetPoints();
    void chop();
    List<StreetPoint> getRoad(String from, String towards);
    List<StreetPoint> getStreetRoad(Street name);
}
