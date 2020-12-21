package com.github.ossvj.sf.portal;

import com.github.ossvj.sf.portal.health.HealthCheckServiceImpl;
import com.github.ossvj.sf.portal.menu.AccountMenu;
import com.github.ossvj.sf.portal.vmod.PortalModule;
import com.github.ossvj.sf.security.keycloak.IsisModuleSecurityKeycloak;
import org.apache.isis.core.config.presets.IsisPresets;
import org.apache.isis.core.runtimeservices.IsisModuleCoreRuntimeServices;
//import org.apache.isis.extensions.flyway.impl.IsisModuleExtFlywayImpl;
import org.apache.isis.persistence.jdo.datanucleus5.IsisModuleJdoDataNucleus5;
import org.apache.isis.testing.fixtures.applib.IsisModuleTestingFixturesApplib;
import org.apache.isis.testing.h2console.ui.IsisModuleTestingH2ConsoleUi;
import org.apache.isis.viewer.restfulobjects.jaxrsresteasy4.IsisModuleViewerRestfulObjectsJaxrsResteasy4;
import org.apache.isis.viewer.wicket.viewer.IsisModuleViewerWicketViewer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@Configuration
@Import({
        IsisModuleCoreRuntimeServices.class,
        IsisModuleSecurityKeycloak.class,
        IsisModuleJdoDataNucleus5.class,
        IsisModuleViewerRestfulObjectsJaxrsResteasy4.class,
        IsisModuleViewerWicketViewer.class,

        IsisModuleTestingFixturesApplib.class,
        IsisModuleTestingH2ConsoleUi.class,

        //IsisModuleExtFlywayImpl.class,

        PortalModule.class,
        HealthCheckServiceImpl.class,
        AccountMenu.class

        //ApplicationModule.class,

        // discoverable fixtures
        //DomainAppDemo.class
})
@PropertySources({
        @PropertySource(IsisPresets.DebugDiscovery),
})
public class PortalAppManifest {
}
