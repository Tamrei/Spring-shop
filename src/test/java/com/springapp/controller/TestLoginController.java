package com.springapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/resources/spring/root-context.xml"})
public class TestLoginController {

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testSuccessLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeDoesNotExist("logout"));
    }

    @Test
    public void testLoginError() throws Exception {
        mockMvc.perform(get("/login")
                .param("error", ""))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeDoesNotExist("logout"));
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(get("/login")
                .param("logout", ""))
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeExists("logout"))
                .andExpect(forwardedUrl("/WEB-INF/views/login.jsp"));
    }
}
