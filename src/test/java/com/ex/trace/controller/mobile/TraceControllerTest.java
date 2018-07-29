package com.ex.trace.controller.mobile;

import com.ex.trace.controller.AbstractMvcTest;
import com.ex.trace.service.dto.mobile.post.PostTraceDTO;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TraceControllerTest extends AbstractMvcTest {


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
    public void loginOK() throws Exception {
        login("user", "user")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
/*                .andExpect(jsonPath("$.user.username", equalTo("user")))
                .andExpect(jsonPath("$.user.password").doesNotExist())*/
                .andReturn();
    }

    @Test
    public void loginKO() throws Exception {
        login ("user", "wrong").andExpect(status().isUnauthorized());
    }


    @Test
    public void obtenirTout_NoRole() throws Exception {
        obtenirTout ( positionX, positionY)
                .andExpect(status().is(equalTo(401)));
    }

    @Test
    public void obtenir_NoRole() throws Exception {
        obtenir ( positionX, positionY, 1)
                .andExpect(status().is(equalTo(401)));
    }

    @Test
    public void signaler_NoRole() throws Exception {
        signaler ( positionX, positionY, 1)
                .andExpect(status().is(equalTo(401)));
    }

    @Test
    public void recommander_NoRole() throws Exception {
        recommander ( positionX2, positionY2, 1)
                .andExpect(status().is(equalTo(401)));
    }

    @Test
    public void ajouter_NoRole() throws Exception {
        PostTraceDTO postDTO = new PostTraceDTO("test unitaire",   Double.parseDouble(positionX), Double.parseDouble(positionY), "FR");
        ajouter (  postDTO)
                .andExpect(status().is(equalTo(401)));
    }


    @Test
    public void obtenirTout_WrongRole() throws Exception {
        obtenirTout ( tokenAdmin, positionX, positionY)
                .andExpect(status().is(equalTo(401)))
                .andExpect(jsonPath("$.message", equalTo("Access is denied")));
    }

    @Test
    public void otenir_WrongRole() throws Exception {
        obtenir ( tokenAdmin, positionX, positionY, 1)
                .andExpect(status().is(equalTo(401)))
                .andExpect(jsonPath("$.message", equalTo("Access is denied")));
    }

    @Test
    public void signaler_WrongRole() throws Exception {
        signaler ( tokenAdmin, positionX, positionY, 1)
                .andExpect(status().is(equalTo(401)))
                .andExpect(jsonPath("$.message", equalTo("Access is denied")));
    }

    @Test
    public void recommander_WrongRole() throws Exception {
        recommander ( tokenAdmin, positionX, positionY, 1)
                .andExpect(status().is(equalTo(401)))
                .andExpect(jsonPath("$.message", equalTo("Access is denied")));
    }

    @Test
    public void ajouter_WrongRole() throws Exception {
        PostTraceDTO postDTO = new PostTraceDTO("test unitaire",   Double.parseDouble(positionX), Double.parseDouble(positionY), "FR");
        ajouter (tokenAdmin, postDTO)
                .andExpect(status().is(equalTo(401)))
                .andExpect(jsonPath("$.message", equalTo("Access is denied")));
    }



    @Test
    public void obtenirTout() throws Exception {
        obtenirTout ( tokenUser, positionX, positionY)
                .andExpect(status().is(equalTo(200)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[2].id", equalTo(3)))
                .andExpect(jsonPath("$[3].id", equalTo(4)));
    }

    @Test
    public void otenir() throws Exception {
        obtenir ( tokenUser, positionX, positionY, 1)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(equalTo(200)))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void ajouter() throws Exception {

        PostTraceDTO dto = new PostTraceDTO("test unitaire",   Double.parseDouble(positionX), Double.parseDouble(positionY), "FR");
        ajouter (tokenUser, dto)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(equalTo(201)))
                /* FIXME:*/
                .andExpect(jsonPath("$.autheur",                equalTo(false)))
                .andExpect(jsonPath("$.contenu",                equalTo( dto.getContenu())))
                .andExpect(jsonPath("$.positionX",              equalTo( dto.getPositionX())))
                .andExpect(jsonPath("$.positionY",              equalTo( dto.getPositionY())))
                .andExpect(jsonPath("$.traceType",              equalTo("I")))
                .andExpect(jsonPath("$.signale",                equalTo(false)))
                .andExpect(jsonPath("$.totalSignalement",       equalTo(0)))
                .andExpect(jsonPath("$.totalRecommandation",    equalTo(0)))
                .andExpect(jsonPath("$.totalVisite",            equalTo(0)));
    }

    @Test
    public void signaler() throws Exception {
        signaler ( tokenUser, positionX, positionY, 1)
                .andExpect(status().is(equalTo(200)));
        obtenir ( tokenUser, positionX, positionY ,1)
                .andExpect(jsonPath("$.signale", equalTo(true)));
    }

    @Test
    public void nonSignaler() throws Exception {
        nonSignaler ( tokenUser, positionX, positionY, 1)
                .andExpect(status().is(equalTo(200)));
        obtenir ( tokenUser, positionX, positionY,1)
                .andExpect(jsonPath("$.signale", equalTo(false)));
    }

    @Test
    public void nonRecommander() throws Exception {
        nonRecommander ( tokenUser, positionX, positionY, 1)
                .andExpect(status().is(equalTo(200)));
        obtenir ( tokenUser, positionX, positionY,1)
                .andExpect(jsonPath("$.recommande", equalTo(false)));
    }

    @Test
    public void recommander() throws Exception {
        recommander ( tokenUser, positionX, positionY, 1)
                .andExpect(status().is(equalTo(200)));
        obtenir ( tokenUser, positionX, positionY, 1)
                .andExpect(jsonPath("$.recommande", equalTo(true)));

    }

    @Test
    public void otenir_TropLoin() throws Exception {
        obtenir ( tokenUser, positionX2, positionY2, 1)
                .andDo( MockMvcResultHandlers.print() )
                .andExpect(status().is(equalTo(404)));
    }

    @Test
    public void otenir_NoExiste() throws Exception {
        obtenir ( tokenUser, positionX, positionY, 0)
                .andDo( MockMvcResultHandlers.print() )
                .andExpect(status().is(equalTo(404)));
    }

    @Test
    public void signaler_TropLoin() throws Exception {
        signaler ( tokenUser,  positionX2, positionY2, 1)
                .andExpect(status().is(equalTo(404)));
    }

    @Test
    public void nonSignaler_TropLoin() throws Exception {
        nonSignaler ( tokenUser,  positionX2, positionY2, 1)
                .andExpect(status().is(equalTo(404)));
    }

    @Test
    public void nonRecommande_TropLoin() throws Exception {
        nonRecommander ( tokenUser, positionX2, positionY2, 1)
                .andExpect(status().is(equalTo(404)));
    }

    @Test
    public void recommander_TropLoin() throws Exception {
        recommander ( tokenUser, positionX2, positionY2, 1)
                .andExpect(status().is(equalTo(404)));
    }

    @Test
    public void ajouter_NoContent() throws Exception {
        PostTraceDTO postDTO = new PostTraceDTO("",   Double.parseDouble(positionX), Double.parseDouble(positionY), "FR");
        ajouter (tokenUser, postDTO)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(equalTo(400)));
    }

    @Test
    public void ajouter_NoCodePays() throws Exception {
        PostTraceDTO postDTO = new PostTraceDTO("contenu",   Double.parseDouble(positionX), Double.parseDouble(positionY), "");
        ajouter (tokenUser, postDTO)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(equalTo(400)));

    }

    private ResultActions registerUser(String username, String email,String password, String matchingPassword) throws Exception {
        return mockMvc.perform(
                post("/utilisateur")
                        .content("{\"username\":\"" + username +
                                        "\",\"email\":\"" + email +
                                        "\",\"password\":\"" + password +
                                        "\",\"matchingPassword\":\"" + matchingPassword +
                                        "\"}"));
    }


    private ResultActions obtenir ( String token, String posX, String posY, int id) throws Exception {
        return mockMvc.perform(get(MOBILE_TRACE_URL+"/"+Integer.toString(id))
                .param("positionX", posX)
                .param("positionY", posY)
                .header("Authorization", token));
    }

    private ResultActions obtenir ( String posX, String posY, int id) throws Exception {
        return mockMvc.perform(get(MOBILE_TRACE_URL+"/"+Integer.toString(id))
                .param("positionX", posX)
                .param("positionY", posY));
    }

    private ResultActions obtenirTout ( String token, String posX, String posY) throws Exception {
        return mockMvc.perform(get( MOBILE_TRACE_URL)
                .param("positionX", posX)
                .param("positionY", posY)
                .header("Authorization", token));
    }

    private ResultActions obtenirTout (String posX, String posY) throws Exception {
        return mockMvc.perform(get( MOBILE_TRACE_URL)
                .param("positionX", posX)
                .param("positionY", posY));
    }

    private ResultActions ajouter (String token, PostTraceDTO postDTO) throws Exception {
        return mockMvc.perform(post( MOBILE_TRACE_URL)
                .content(asJsonString(postDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token));
    }

    private ResultActions ajouter ( PostTraceDTO postDTO) throws Exception {
        return mockMvc.perform(post( MOBILE_TRACE_URL)
                .content(asJsonString( postDTO))
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions signaler ( String token, String posX, String posY, int id) throws Exception {
        return mockMvc.perform(post(MOBILE_TRACE_URL+"/"+Integer.toString(id)+"/signalement")
                .param("positionX", posX)
                .param("positionY", posY)
                .header("Authorization", token));
    }

    private ResultActions signaler ( String posX, String posY, int id) throws Exception {
        return mockMvc.perform(post(MOBILE_TRACE_URL+"/"+Integer.toString(id)+"/signalement")
                .param("positionX", posX)
                .param("positionY", posY));
    }

    private ResultActions nonSignaler ( String token, String posX, String posY, int id) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders
                .delete(MOBILE_TRACE_URL+"/"+Integer.toString(id)+"/signalement")
                .param("positionX", posX)
                .param("positionY", posY)
                .header("Authorization", token));
    }

    private ResultActions nonSignaler ( String posX, String posY, int id) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders
                .delete(MOBILE_TRACE_URL+"/"+Integer.toString(id)+"/signalement")
                .param("positionX", posX)
                .param("positionY", posY));
    }

    private ResultActions recommander ( String token, String posX, String posY, int id) throws Exception {
        return mockMvc.perform(post(MOBILE_TRACE_URL+"/"+Integer.toString(id)+"/recommandation")
                .param("positionX", posX)
                .param("positionY", posY)
                .header("Authorization", token));
    }

    private ResultActions recommander ( String posX, String posY, int id) throws Exception {
        return mockMvc.perform(post(MOBILE_TRACE_URL+"/"+Integer.toString(id)+"/recommandation")
                .param("positionX", posX)
                .param("positionY", posY));
    }

    private ResultActions nonRecommander ( String token, String posX, String posY, int id) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders
                .delete(MOBILE_TRACE_URL+"/"+Integer.toString(id)+"/recommandation")
                .param("positionX", posX)
                .param("positionY", posY)
                .header("Authorization", token));
    }

    private ResultActions nonRecommander ( String posX, String posY, int id) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders
                .delete(MOBILE_TRACE_URL+"/"+Integer.toString(id)+"/recommandation")
                .param("positionX", posX)
                .param("positionY", posY));
    }

}