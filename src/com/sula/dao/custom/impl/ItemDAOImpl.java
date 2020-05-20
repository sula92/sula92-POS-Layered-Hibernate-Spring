package com.sula.dao.custom.impl;

import com.sula.dao.CrudDAOImpl;
import com.sula.dao.custom.ItemDAO;
import com.sula.entity.Item;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@Qualifier("itemdaoimpl")
public class ItemDAOImpl extends CrudDAOImpl<Item,String> implements ItemDAO {


    @Override
    public int updateQty(int qty, String icode) throws SQLException {
        try {
            Item item=session.get(Item.class, icode);
            item.setQtyOnHand(qty);
            session.merge(icode,item);
            session.getTransaction().commit();
            return 1;
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }

    }

    @Override
    public String getLastItemId() throws SQLException {
        return (String) session.createNativeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1").uniqueResult();
    }
}
