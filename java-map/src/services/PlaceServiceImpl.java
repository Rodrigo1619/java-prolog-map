/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.ArrayList;
import java.util.List;
import models.Place;
import repositories.PlaceRepository;

/**
 *
 * @author Madriz
 */
public class PlaceServiceImpl implements PlaceService {

    private PlaceRepository repository;
    
    public PlaceServiceImpl( PlaceRepository r) {
        this.repository = r;
    }
    
    @Override
    public void printPlaces() {
        List<String> places = repository.getAllPlaces();
        
        places.stream().forEach( (p) -> {System.out.println(p);});
    }

    @Override
    public void printRoad(String from, String towards) {
        List<String> placesRoad = new ArrayList<String>();
        
        if(repository.createRoad(from, towards)) {
            placesRoad = repository.getRoad();
            
            System.out.println("El camino para ir de " + from + " hacia " + towards + "es :");
            placesRoad.stream().forEach( (p) -> {System.out.println(p);});
            return;
        }
        
        System.out.println("No se hallo camino para ir de " + from + " hacia " + towards);
    }
    
}
