package com.retail.service;

import com.retail.dao.*;
import com.retail.exception.ProductNotFoundException;
import com.retail.marklogic.MLUtil;
import com.retail.marklogic.MLConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildProductService {

    @Autowired
    private MLConfig mlconfig;

    @Autowired
    private MLUtil mlutil;

    @Autowired
    private ProductNameServiceClass productserviceclass;


    //aggregates and constructs the final response message
    public Product getProductInfo(Long productID) throws Exception {
        String pid = productID.toString();
        List<String> pricedetails =  new ArrayList<String>();

        pricedetails = mlutil.fetchPrice(pid);

        if(pricedetails.isEmpty()){
            throw new ProductNotFoundException("Product Not Found");
        }

       String price = pricedetails.get(0);
       String currency = pricedetails.get(1);
       ProductPrice productprice = new ProductPrice();
       productprice.setPrice(price);
       productprice.setCurrency(currency);
       ProductName productname = productserviceclass.getProductName(productID);
       return combineResponses(productID,productprice,productname);
    }

    //updates the product price/currency when a PUT request is executed
    public boolean updateProductPriceInfo(Long productID, String price, String currency) throws Exception {

        List<String> result = mlutil.updatePrice(productID.toString(),price, currency);

        if(result.get(0).toString().equals("true")){
            return true;
        }
        else{
            return false;
        }

    }

    //aggregates response from database as well as redski.target.com com.retail.service
    public Product combineResponses(Long productID, ProductPrice productPrice, ProductName productName) {

        Product productObject = new Product();
        productObject.setProductName(productName.getProductName());
        productObject.setProductID(productID);

        PriceInfo priceInfo = new PriceInfo();
        if (productPrice == null) {
            priceInfo.setCurrency(null);
            priceInfo.setPrice(null);
        } else {
            priceInfo.setCurrency(productPrice.getCurrency());
            priceInfo.setPrice(productPrice.getPrice());
        }
        productObject.setPriceInfo(priceInfo);
        return productObject;
    }


}
