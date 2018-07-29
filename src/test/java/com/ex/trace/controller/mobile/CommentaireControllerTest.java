package com.ex.trace.controller.mobile;


import com.ex.trace.controller.AbstractMvcTest;
import com.ex.trace.service.dto.mobile.post.PostTraceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CommentaireControllerTest extends AbstractMvcTest{


    private String positionX = "48.2845041607";
    private String positionY = "0.2971697821";

    private String positionX2 = "48.3947208656";
    private String positionY2 = "0.2600692198";
    private  String tokenUser;
    private  String tokenAdmin;

    @Override
    protected void doInit() throws Exception {
        /*        registerUser("user", "user@gmail.com", "user", "user").andExpect(status().isCreated());*/
        tokenAdmin = extractToken( login("admin", "admin").andReturn());
        tokenUser = extractToken( login("user", "user").andReturn());
    }


    @Test
    public void recommander_NoRole() throws Exception {
        recommander ( positionX, positionY, 6)
                .andExpect(status().is(equalTo(401)));
    }

    @Test
    public void signaler_NoRole() throws Exception {
        signaler ( positionX, positionY, 6)
                .andExpect(status().is(equalTo(401)));
    }

    @Test
    public void signaler_WrongRole() throws Exception {
        signaler ( tokenAdmin, positionX, positionY, 6)
                .andExpect(status().is(equalTo(401)))
                .andExpect(jsonPath("$.message", equalTo("Access is denied")));
    }

    @Test
    public void recommander_WrongRole() throws Exception {
        recommander ( tokenAdmin, positionX, positionY, 6)
                .andExpect(status().is(equalTo(401)))
                .andExpect(jsonPath("$.message", equalTo("Access is denied")));
    }


    @Test
    public void obtenirTout() throws Exception {
        obtenirTout ( tokenUser, positionX, positionY, 1)
                .andExpect(status().is(equalTo(200)))
                .andDo(MockMvcResultHandlers.print());
    }



    private ResultActions obtenirTout ( String token, String posX, String posY, int idTrace) throws Exception {
        return mockMvc.perform(get( MOBILE_TRACE_URL+"/"+Integer.toString(idTrace)+"/commentaire")
                .param("traceId", Integer.toString(idTrace))
                .param("positionX", posX)
                .param("positionY", posY)
                .header("Authorization", token));
    }

    private ResultActions signaler ( String token, String posX, String posY, int id) throws Exception {
        return mockMvc.perform(post(MOBILE_COMMENTAIRE_URL+"/"+Integer.toString(id)+"/signalement")
                .param("positionX", posX)
                .param("positionY", posY)
                .header("Authorization", token));
    }

    private ResultActions signaler ( String posX, String posY, int id) throws Exception {
        return mockMvc.perform(post(MOBILE_COMMENTAIRE_URL+"/"+Integer.toString(id)+"/signalement")
                .param("positionX", posX)
                .param("positionY", posY));
    }

    private ResultActions nonSignaler ( String token, String posX, String posY, int id) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders
                .delete(MOBILE_COMMENTAIRE_URL+"/"+Integer.toString(id)+"/signalement")
                .param("positionX", posX)
                .param("positionY", posY)
                .header("Authorization", token));
    }

    private ResultActions nonSignaler ( String posX, String posY, int id) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders
                .delete(MOBILE_COMMENTAIRE_URL+"/"+Integer.toString(id)+"/signalement")
                .param("positionX", posX)
                .param("positionY", posY));
    }

    private ResultActions recommander ( String token, String posX, String posY, int id) throws Exception {
        return mockMvc.perform(post(MOBILE_COMMENTAIRE_URL+"/"+Integer.toString(id)+"/recommandation")
                .param("positionX", posX)
                .param("positionY", posY)
                .header("Authorization", token));
    }

    private ResultActions recommander ( String posX, String posY, int id) throws Exception {
        return mockMvc.perform(post(MOBILE_COMMENTAIRE_URL+"/"+Integer.toString(id)+"/recommandation")
                .param("positionX", posX)
                .param("positionY", posY));
    }

    private ResultActions nonRecommander ( String token, String posX, String posY, int id) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders
                .delete(MOBILE_COMMENTAIRE_URL+"/"+Integer.toString(id)+"/recommandation")
                .param("positionX", posX)
                .param("positionY", posY)
                .header("Authorization", token));
    }

    private ResultActions nonRecommander ( String posX, String posY, int id) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders
                .delete(MOBILE_COMMENTAIRE_URL+"/"+Integer.toString(id)+"/recommandation")
                .param("positionX", posX)
                .param("positionY", posY));
    }
}
