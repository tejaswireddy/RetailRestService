package com.retail.service;

import com.retail.AbstractTest;
import com.retail.dao.Product;
import com.retail.dao.ProductName;
import com.retail.dao.ProductPrice;
import com.retail.marklogic.MLUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestBuildProductService extends AbstractTest{

    @Autowired
    public BuildProductService buildproductservice;

    @Autowired
    public MLUtil mlUtil;



    Long productID = (long) 13860428;


    @Test
    public void getProductDetailsTest() throws Exception {

        Product pname = buildproductservice.getProductInfo(productID);
        assertEquals(pname.getPriceInfo().getPrice(),"99.9");
        assertEquals(pname.getPriceInfo().getCurrency(),"USD");
        assertEquals(pname.getProductID().toString(),"13860428");
        assertEquals(pname.getProductName(),"The Big Lebowski (Blu-ray)");
    }

    @Test
    public void updateProductPriceTest() throws Exception {
        Boolean result = buildproductservice.updateProductPriceInfo(productID,"99.9","USD");

        List<String> pricedetails = mlUtil.fetchPrice(productID.toString());
        assertEquals(result,true);
        assertEquals(pricedetails.get(0),"99.9");
        assertEquals(pricedetails.get(1),"USD");

    }


}
