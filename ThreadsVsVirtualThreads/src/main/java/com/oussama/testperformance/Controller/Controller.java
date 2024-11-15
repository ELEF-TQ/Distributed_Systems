package com.oussama.testperformance.Controller;


import com.oussama.testperformance.Entity.Product;
import com.oussama.testperformance.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class Controller {
    @Autowired
    ProductsService productsservice;
    //creating a get mapping that retrieves all the books detail from the database
    @GetMapping("/products")
    private List<Product> getAllProducts() throws InterruptedException
    {
        return productsservice.getProducts();
    }
//creating a get mapping that retrieves the detail of a specific product
    @GetMapping("/products/{productid}")
    private Product getProduct(@PathVariable("productid") long id) {
        return productsservice.getProductsById(id);
    }
    //creating a delete mapping that deletes a specified product
    @DeleteMapping("/products/{productid}")
    private void deleteProduct(@PathVariable("productid") int id) {
        productsservice.delete(id);
    }

    //creating post mapping that post the product detail in the database
    @PostMapping("/products")
    private long saveProduct(@RequestBody Product product) {
        productsservice.saveOrUpdate(product);
        return product.getId();
    }

    //creating put mapping that updates the product detail
    @PutMapping("/products/{productid}")
    private Product update(@RequestBody Product product,
                           @PathVariable("productid") long id) {
        productsservice.update(product,id);
        return product;
    }
    //creating post mapping that save 1000 products detail in the database
    @PostMapping("/bulksave")
    public String BulkProduct() throws InterruptedException {
        return productsservice.bulkSaveProduct();
    }
}
