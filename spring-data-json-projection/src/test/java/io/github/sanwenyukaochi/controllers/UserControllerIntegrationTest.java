package io.github.sanwenyukaochi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@DisplayName("User API integration tests")
class UserControllerIntegrationTest {

    @Autowired private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private String createUserRequestJson;
    private String expectedUserResponseJson;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        createUserRequestJson =
                """
        {
          "data": {
            "user": {
              "id": 1001,
              "info": {
                "nickname": "alice"
              }
            },
            "company": {
              "name": "Acme"
            }
          }
        }
        """;

        expectedUserResponseJson =
                """
        {
          "id": 1001,
          "nickname": "alice",
          "company": "Acme"
        }
        """;
    }

    @Test
    @DisplayName("Return flat JSON from POST /users")
    void testCreateUser() throws Exception {
        ResultActions response =
                mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createUserRequestJson));

        response.andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                content().json(expectedUserResponseJson, JsonCompareMode.STRICT));
    }
}
