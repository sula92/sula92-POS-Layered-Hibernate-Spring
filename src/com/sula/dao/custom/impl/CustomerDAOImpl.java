package com.sula.dao.custom.impl;


import com.sula.dao.CrudDAOImpl;
import com.sula.dao.custom.CustomerDAO;
import com.sula.entity.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Qualifier("customerdaoimpl")
public class CustomerDAOImpl extends CrudDAOImpl<Customer, String> implements CustomerDAO  {

    public CustomerDAOImpl() {
    }

    @Override
    public String getLastCustomerId() throws SQLException {
        return (String) session.createNativeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1").uniqueResult();
    }
}
