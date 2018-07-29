package com.ex.trace.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageNotProxiException extends RuntimeException{

    private final static String PROXI       = "Assurez vous d'être situé à proximité";

    public final static String ERR_TRACE_1 = "Impossible d'obtenir la trace. " + PROXI;
    public final static String ERR_TRACE_2 = "Impossible de commenter cette trace. " + PROXI;
    public final static String ERR_TRACE_3 = "Impossible d'obtenir les commentaires de cette trace. " + PROXI;
    public final static String ERR_TRACE_4 = "Impossible de signaler cette trace. " + PROXI;
    public final static String ERR_TRACE_5 = "Impossible de recommander cette trace. " + PROXI;

    public final static String ERR_COMMENTAIRE_1 = "Impossible d'obtenir le commentaire. " + PROXI;
    public final static String ERR_COMMENTAIRE_2 = "Impossible de signaler ce commentaire. " + PROXI;
    public final static String ERR_COMMENTAIRE_3 = "Impossible de recommander ce commentaire. " + PROXI;


    public MessageNotProxiException(String nomRessource, long id, String message){
        super( nomRessource +" n°"+ id+ " trop éloigné. "+message);

    }
}
