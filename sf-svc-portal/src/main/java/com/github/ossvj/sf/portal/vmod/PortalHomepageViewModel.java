package com.github.ossvj.sf.portal.vmod;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.user.ResourceMemento;
import org.apache.isis.applib.services.user.UserMemento;
import org.apache.isis.applib.services.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Inject
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String appName;

    @lombok.Value
    private static class ServiceInfo{
        private String name;
        private String location;
        private String status;
    }

    public List<AppObject> getApps(){
        Optional<UserMemento> optionalCurrentUser = userService.currentUser();

        List<AppObject> accessibleApps = new ArrayList<>();
        //TODO: Probably think about providing support for Apps accessible as unauthenticated user

        if(optionalCurrentUser.isPresent()){
            UserMemento userMementoObj = optionalCurrentUser.get();
            List<ResourceMemento> resources = userMementoObj.getResources();
            for (ResourceMemento resource : resources) {
                String appName = resource.getName();
                if(!this.appName.equalsIgnoreCase(appName)) { //skip adding this app to the list
                    String description = resource.getDescription();
                    ServiceInfo serviceInfo = getServiceInfo(appName);
                    String urlMarkupText = String.format(urlFormat, serviceInfo.location);
                    AppObject appObject = new AppObject(appName, description, serviceInfo.status, urlMarkupText);
                    //appObject.setAppName(appName);
                    //appObject.setAppDescription(description);
                    //appObject.setUrl();
                    accessibleApps.add(appObject);
                }
                //accessibleApps.add(new AppObject(appName,description,new Markup(String.format(urlFormat,"http://www.google.com"))));
            }

        }

        return accessibleApps;
    }

    private ServiceInfo getServiceInfo(String appName) {
        String status = "Unavailable";
        String location = "Unknown";
        List<ServiceInstance> appInstances = discoveryClient.getInstances(appName);
        if(appInstances != null && appInstances.size() > 0){
            //if there is more than one instance available and healthy we can assume
            //that the app is available
            status = "Available";
            for (ServiceInstance appInstance : appInstances) {
                Map<String, String> metadata = appInstance.getMetadata();
                if(metadata.containsKey("external-app-uri")){
                    location = metadata.get("external-app-uri");
                }
            }

        }
        return new ServiceInfo(appName,location,status);
    }

    public String title(){
        return String.format("%s - App Dashboard",appName);
    }
}
