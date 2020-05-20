package com.sula.dao.custom;

import com.sula.dao.CrudDAO;
import com.sula.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer,String> {

    public abstract String getLastCustomerId() throws SQLException;

}
