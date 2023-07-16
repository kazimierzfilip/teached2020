package com.teachedapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn150() throws Exception {
        this.mockMvc.perform(get("/teachers/-2/salary"))
                .andDo(print())
                .andExpect(content().string(containsString("150.0")));
    }

    @Test
    public void shouldReturn105() throws Exception {
        this.mockMvc.perform(get("/teachers/-2/salary?calculateDate=2020-05-12"))
                .andDo(print())
                .andExpect(content().string(containsString("105.0")));
    }
}
