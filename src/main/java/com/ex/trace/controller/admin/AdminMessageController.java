package com.ex.trace.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;


public abstract class AdminMessageController {

    @PreAuthorize( "hasRole('admin')" )
    public abstract ResponseEntity<Void> supprimer ( Long id);

    @PreAuthorize( "hasRole('admin')" )
    public abstract ResponseEntity<Void> valider( Long id, boolean estVerifier);


}

