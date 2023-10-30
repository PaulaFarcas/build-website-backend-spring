package com.youtube.jwt.service;


import com.youtube.jwt.configuration.JwtRequestFilter;
import com.youtube.jwt.dao.CartDao;
import com.youtube.jwt.dao.ProductDao;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.Cart;
import com.youtube.jwt.entity.Product;
import com.youtube.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public Product addNewProduct(Product product){
        return productDao.save(product);
    }

    public List<Product> getAllProducts(int pageNumber,String searchKey){
        Pageable pageable = PageRequest.of(pageNumber,12);
        if(searchKey.equals("")){
            return (List<Product>) productDao.findAll(pageable);
        }else{
           return (List<Product>) productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                    searchKey,searchKey,pageable
            );
        }

    }

    public Product getProductDetailById(Integer productId){
        return productDao.findById(productId).get();
    }

    public void deleteProductDetails(Integer productId){
        productDao.deleteById(productId);
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId){
        if(isSingleProductCheckout && productId!=0){
            //buy single product
            List<Product> list=new ArrayList<>();
            Product product =  productDao.findById(productId).get();
            list.add(product);
            return list;
        }else{
            //checkout entire cart
             String username = JwtRequestFilter.CURRENT_USER;
             User user = userDao.findById(username).get();
             List<Cart> carts = cartDao.findByUser(user);
             return carts.stream().map(x->x.getProduct()).collect(Collectors.toList());
        }
    }
}

