package com.youtube.jwt.service;

import com.youtube.jwt.configuration.JwtRequestFilter;
import com.youtube.jwt.dao.CartDao;
import com.youtube.jwt.dao.ProductDao;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.Cart;
import com.youtube.jwt.entity.Product;
import com.youtube.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;
    public Cart addToCart(Integer productId){
        Product product= productDao.findById(productId).get();
        String username = JwtRequestFilter.CURRENT_USER;
        User user=null;
        if(username!=null){
            user = userDao.findById(username).get();
        }

        if (product != null && user!=null) {
            Cart cart=new Cart(product,user);
            return cartDao.save(cart);
        }
        return null;
    }

    public List<Cart> getCartDetails(){
        String username=JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(username).get();
        return cartDao.findByUser(user);
    }
}
