package com.github.ossvj.sf.portal.vmod;

import lombok.Getter;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.user.ResourceMemento;
import org.apache.isis.applib.services.user.UserMemento;
import org.apache.isis.applib.services.user.UserService;
import org.apache.isis.applib.value.Markup;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DomainObject(nature = Nature.VIEW_MODEL,
        objectType = "sf.portal.PortalHomePageViewModel"
)
@DomainObjectLayout
@HomePage
public class PortalHomepageViewModel {

    public static String urlFormat = "<a href=\"%s\" target=\"_blank\">Open</a>";

    @Inject
    private UserService userService;

    @Value("${spring.application.name}")
    private String appName;


    public List<AppObject> getApps(){
        Optional<UserMemento> optionalCurrentUser = userService.currentUser();

        List<AppObject> accessibleApps = new ArrayList<>();
        //TODO: Probably think about providing support for Apps accessible as unauthenticated user

        if(optionalCurrentUser.isPresent()){
            UserMemento userMementoObj = optionalCurrentUser.get();
            List<ResourceMemento> resources = userMementoObj.getResources();
            for (ResourceMemento resource : resources) {
                String appName = resource.getName();
                String description = resource.getDescription();
                String status = "Available";
                String urlMarkupText = String.format(urlFormat,"http://localhost:9091/wicket");
                AppObject appObject = new AppObject(appName,description,status,urlMarkupText);
                //appObject.setAppName(appName);
                //appObject.setAppDescription(description);
                //appObject.setUrl();
                accessibleApps.add(appObject);
                //accessibleApps.add(new AppObject(appName,description,new Markup(String.format(urlFormat,"http://www.google.com"))));
            }

        }

        return accessibleApps;
    }

    public String title(){
        return String.format("%s - App Dashboard",appName);
    }
}
