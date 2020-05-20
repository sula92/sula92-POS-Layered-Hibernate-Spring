package com.sula.business.custom.impl;

import com.sula.business.custom.ItemBO;
import com.sula.dao.DAOFactory;
import com.sula.dao.DAOTypes;
import com.sula.dao.custom.ItemDAO;
import com.sula.db.HibernateUtil;
import com.sula.dto.ItemDTO;
import com.sula.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("itemboimpl")
@Transactional
public class ItemBOImpl implements ItemBO {

    @Autowired
    ItemDAO itemDAO;//=DAOFactory.getInstance().getDAO(DAOTypes.ITEM);

    @Autowired
    Session session;

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveItem(ItemDTO item) throws Exception {

        //try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            System.out.println("eeee"+item.getQty());
            itemDAO.save(new Item(item.getItemCode(),
                    item.getDescription(), Integer.valueOf(item.getQty()), Double.valueOf(item.getUprice())));
            session.getTransaction().commit();
        //}

    }

    @Override
    public void updateItem(ItemDTO item) throws Exception {

            itemDAO.setSession(session);
            session.beginTransaction();
            itemDAO.update(new Item(item.getItemCode(),
                    item.getDescription(), Integer.valueOf(item.getQty()), Double.valueOf(item.getUprice())));
            session.getTransaction().commit();

    }

    @Override
    public void deleteItem(String itemCode) throws Exception {

        try (Session session = sessionFactory.openSession()) {
            itemDAO.setSession(session);

            session.beginTransaction();

            itemDAO.delete(itemCode);
            session.getTransaction().commit();
        }

    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {

            itemDAO.setSession(session);
            session.beginTransaction();
            System.out.println("sfsgsh");
            List<Item> allItems = itemDAO.findAll();
            List<ItemDTO> dtos = new ArrayList<>();
            for (Item item : allItems) {
                dtos.add(new ItemDTO(item.getCode(),
                        item.getDescription(),
                        String.valueOf(item.getQtyOnHand()),
                        String.valueOf(item.getUnitPrice())));
            }
            session.getTransaction().commit();
            return dtos;

    }

    @Override
    public String getLastItemCode() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            String lastItemCode = itemDAO.getLastItemId();
            session.getTransaction().commit();
            return lastItemCode==null ? "I000" : lastItemCode;
        }
    }

    @Override
    public ItemDTO findItem(String itemCode) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            Item item = itemDAO.find(itemCode);
            session.getTransaction().commit();
            return new ItemDTO(item.getCode(),
                    item.getDescription(),
                    String.valueOf(item.getQtyOnHand()),
                    String.valueOf(item.getUnitPrice()));
        }
    }

    @Override
    public List<String> getAllItemCodes() throws Exception {
        return null;
    }
}
