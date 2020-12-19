package com.github.ossvj.sf.portal.vmod;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.value.Markup;

@DomainObject(
        nature= Nature.VIEW_MODEL,
        objectType = "sf.portal.AppObject"
)
@DomainObjectLayout
public class AppObject {


    /*public AppObject(String name,String description,Markup url){
        this.appName = name;
        this.appDescription = description;
        this.url = url;

    }*/


    @Title
    @Getter @Setter
    @MemberOrder(sequence = "1")
    private String appName;

    @Getter @Setter
    @MemberOrder(sequence = "2")
    private String appDescription;

    @Getter @Setter
    @MemberOrder(sequence = "2.5")
    private String status;

    @Getter @Setter
    @Property(hidden = Where.EVERYWHERE)
    private String url;

    public AppObject(String appName,
                     String description,
                     String status,
                     String urlMarkupText) {
        this.appName = appName;
        this.appDescription = description;
        this.status = status;
        this.url = urlMarkupText;
    }

    public AppObject(){
        this("Unknown","Unknown","Unknown","");
    }

    @Property(editing=Editing.DISABLED)
    @MemberOrder(sequence = "3")
    public Markup getLocation(){
        return new Markup(url);
    }

}
