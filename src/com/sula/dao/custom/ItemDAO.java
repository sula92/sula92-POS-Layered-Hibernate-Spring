package com.sula.dao.custom;

import com.sula.dao.CrudDAO;
import com.sula.entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item,String> {

    public int updateQty(int qty,String icode) throws SQLException;

    public abstract String getLastItemId() throws SQLException;

}
