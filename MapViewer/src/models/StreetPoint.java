/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Comparator;

/**
 *
 * @author Madriz
 */
public class StreetPoint /*implements Comparable<StreetPoint>*/{

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public StreetPoint(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    
    private String name;
    private double x;
    private double y;

    
    public double distanceTo(StreetPoint anotherPoint) {
        double dx = x - anotherPoint.x;
        double dy = y - anotherPoint.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /*
    @Override
    public int compareTo(StreetPoint anotherPoint) {
        double distanciaThis = distanceTo(new StreetPoint("", 0, 0)); // Cambia el punto de comparación si es diferente
        double distanciaOtro = anotherPoint.distanceTo(new StreetPoint("", 0, 0)); // Cambia el punto de comparación si es diferente

        // Compara las distancias
        return Double.compare(distanciaThis, distanciaOtro);
    }*/
}

