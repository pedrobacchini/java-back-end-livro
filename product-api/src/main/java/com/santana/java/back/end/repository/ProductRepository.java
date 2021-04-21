package com.santana.java.back.end.repository;

import com.santana.java.back.end.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p.name, p.price, p.productIdentifier "
            + "from product p "
            + "join category c on p.category.id = c.id "
            + "where c.id = :categoryId ")
    List<Product> getProductByCategory(@Param("categoryId") long categoryId);

    Product findByProductIdentifier(String productIdentifier);

}
