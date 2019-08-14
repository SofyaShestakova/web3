package ru.shestakova.util;

import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import lombok.Data;

@SuppressWarnings("ALL")
@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
@Data
public class NavigationController extends NavigationHandler {


    public String showIndex() {
        return "index";
    }

    public String showPlot() {
        return "plot";
    }

    public void handleNavigation(FacesContext facesContext, String s, String s1) {

    }
}

