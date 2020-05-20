package com.sula.business.custom;

import com.sula.business.SuperBO;
import com.sula.dto.OrderDTO;
import com.sula.dto.SearchOrderDTO;

import java.util.ArrayList;
import java.util.List;

public interface OrderBO extends SuperBO {

    String getLastOrderId() throws Exception;

    public void placeOrder(OrderDTO orderDTO) throws Exception;

    List<SearchOrderDTO> getOrderInfo(String query) throws Exception;

    ArrayList<SearchOrderDTO> getAllOrderInformation();
}
