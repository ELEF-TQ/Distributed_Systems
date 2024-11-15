package com.oussama.testperformance.Repository;

import com.oussama.testperformance.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
