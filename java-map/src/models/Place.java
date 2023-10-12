/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Madriz
 */
public class Place implements Point{

    private String name;
    private Float x_coordinate;
    private Float y_coordinate;
    
    public Place(String name, Float x_coordinate, Float y_coordinate) {
        this.name = name;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public Float getX_coordinate() {
        return x_coordinate;
    }

    public Float getY_coordinate() {
        return y_coordinate;
    }
    
    @Override
    public double getX() {
        return x_coordinate;
    }

    @Override
    public double getY() {
        return y_coordinate;
    }
    
    public String getName(){
        return this.name;
    }
}
