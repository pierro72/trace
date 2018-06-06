package com.ex.trace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String nomRessource, double id){
        super(nomRessource+": Aucune occurence trouvé avec l'id "  + id);
    }

}