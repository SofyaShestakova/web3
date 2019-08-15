package ru.shestakova.bean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ApplicationScoped
@ManagedBean(name = "navigationController", eager = true)
public class NavigationController {

  public String showIndexPage() {
    return "index";
  }

  public String showPlotPage() {
    return "plot";
  }
}

