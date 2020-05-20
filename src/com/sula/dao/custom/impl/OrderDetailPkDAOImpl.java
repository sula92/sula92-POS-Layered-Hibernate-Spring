package com.sula.dao.custom.impl;

import com.sula.dao.custom.OrderDetailPkDAO;
import com.sula.entity.OrderDetailPk;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDetailPkDAOImpl implements OrderDetailPkDAO {



    @Override
    public void setSession(Session session) {

    }

    @Override
    public List<OrderDetailPk> findAll() throws Exception {
        return null;
    }

    @Override
    public OrderDetailPk find(String s) throws Exception {
        return null;
    }

    @Override
    public void save(OrderDetailPk entity) throws Exception {

    }

    @Override
    public void update(OrderDetailPk entity) throws Exception {

    }

    @Override
    public void delete(String s) throws Exception {

    }
}

