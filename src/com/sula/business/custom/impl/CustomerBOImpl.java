package com.sula.business.custom.impl;

import com.sula.business.custom.CustomerBO;
import com.sula.dao.DAOFactory;
import com.sula.dao.DAOTypes;
import com.sula.dao.custom.CustomerDAO;
import com.sula.db.HibernateUtil;
import com.sula.dto.CustomerDTO;
import com.sula.entity.Customer;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("customerboimpl")
@Transactional
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    Session session;

    @Autowired
    CustomerDAO customerDAO;//= DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);


    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {

        try  {
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
            session.getTransaction().commit();
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"An Error").show();
        }
        finally {

            //session.close();
        }

    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {

        try  {
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
            session.getTransaction().commit();
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"An Error").show();
        }
        finally {

            //session.close();
        }

    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {

        try  {
            customerDAO.setSession(session);
            //check orders before delete a customer!!!
            session.beginTransaction();

            customerDAO.delete(customerId);
            session.getTransaction().commit();
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"An Error").show();
        }
        finally {

           // session.close();
        }

    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        try  {

            customerDAO.setSession(session);
            session.beginTransaction();
            List<Customer> alCustomers = customerDAO.findAll();
            List<CustomerDTO> dtos = new ArrayList<>();
            for (Customer customer : alCustomers) {
                dtos.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress()));
            }
            session.getTransaction().commit();
            return dtos;
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"An Error").show();
        }
        finally {

           // session.close();
        }
        return null;
    }

    @Override
    public String getLastCustomerId() throws Exception {
        try  {
            customerDAO.setSession(session);
            session.beginTransaction();
            String lastCustomerId = customerDAO.getLastCustomerId();
            session.getTransaction().commit();
            return lastCustomerId==null ? "C000" : lastCustomerId;
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"An Error").show();
        }
        finally {

           // session.close();
        }
        return null;
    }

    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {
        return null;
    }

    @Override
    public List<String> getAllCustomerIDs() throws Exception {
        return null;
    }
}
