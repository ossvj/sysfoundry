package com.github.ossvj.sf.portal.menu;

import org.apache.isis.applib.annotation.*;

import java.net.MalformedURLException;

@DomainService(objectType = "sf.accountMenu")
@DomainServiceLayout(menuBar = DomainServiceLayout.MenuBar.TERTIARY)
public class AccountMenu {


    @Action(semantics= SemanticsOf.SAFE)
    @ActionLayout(
            cssClassFa = "fa-user"
    )
    public java.net.URL myAccount() throws MalformedURLException {
        return new java.net.URL(getLink());
    }

    private String getLink() {
        return "http://localhost:8080/auth/realms/Example/account/";
    }
}
