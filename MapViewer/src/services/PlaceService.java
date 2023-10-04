/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import java.util.List;
import models.Place;

/**
 *
 * @author Madriz
 */
public interface PlaceService {
    void printPlaces();
    void printRoad(String from, String towards);
    List<Place> getAllPlaces();
}
