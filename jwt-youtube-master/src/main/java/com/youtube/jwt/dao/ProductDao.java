package com.youtube.jwt.dao;


import com.youtube.jwt.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface ProductDao extends CrudRepository<Product,Integer> {
    public List<Product> findAll(Pageable pageable);

    //ignore-uppercase or lowercase
    public List<Product> findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
            String key1,String key2,Pageable pageable
    );
}
