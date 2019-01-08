package com.retail.service;

import com.retail.constants.JSONConstants;
import com.retail.dao.ProductName;
import com.retail.exception.ProductNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
@PropertySource("classpath:application.properties")
public class ProductNameServiceClass {


    @Value("${restservice.baseurl}")
    private String baseUrl;

    @Value("${restservice.resourceuri}")
    private String resourceuri;

    private RestTemplate restTemplate = new RestTemplate();

    //hits redski.target.com com.retail.service and gets the productName
    public ProductName getProductName(Long productID) throws Exception {

        String endPointURL = baseUrl + productID + resourceuri;
        System.out.println("eurl"+endPointURL);
        String endPointResponse = null;
        try {
            endPointResponse = restTemplate.getForObject(endPointURL, String.class);

        } catch (HttpClientErrorException e) {
            throw new ProductNotFoundException("product not found for the given ProductID");
        }
        if (endPointResponse == null) {
            throw new ProductNotFoundException("product not found for the given ProductID");
        }
        JSONObject requestJsonObject = new JSONObject(endPointResponse);
        ProductName productName = parseJson(requestJsonObject);
        return productName;
    }

    //parses the response to fetch desired elements
    public ProductName parseJson(JSONObject requestJsonObject) throws JSONException {
        ProductName productName = new ProductName();
        JSONObject product = requestJsonObject.getJSONObject(JSONConstants.PRODUCT);
        JSONObject item = product.getJSONObject(JSONConstants.ITEM);
        JSONObject productDescription = item.getJSONObject(JSONConstants.DESCRIPTION);
        productName.setProductName(productDescription.getString(JSONConstants.TITLE));
        System.out.println(productName);
        return productName;
    }



}
