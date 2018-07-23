package com.ex.trace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class MessageController {

    @PreAuthorize( "hasRole('USER')" )
    public abstract ResponseEntity<Void> ajouterRecommandation(@PathVariable Long id, @RequestParam double positionX, @RequestParam double positionY);

    @PreAuthorize( "hasRole('USER')" )
    public abstract ResponseEntity<Void> supprimerRecommandation( @PathVariable Long id, @RequestParam double positionX, @RequestParam double positionY);

    @PreAuthorize( "hasRole('USER')" )
    public abstract ResponseEntity<Void> ajouterSignalement(@PathVariable Long id, @RequestParam double positionX, @RequestParam double positionY);

    @PreAuthorize( "hasRole('USER')" )
    public abstract  ResponseEntity<Void>  supprimerSignalement(@PathVariable Long id, @RequestParam double positionX, @RequestParam double positionY);

}

