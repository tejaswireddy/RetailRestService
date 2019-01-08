package com.retail.controller;

import com.retail.dao.ExceptionResponse;
import com.retail.dao.Product;
import com.retail.exception.ProductNotFoundException;
import com.retail.service.BuildProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ProductController {


    @Autowired
    private BuildProductService productService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception ex) {

        ExceptionResponse error = new ExceptionResponse();
        if(ex instanceof   IllegalArgumentException){
            error.setErrorCode(400);
            error.setMessage (ex.getMessage());
            return new ResponseEntity<ExceptionResponse>(error, HttpStatus.BAD_REQUEST);
        }
         else if (ex instanceof  ProductNotFoundException){
            error.setErrorCode(404);
            error.setMessage (ex.getMessage());
            return new ResponseEntity<ExceptionResponse>(error, HttpStatus.NOT_FOUND);
        }else{
            error.setErrorCode(500);
            error.setMessage (" Unknown error occured.");
            error.setMessage (ex.getMessage());
            return new ResponseEntity<ExceptionResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "products/{productID:[\\d]+}", method = RequestMethod.GET)
    public Product getProductInfo(@PathVariable("productID") Long productID) throws Exception {
        //throw exception if product id is null or negative value
        if (productID == null || productID < 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        //gets product information product details + price information
        Product product = productService.getProductInfo(productID);

        //if either productname or productid is null throw NotFound Exception

        if (product.getProductName() == null || product.getProductID() == null)
            throw new ProductNotFoundException("ProductID not found");

        return product;
    }



    @RequestMapping(value = "products/{productID}", method = RequestMethod.PUT)
    public ResponseEntity<ExceptionResponse> updateProductPriceInfo(@PathVariable("productID") Long productID, @RequestBody Product product)
            throws Exception {
        ExceptionResponse error = new ExceptionResponse();
        //throw exception if product id is null or negative value
        if (productID == null || productID < 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        //returns true if updating price/currency in database is successfull
        //This updates both price and currency

        if(product.getPriceInfo().getPrice() == null){
            throw new IllegalArgumentException("Please enter the price to update");
        }
       boolean updated = productService.updateProductPriceInfo(productID, product.getPriceInfo().getPrice(), product.getPriceInfo().getCurrency());


        if (updated==false) {
            throw new ProductNotFoundException("Product Not Found");
        }
        else {
            error.setErrorCode(200);
            error.setMessage("Product with id " + productID + " successfully updated");
            error.setMessage (error.getMessage());
            return new ResponseEntity<ExceptionResponse>(error, HttpStatus.OK);
        }

    }


}
