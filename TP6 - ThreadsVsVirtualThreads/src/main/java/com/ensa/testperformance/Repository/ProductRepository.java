package com.ensa.testperformance.Repository;

import com.ensa.testperformance.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
