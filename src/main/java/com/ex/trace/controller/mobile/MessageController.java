package com.ex.trace.controller.mobile;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

