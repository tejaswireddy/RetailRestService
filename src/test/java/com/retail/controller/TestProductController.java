package com.retail.controller;


import com.retail.AbstractTest;
import com.retail.Application;
import com.retail.dao.PriceInfo;
import com.retail.dao.Product;
import com.retail.service.BuildProductService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@ContextConfiguration(classes=Application.class, loader=SpringBootContextLoader.class)
@ActiveProfiles(profiles = "unit")
public class TestProductController{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildProductService buildproductservice;


    PriceInfo priceInfo = new PriceInfo("USD", "67.5");
    Long productID = (long) 13860428;
    String expectedResponse = "{\"productID\":13860428,\"productName\":\"The Big Lebowski (Blu-ray)\",\"priceInfo\":{\"currency\":\"USD\",\"price\":\"67.5\"}}";
    String request = "{\"priceInfo\":{\"price\":67.5,\"currency\":\"USD\"}}";
    Product mockProduct = new Product(productID, "The Big Lebowski (Blu-ray)", priceInfo);


    @Test
    public void getProductInfoTest() throws Exception {

       Mockito.when(buildproductservice.getProductInfo(Mockito.anyLong())).thenReturn(mockProduct);

       MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/13860428")
                .accept(MediaType.APPLICATION_JSON)).andReturn();

       assertEquals(200, result.getResponse().getStatus());
       assertEquals(expectedResponse,result.getResponse().getContentAsString().toString());

    }


    @Test
    public void getProductInfoTestNotFound() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/product/138000")
                .accept(MediaType.APPLICATION_JSON).content(request).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals("{\"errorCode\":404,\"message\":\"Product Not Found\"}", result.getResponse().getContentAsString().toString());

    }


}
