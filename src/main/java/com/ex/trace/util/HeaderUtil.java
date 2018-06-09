package com.ex.trace.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private HeaderUtil() {
    }

    public static HttpHeaders alert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("alert", message);
        headers.add("params", param);
        return headers;
    }

    public static HttpHeaders ajouterAlert(String entityName, String param) {
        return alert("Un(e)" + entityName + " est créer avec l'identifiant " + param, param);
    }

    public static HttpHeaders editerAlert(String entityName, String param) {
        return alert("Un(e) " + entityName + " est mise à jours avec l'identifiant " + param, param);
    }

    public static HttpHeaders supprimerAlert(String entityName, String param) {
        return alert("Un(e) " + entityName + " est supprimé avec l'identifiant  " + param, param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        log.error("Entity processing failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-jhipsterDemoApp-error", defaultMessage);
        headers.add("X-jhipsterDemoApp-params", entityName);
        return headers;
    }
}
