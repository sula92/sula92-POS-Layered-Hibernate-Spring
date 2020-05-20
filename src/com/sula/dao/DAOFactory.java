package com.sula.dao;

import com.sula.Appinitializer;
import com.sula.dao.custom.impl.*;


//there is no need of daofactory with the spring since Application context work as the factory which can generate any type of
//object (DAO,BO etc..)

public class DAOFactory {
   /* private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    *//*public static DAOFactory getInstance(){
        return (daoFactory==null)? new DAOFactory() : daoFactory;
    }

   *//**//* public <T extends SuperDAO> T getDAO(DAOTypes daoType){
        switch (daoType){
            case CUSTOMER:
                return (T) Appinitializer.ctx.getBean(CustomerDAOImpl.class); //new CustomerDAOImpl();
            case ORDER:
                return (T) Appinitializer.ctx.getBean(OrderDAOImpl.class);//new OrderDAOImpl();
            case ITEM:
                return (T) Appinitializer.ctx.getBean(ItemDAOImpl.class);//new ItemDAOImpl();
            case ORDERDETAIL:
                return (T) Appinitializer.ctx.getBean(OrderDetailDAOImpl.class);//new OrderDetailDAOImpl();
            case QUERY:
                return (T) Appinitializer.ctx.getBean(QueryDAOImpl.class);//new QueryDAOImpl();
            default:
                return null;
        }
    }*//*
*/
}
