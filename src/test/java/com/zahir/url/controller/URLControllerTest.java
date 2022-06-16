package com.zahir.url.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class URLControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private URLController urlController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(urlController)
                .build();
    }

    @Test
    public void saveURL() throws Exception {
        mockMvc.perform(post("/short?url=https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
    }

    @Test
    public void getOriginalURL() throws Exception {
        mockMvc.perform(get("/long?tiny=cfbb0bcb")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
    }
}