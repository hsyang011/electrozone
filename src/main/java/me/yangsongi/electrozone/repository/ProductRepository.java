package me.yangsongi.electrozone.repository;

import me.yangsongi.electrozone.domain.ProductCategory;
import me.yangsongi.electrozone.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop6ByOrderByRegisteredAtDesc();

    List<Product> findTop12ByProductCategory(ProductCategory productCategory);

}
