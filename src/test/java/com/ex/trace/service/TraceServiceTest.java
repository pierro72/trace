package com.ex.trace.service;

import com.ex.trace.service.TraceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TraceServiceTest {

    @Autowired
    TraceService service;

    @Test
    public void ObtenirTrace_GPS_OK (){
        System.out.println("test obtenir trace a porté ");
    }
}
