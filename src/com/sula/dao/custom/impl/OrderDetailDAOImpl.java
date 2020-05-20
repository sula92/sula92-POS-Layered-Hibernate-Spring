package com.sula.dao.custom.impl;

import com.sula.dao.CrudDAOImpl;
import com.sula.dao.custom.OrderDetailDAO;
import com.sula.entity.OrderDetail;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail,String> implements OrderDetailDAO {


}
