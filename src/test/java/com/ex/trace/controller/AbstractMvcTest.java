package com.ex.trace.controller;

import com.ex.trace.TraceApplication;
import com.ex.trace.security.JwtRequeteAuthentification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TraceApplication.class})
@ActiveProfiles("test")
@Ignore
public class AbstractMvcTest {

    private static final String BASEURL = "/api";

    protected static final String MOBILE_URL = BASEURL+"/mobile";
    protected static final String MOBILE_TRACE_URL = MOBILE_URL+"/trace";
    protected static final String MOBILE_COMMENTAIRE_URL = MOBILE_URL+"/commentaire";
    protected static final String ADMIN_URL = BASEURL+"/admin";
    protected static final String ADMIN_TRACE_URL = ADMIN_URL+"/trace";
    protected static final String ADMIN_COMMENTAIRE_URL = ADMIN_URL+"/commentaire";


    @Value( "${trace.visible}") protected double VISIBLE;
    @Value( "${trace.lisible}") protected double LISIBLE;

    protected MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private static Set<Class> inited = new HashSet<>();

    @Autowired  private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Before
    public void init() throws Exception {
        doInit();
    }

    protected void doInit() throws Exception {
    }

    protected String json(Object o) throws IOException {
        return mapper.writeValueAsString(o);
    }

    protected ResultActions login(String username, String password) throws Exception {
        final JwtRequeteAuthentification auth = new JwtRequeteAuthentification();
        auth.setUsername(username);
        auth.setPassword(password);
        return mockMvc.perform(
                post("/auth")
                        .content(json(auth))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    protected String extractToken(MvcResult result) throws UnsupportedEncodingException {
        return JsonPath.read(result.getResponse().getContentAsString(), "$.token");
    }

    protected static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
