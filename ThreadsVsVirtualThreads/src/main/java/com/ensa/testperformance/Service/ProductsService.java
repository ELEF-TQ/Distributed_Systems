package com.ensa.testperformance.Service;


import com.ensa.testperformance.Repository.ProductRepository;
import com.ensa.testperformance.Entity.Product;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductsService {
    @Autowired
    ProductRepository productRepository;
    public List<Product> getProducts() throws InterruptedException {
        Thread.sleep(1000); // simuler la latence réseau
        return productRepository.findAll();
    }
    public String bulkSaveProduct() throws InterruptedException {
        for(int i=0; i< 1000; i++) {
            Product product = new Product();
            product.setProductName(RandomStringUtils.randomAlphanumeric(5));
            product.setPrice(RandomUtils.nextLong(10,1000));
            product.setPrice(1L);
            saveOrUpdate(product);
        }
        return "finished";
    }
    public Product getProductsById(Long id)
    {
        return productRepository.findById(id).get();
    }
    public void saveOrUpdate(Product product)
    {
        productRepository.save(product);
    }
    //deleting a specific record by using the method deleteById() of CrudRepository
    public void delete(long id)
    {
        productRepository.deleteById(id);
    }
    //updating a record
    public void update(Product product, long productid)
    {
        productRepository.save(product);
    }
}
