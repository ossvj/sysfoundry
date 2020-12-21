package com.github.ossvj.sf.security.keycloak;

import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public class Config extends AdapterConfig {

}
