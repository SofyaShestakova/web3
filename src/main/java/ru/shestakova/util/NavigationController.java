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
public class NavigationController  extends NavigationHandler {

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    // todo refactor
    //this managed property will read value from request parameter pageId
    @ManagedProperty(value = "#{param.pageId}")
    private String pageId = "2";

    //condional navigation based on pageId
    //if pageId is 1 show page1.xhtml,
    //if pageId is 2 show page2.xhtml
    //else show home.xhtml

    public String showPage() {
        if (pageId == null) {
            System.out.println("null");
            return "home";
        }
        if (pageId.equals("1")) {
            System.out.println("1.home");
            return "home";
        } else if (pageId.equals("2")) {
            System.out.println("2.index");
            return "index";
        } else {
            System.out.println("else.home");
            return "home";
        }
    }

    public void handleNavigation(FacesContext facesContext, String s, String s1) {

    }
}

