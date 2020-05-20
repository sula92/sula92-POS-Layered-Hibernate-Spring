package com.sula.business.custom.impl;

import com.sula.Appinitializer;
import com.sula.business.custom.OrderBO;
import com.sula.dao.DAOFactory;
import com.sula.dao.DAOTypes;
import com.sula.dao.custom.*;
import com.sula.dao.custom.impl.CustomerDAOImpl;
import com.sula.dao.custom.impl.OrderDAOImpl;
import com.sula.db.DBConnection;
import com.sula.db.HibernateUtil;
import com.sula.dto.OrderDTO;
import com.sula.dto.SearchOrderDTO;
import com.sula.entity.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderBOImpl implements OrderBO {

    @Autowired
    OrderDAO orderDAO= Appinitializer.ctx.getBean(OrderDAOImpl.class);//DAOFactory.getInstance().getDAO(DAOTypes.ORDER);
    @Autowired
    OrderDetailDAO orderDetailDAO;//=DAOFactory.getInstance().getDAO(DAOTypes.ORDERDETAIL);
    @Autowired
    ItemDAO itemDAO;//=DAOFactory.getInstance().getDAO(DAOTypes.ITEM);
    @Autowired
    CustomerDAO customerDAO;//=DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
    @Autowired
    QueryDAO queryDAO;//=DAOFactory.getInstance().getDAO(DAOTypes.QUERY);
    //Connection connection = DBConnection.getInstance().getConnection();

    @Autowired
    Session session;

    @Override
    public String getLastOrderId() throws Exception {
        //Session session= HibernateUtil.getSessionFactory().openSession();
        orderDAO.setSession(session);
        session.beginTransaction();

        session.getTransaction().commit();

        return orderDAO.getLastOrderId();

    }

    @Override
    public void placeOrder(OrderDTO order) throws Exception {

        //Session session=HibernateUtil.getSessionFactory().openSession();
        orderDAO.setSession(session);
        orderDetailDAO.setSession(session);
        itemDAO.setSession(session);
        customerDAO.setSession(session);

        session.beginTransaction();

        LocalDate localDate=LocalDate.now();
        Date date=Date.valueOf(localDate);
        System.out.println(date);
        //List<OrderDetail> orderDetails= new ArrayList<>();
        //OrderDetail detail=new OrderDetail(order.getOrderId(),"I002",3,20.0);
        Customer customer=customerDAO.find(order.getCustomerId());
        try {
            orderDAO.save(new Order(order.getOrderId(),date,customer));
            order.getOrderDetails().stream().forEach(odtls -> {
                OrderDetail detail=new OrderDetail(order.getOrderId(),odtls.getCode(),odtls.getQty(),odtls.getUnitPrice());
                saveOrderDetail(detail);
                updateItemQty(odtls.getCode(),odtls.getQty());

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        session.getTransaction().commit();

    }

    public void saveOrderDetail(OrderDetail detail){
        try {
            orderDetailDAO.save(detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateItemQty(String code, int qty){
        try {
            Item item=itemDAO.find(code);
            item.setQtyOnHand(item.getQtyOnHand()-qty);
            itemDAO.update(item);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<SearchOrderDTO> getOrderInfo(String query) throws Exception {
        return null;
    }

    @Override
    public ArrayList<SearchOrderDTO> getAllOrderInformation() {
        //Session session=HibernateUtil.getSessionFactory().openSession();
        orderDAO.setSession(session);
        queryDAO.setSession(session);
        session.beginTransaction();
        ArrayList<SearchOrderDTO> searchOrderDTOS=new ArrayList<>();
        try {
            ArrayList<CustomEntity> customEntities=queryDAO.getALLOrderInformation();
            customEntities.stream().forEach(customEntity -> {
               searchOrderDTOS.add(new SearchOrderDTO(customEntity.getOrderId(),customEntity.getDate(),customEntity.getCustomerId(),customEntity.getCustomerName(),customEntity.getTotal()));
            });
            session.getTransaction().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchOrderDTOS;
    }
}
