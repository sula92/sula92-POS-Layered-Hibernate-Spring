package com.sula.dao.custom;

import com.sula.dao.CrudDAO;
import com.sula.entity.Order;

public interface OrderDAO extends CrudDAO<Order,String> {

    public String getLastOrderId();

}
