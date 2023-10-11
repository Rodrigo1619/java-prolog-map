/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.pointlist;

import com.graphhopper.util.PointList;

/**
 *
 * @author Zepeda22
 */
public final class PointListCustom extends PointList {
    private double Latitud;
    private double Longitud;

    public double getLatitud() {
        return Latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLatitud(double Latitud) {
        this.Latitud = Latitud;
    }

    public void setLongitud(double Longitud) {
        this.Longitud = Longitud;
    }

    public PointListCustom(double Latitud, double Longitud) {
        this.Latitud = Latitud;
        this.Longitud = Longitud;
        add(Latitud, Longitud);
    }


    @Override
    public void add(double lat, double lon) {
        super.add(lat, lon); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    
    
    
    
}
