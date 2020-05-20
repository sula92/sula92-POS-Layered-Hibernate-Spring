package com.sula.dao.custom.impl;

import com.sula.dao.CrudDAOImpl;
import com.sula.dao.custom.OrderDAO;
import com.sula.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOImpl extends CrudDAOImpl<Order,String> implements OrderDAO {


    @Override
    public String getLastOrderId() {
        return (String) session.createNativeQuery("select id from `order` order by id DESC LIMIT 1").uniqueResult();
    }
}
