package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.rest_service.Application;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(classes = Application.class)
public class RecordsControllerTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  private HttpMessageConverter mappingJackson2HttpMessageConverter;

  private MockMvc mockMvc;

  @Autowired
  private RecordsController recordsController;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters) {

    this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
        .findAny()
        .orElse(null);

    assertNotNull("the JSON message converter must not be null",
        this.mappingJackson2HttpMessageConverter);

  }

  @Before
  public void setup() {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testGetSuccessful() {
  }

  @Test
  public void testGetNotFound() throws Exception {
    mockMvc.perform(get("/records/0")).andExpect(status().isNotFound());
  }

  @Test
  public void testPostSuccessful() {

  }

  @Test
  public void testPostBadJson() {

  }
}
