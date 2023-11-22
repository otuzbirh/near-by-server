package com.sb.nearby.repository;

import com.sb.nearby.model.Category;
import com.sb.nearby.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByName(String name, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByNameAndCategory_Id(String name, Long category, Pageable pageable);

    Page<Product> findByCategoryId(Long category, Pageable pageable);

}
