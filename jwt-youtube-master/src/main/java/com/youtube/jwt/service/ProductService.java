package com.youtube.jwt.service;


import com.youtube.jwt.dao.ProductDao;
import com.youtube.jwt.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product addNewProduct(Product product){
        return productDao.save(product);
    }

    public List<Product> getAllProducts(){
       return (List<Product>) productDao.findAll();
    }

    public Product getProductDetailById(Integer productId){
        return productDao.findById(productId).get();
    }

    public void deleteProductDetails(Integer productId){
        productDao.deleteById(productId);
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId){
        if(isSingleProductCheckout){
            //buy single product
            List<Product> list=new ArrayList<>();
            Product product =  productDao.findById(productId).get();
            list.add(product);
            return list;
        }else{
            //checkout entire cart
        }
        return new ArrayList<>();
    }
}

