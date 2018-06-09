package com.ex.trace.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TraceNotProxiException extends RuntimeException{


    public final static String ERR1 = "Impossible de commenter";

    public final static String ERR2 = "Impossible d'afficher les commentaires";

    public final static String ERR3 = "Impossible d'afficher la trace";

    public TraceNotProxiException( long idTrace, String message){
        super("Trace "+ idTrace+ " trop éloigné. "+message);
    }
}
