package ru.shestakova.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "request")
@Data
public class Request {

    public Request() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "x")
    private double x;

    @Column(name = "y")
    private double y;

    @Column(name = "r")
    private double r;

    @Column(name = "result")
    private boolean result;

    public Request(double x, double y, double r, boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }
}
