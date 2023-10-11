/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.ArrayList;
import java.util.List;
import models.Place;
import models.StreetPoint;
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
        List<Place> places = repository.getAllPlaces();
        
        places.stream().forEach( (p) -> {
            System.out.print("lugar: " + p.getName());
            System.out.print("coordenada X: " + p.getX_coordinate());
            System.out.println("coordenada Y: " + p.getY_coordinate());
        });
    }

    @Override
    public void printRoad(String from, String towards) {
        List<StreetPoint> placesRoad = new ArrayList<StreetPoint>();
        
        if(repository.conectionExists(from, towards)){
            System.out.println("El camino para ir de " + from + " hacia " + towards + " es directo");
            return;
        }
        
        if(repository.createRoad(from, towards)) {
            placesRoad = repository.getRoad();
            
            //En el caso concreto de imprimir en consola, prolog al parecer si imprime automaticamente. Por eso se ve la misma lista dos veces
            //Igual, dejo el codigo asi para que sirva de guia para algo que no sea imprimir consola xD
            System.out.println("El camino para ir de " + from + " hacia " + towards + " es :");
            placesRoad.stream().forEach( (p) -> {System.out.println(p.getName());});
            return;
        }
        
        System.out.println("No se hallo camino para ir de " + from + " hacia " + towards);
    }

    @Override
    public List<Place> getAllPlaces() {
        return repository.getAllPlaces();
    }

    @Override
    public void chop() {
        repository.chopStreet();
    }

    @Override
    public List<StreetPoint> getAllStreetPoints() {
        
        return repository.getAllPoints();
    }

    @Override
    public List<StreetPoint> getRoad(String from, String towards) {
        repository.createRoad(from, towards);
        
        return repository.getRoad();
    }

    
}
