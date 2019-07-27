package ru.shestakova.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import lombok.Data;

@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
@Data
public class NavigationController {

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
            return "home";
        }
        if (pageId.equals("1")) {
            return "home";
        } else if (pageId.equals("2")) {
            return "start";
        } else {
            return "home";
        }
    }
}

