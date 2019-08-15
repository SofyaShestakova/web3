package ru.shestakova.bean;


import static ru.shestakova.util.ConstantValues.DOUBLE_MACHINE_EPSILON;

import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "areaCheck", eager = true)
@SessionScoped
@Data
public class AreaCheckBean {

  private static final Logger logger = LoggerFactory.getLogger(AreaCheckBean.class);
  private static final double EPS = DOUBLE_MACHINE_EPSILON;

  private final List<Double> xPossibleValues =
      Arrays.asList(-3D, -2D, -1D, 0D, 1D, 2D, 3D, 4D, 5D);
  private final double rMinValue = 1D, rMaxValue = 4D, rStepValue = .5D;

  private double currentXValue = -3D;
  private double currentYValue = -3D;
  private double currentRValue = 1D;

  private double hiddenXValue;
  private double hiddenYValue;
  private double hiddenRValue;
  private String hiddenResultValue;

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

  public void validateFromGraph() {
    this.hiddenResultValue =
        validateGraph(hiddenXValue, hiddenYValue, hiddenRValue) ? "true" : "false";
  }

  public void sliderChanged(ValueChangeEvent valueChangeEvent) {
    double oldValue = Double.parseDouble(valueChangeEvent.getOldValue().toString());
    double newValue = Double.parseDouble(valueChangeEvent.getNewValue().toString());

    this.currentRValue = rMinValue + rStepValue * newValue;

    logger.debug(
        "Old: {}, New: {}, Current: {}",
        oldValue,
        newValue,
        currentRValue
    );
  }
}
