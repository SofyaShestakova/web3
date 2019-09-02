package ru.shestakova.bean;


import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import org.primefaces.event.SlideEndEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shestakova.util.ConstantValues;

@ManagedBean(name = "areaCheck", eager = true)
@SessionScoped
@Data
public class AreaCheckBean {

  private static final Logger logger = LoggerFactory.getLogger(AreaCheckBean.class);
  private static final double EPS = ConstantValues.getMachineEpsilon();

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
    // x < 0
    if (x < -EPS) {
      if (y >= 0) {
        // y >= 0 and y <= x + 0.5r
        return (y - (x + 0.5 * r) <= -EPS);
      } else {
        // y < 0
        return false;
      }
    } else if (Math.abs(x) <= EPS) {
      // x == 0
      // -r <= y <= r/2
      return (r / 2 - r >= -EPS) && (y + r >= -EPS);
    } else {
      // x > 0
      if (y > EPS) {
        // y > 0 && (x^2 + y^2 <= (r/2)^2)
        return y * y + x * x - r * r / 4 <= -EPS;
      } else {
        // y <= 0 && (x <= r/2) && (y >= -r)
        return (x - r / 2 <= EPS) && (y + r >= -EPS);
      }
    }
  }

  public boolean isValidPoint() {
    return validateGraph(currentXValue, currentYValue, currentRValue);
  }

  public void validateFromGraph() {
    logger.debug(
        "validateFromGraph(): Validating X:{}, Y:{}, R:{}", hiddenXValue, hiddenYValue, hiddenRValue
    );
    this.hiddenResultValue =
        validateGraph(hiddenXValue, hiddenYValue, hiddenRValue) ? "true" : "false";
  }

  public void onSlideEnd(SlideEndEvent valueChangeEvent) {
    double value = valueChangeEvent.getValue();

    logger.debug(
        "onSlideEnd(): Old: {}, Current: {}",
        currentRValue,
        value
    );

    this.currentRValue = value;
  }
}
