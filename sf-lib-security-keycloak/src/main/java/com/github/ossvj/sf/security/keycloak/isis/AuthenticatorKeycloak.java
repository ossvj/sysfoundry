package com.github.ossvj.sf.security.keycloak.isis;

import lombok.extern.slf4j.Slf4j;
import org.apache.isis.applib.annotation.OrderPrecedence;
import org.apache.isis.applib.services.user.UserMemento;
import org.apache.isis.core.security.authentication.Authentication;
import org.apache.isis.core.security.authentication.AuthenticationRequest;
import org.apache.isis.core.security.authentication.standard.Authenticator;
import org.apache.isis.core.security.authentication.standard.SimpleAuthentication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Service
@Named("sfSecurityKeycloak.AuthenticatorKeycloak")
@Order(OrderPrecedence.EARLY)
@Qualifier("Keycloak")
@Singleton
@Slf4j
public class AuthenticatorKeycloak implements Authenticator {

    @Override
    public final boolean canAuthenticate(final Class<? extends AuthenticationRequest> authenticationRequestClass) {
        return true;
    }

    @Override
    public Authentication authenticate(final AuthenticationRequest request, final String code) {
        AuthenticatedWebSession unAuthenticatedSession = AuthenticatedWebSession.get();
        AccessToken.Access accessInfo = (AccessToken.Access)unAuthenticatedSession.getAttribute("keycloak-accessinfo");
        String issuer = (String)unAuthenticatedSession.getAttribute("keycloak-issuer");
        UserMemento userMemento = UserMemento.ofNameAndRoleNames(request.getName(), getRoles(accessInfo.getRoles()));
        SimpleAuthentication simpleAuthentication = SimpleAuthentication.of(userMemento,code);

        return simpleAuthentication;
    }

    private Stream<String> getRoles(Set<String> existingRoles) {
        Set<String> defaultRoles = new HashSet<>();
        defaultRoles.add("org.apache.isis.viewer.wicket.roles.USER");
        defaultRoles.addAll(existingRoles);
        return defaultRoles.stream();
    }

    @Override
    public void logout(final Authentication session) {
        log.info("About to logout of session "+session.getUserName());

        //SimpleSession actualSession = (SimpleSession)session;
        //AuthenticatedWebSession.get().invalidateNow();
    }

}
