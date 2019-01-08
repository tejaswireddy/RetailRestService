package com.retail.service;

import com.retail.AbstractTest;
import com.retail.controller.TestProductController;
import com.retail.dao.Product;
import com.retail.dao.ProductName;
import com.retail.exception.ProductNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class TestProductNameServiceClass extends AbstractTest {

    @Autowired
    public ProductNameServiceClass pserviceclass;

    Long productID = (long) 13860428;
    Long wrongProductID = (long) 122222;


    @Test
    public void getProductNameTest() throws Exception {

        ProductName pname = pserviceclass.getProductName(productID);
        assertEquals(pname.getProductName(),"The Big Lebowski (Blu-ray)");


    }

    @Test
    public void getProductNameTestWrongId() throws Exception {
       try {
           ProductName pname = pserviceclass.getProductName(wrongProductID);
       }catch (ProductNotFoundException e){

       }


    }


}
