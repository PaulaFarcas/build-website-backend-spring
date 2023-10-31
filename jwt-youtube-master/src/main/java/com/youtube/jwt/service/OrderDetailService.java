package com.youtube.jwt.service;

import com.youtube.jwt.configuration.JwtRequestFilter;
import com.youtube.jwt.dao.CartDao;
import com.youtube.jwt.dao.OrderDetailDao;
import com.youtube.jwt.dao.ProductDao;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {

    private static final String ORDER_PLACED="Placed";

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;


    public List<OrderDetail> getOrderDetails(){
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user=userDao.findById(currentUser).get();
        return orderDetailDao.findByUser(user);
    }

    public List<OrderDetail> getAllOrderDetails(){
        List<OrderDetail> orderDetails=new ArrayList<>();
        orderDetailDao.findAll().forEach(
                x->orderDetails.add(x)
        );
        return orderDetails;
    }

    public void placeOrder(OrderInput orderInput,boolean isSingleProductCheckout){
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for(OrderProductQuantity o:productQuantityList){
            Product product = productDao.findById(o.getProductId()).get();

            String currentUser= JwtRequestFilter.CURRENT_USER;
            User user= userDao.findById(currentUser).get();


            OrderDetail orderDetail=new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContractNumber(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice()*o.getQuantity(),
                    product,
                    user
            );
            if(!isSingleProductCheckout){
                List<Cart> carts = cartDao.findByUser(user);
                carts.stream().forEach(x->cartDao.deleteById(x.getCartId()));
            }
            orderDetailDao.save(orderDetail);
        }
    }
}
