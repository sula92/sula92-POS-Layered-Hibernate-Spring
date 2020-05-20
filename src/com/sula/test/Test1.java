package com.sula.test;

import com.sula.Appinitializer;
import com.sula.dao.DAOFactory;
import com.sula.dao.DAOTypes;
import com.sula.dao.custom.OrderDAO;
import com.sula.dao.custom.OrderDetailDAO;
import com.sula.dao.custom.impl.OrderDAOImpl;
import com.sula.db.HibernateUtil;
import com.sula.entity.Customer;
import com.sula.entity.Order;
import com.sula.entity.OrderDetail;
import org.hibernate.Session;

import java.sql.Date;
import java.time.LocalDate;

public class Test1 {

    public static void main(String[] args) {

        OrderDAO orderDAO= Appinitializer.ctx.getBean(OrderDAOImpl.class);//= DAOFactory.getInstance().getDAO(DAOTypes.ORDER);
        OrderDetailDAO orderDetailDAO = Appinitializer.ctx.getBean(OrderDetailDAO.class);//DAOFactory.getInstance().getDAO(DAOTypes.ORDERDETAIL);

        Session session=HibernateUtil.getSessionFactory().openSession();
        orderDAO.setSession(session);
        orderDetailDAO.setSession(session);

        session.beginTransaction();

        LocalDate localDate=LocalDate.now();
        Date date=Date.valueOf(localDate);
        System.out.println(date);
        //List<OrderDetail> orderDetails= new ArrayList<>();
        OrderDetail detail=new OrderDetail("O030","I002",3,20.0);
        Customer customer=new Customer("C003","xxx","add");
        try {
            orderDAO.save(new Order("O030",date,customer));
            orderDetailDAO.save(detail);

        } catch (Exception e) {
            e.printStackTrace();
        }
        session.getTransaction().commit();
    }




}
