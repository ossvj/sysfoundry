package com.github.ossvj.sf.security.keycloak;

import com.github.ossvj.sf.security.keycloak.isis.AuthenticatorKeycloak;
import com.github.ossvj.sf.security.keycloak.isis.AuthorizerKeycloak;
import com.github.ossvj.sf.security.keycloak.webmodule.IsisKeycloakConfigResolver;
import com.github.ossvj.sf.security.keycloak.webmodule.WebModuleKeycloak;
import org.apache.isis.core.runtimeservices.IsisModuleCoreRuntimeServices;
import org.apache.isis.core.webapp.IsisModuleCoreWebapp;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.InputStream;

@Configuration
@Import({
        // modules
        IsisModuleCoreRuntimeServices.class,
        IsisModuleCoreWebapp.class,

        // @Service's
        AuthenticatorKeycloak.class,
        AuthorizerKeycloak.class,

        //The below module registers the necessary servlet filters
        WebModuleKeycloak.class,


})
public class IsisModuleSecurityKeycloak {

    public static KeycloakDeployment globalKeycloakDeployment;

    @Bean
    public KeycloakDeployment keycloakConfig(){
        InputStream inputStream = IsisKeycloakConfigResolver.class.getResourceAsStream("/config/keycloak.json");
        KeycloakDeployment keycloakDeployment = KeycloakDeploymentBuilder.build(inputStream);
        globalKeycloakDeployment = keycloakDeployment;
        return keycloakDeployment;
    }
}
