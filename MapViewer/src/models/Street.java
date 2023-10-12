/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Madriz
 */
public class Street {

    public StreetPoint getInit() {
        return init;
    }

    public StreetPoint getEnd() {
        return end;
    }

    public String getName() {
        return name;
    }

    public double getX1() {
        return init.getX();
    }

    public double getX2() {
        return end.getX();
    }

    public double getY1() {
        return init.getY();
    }

    public double getY2() {
        return end.getY();
    }

    public Street(String name, double x1, double x2, double y1, double y2) {
        this.name = name;
        this.init = new StreetPoint(name+"_Init", x1, y1);
        this.end = new StreetPoint(name+"_End", x2, y2);
    }
    
    
    private StreetPoint init;
    private StreetPoint end;
    private String name;

    public StreetPoint getIntersection( Street s2){
        
        
        if( init.distanceTo(s2.init) == 0 ){
            return init;
        }
        
        if( init.distanceTo(s2.end) == 0 ){
            return init;
        }
        
        if( end.distanceTo(s2.init) == 0 ){
            return end;
        }
        
        if( end.distanceTo(s2.end) == 0 ){
            return end;
        }
     
        double S1X1 = this.getX1(), S1X2 = this.getX2(), S1Y1 = this.getY1(), S1Y2 = this.getY2();
        double S2X1 = s2.getX1(), S2X2 = s2.getX2(), S2Y1 = s2.getY1(), S2Y2 = s2.getY2();

        double m1 = (S1Y2-S1Y1)/(S1X2-S1X1);
        double b1 = -m1*S1X2 + S1Y2;

        double m2 = (S2Y2-S2Y1)/(S2X2-S2X1);
        double b2 = -m2*S2X2 + S2Y2;

        double x = (b2-b1)/(m1-m2);
        double y = m1*x + b1;

        if( this.pointBelongs(x, y) && s2.pointBelongs(x, y)) {

            return new StreetPoint(
                this.getName() + "X" + s2.getName(),
                x,
                y
            );
        }

        return null;
    }

    public StreetPoint nearestPoint(Place pl) {

        double m = (end.getY()-init.getY())/(end.getX()-init.getX());
        double b1 = -m*end.getX() + end.getY();
        double b2 = m*pl.getX() + pl.getY();

        double x = (b2-b1)/(2*m);
        double y = m*x + b1;
                
        if( this.pointBelongs(x, y)) {

            return new StreetPoint(
                this.getName() + "X" + pl.getName(),
                x,
                y
            );
        }

        return (init.distanceTo(pl)<end.distanceTo(pl)) ?  init : end;
    }
    
    private Boolean pointBelongs(double x, double y) {
        if( between(x, init.getX(), end.getX()) && between(y, init.getY(), end.getY())){
            return true;
        }
        return false;
    }
    
    private Boolean between(double number, double n1, double n2){
        double min = (n1 < n2) ? n1 : n2;
        double max = (n1 > n2) ? n1 : n2;
        
        return (number >= min && number <= max );
    }
}
