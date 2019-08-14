package ru.shestakova.bean;


import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import lombok.Data;

import static ru.shestakova.util.ConstantValues.DOUBLE_MACHINE_EPSILON;

@ManagedBean(name = "areaCheck", eager = true)
@SessionScoped
@Data
public class AreaCheckBean {

    private static final double EPS = DOUBLE_MACHINE_EPSILON;

    private List<Double> xPossibleValues = Arrays.asList(-3D, -2D, -1D, 0D, 1D, 2D, 3D, 4D, 5D);
    private double currentXValue = -3D;
    private double hiddenXValue;

    private double currentYValue = -3D;
    private double hiddenYValue;

    private double rMinValue = 1D, rMaxValue = 4D, rStepValue = .5D;
    private double currentRValue = 1D;
    private double hiddenRValue;

    private String hiddenResultValue;
    public List<Double> getxPossibleValues() {
        return xPossibleValues;
    }

    public void setxPossibleValues(List<Double> xPossibleValues) {
        this.xPossibleValues = xPossibleValues;
    }

    public double getCurrentXValue() {
        return currentXValue;
    }

    public void setCurrentXValue(double xValue) {
        this.currentXValue = xValue;
    }

    public double getCurrentYValue() {
        return currentYValue;
    }

    public void setCurrentYValue(double currentYValue) {
        this.currentYValue = currentYValue;
    }

    public double getCurrentRValue() {
        return currentRValue;
    }

    public void setCurrentRValue(double currentRValue) {
        this.currentRValue = currentRValue;
    }

    public double getHiddenXValue() {
        return hiddenXValue;
    }

    public void setHiddenXValue(double hiddenXValue) {
        this.hiddenXValue = hiddenXValue;
    }

    public double getHiddenRValue() {
        return hiddenRValue;
    }

    public void setHiddenRValue(double hiddenRValue) {
        this.hiddenRValue = hiddenRValue;
    }

    public double getHiddenYValue() {
        return hiddenYValue;
    }

    public void setHiddenYValue(double hiddenYValue) {
        this.hiddenYValue = hiddenYValue;
    }

    public static boolean validateGraph(double x, double y, double r) {
        if (Math.abs(y) <= EPS) {
            return r - Math.abs(x) >= 0;
        }

        if (Math.abs(x) <= EPS) {
            return r - Math.abs(y) >= 0;
        }

        if ((x > 0) && (y > 0)) {
            return (y + 2 * x <= r);
        } else if ((x > 0) && (y < 0)) {
            return (Math.sqrt(x * x + y * y) <= r);
        } else if ((x < 0) && (y > 0)) {
            return (x >= -r) && (y <= r);
        } else {
            return false;
        }
    }

    public boolean isValidPoint() {
        return validateGraph(currentXValue, currentYValue, currentRValue);
    }

    public String getHiddenResultValue() {
        return hiddenResultValue;
    }

    public void setHiddenResultValue(String hiddenResultValue) {
        this.hiddenResultValue = hiddenResultValue;
    }

    public void validateFromGraph() {
        hiddenResultValue = validateGraph(hiddenXValue, hiddenYValue, hiddenRValue) ? "true" : "false";
    }
    public void sliderChanged(ValueChangeEvent valueChangeEvent) {
        double oldValue = Double.parseDouble(valueChangeEvent.getOldValue().toString());
        double newValue = Double.parseDouble(valueChangeEvent.getNewValue().toString());
        this.currentRValue = rMinValue + rStepValue * newValue;

    }
}
