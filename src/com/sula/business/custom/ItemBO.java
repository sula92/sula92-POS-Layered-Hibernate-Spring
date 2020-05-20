package com.sula.business.custom;

import com.sula.business.SuperBO;
import com.sula.dto.ItemDTO;

import java.util.List;

public interface ItemBO extends SuperBO {

    void saveItem(ItemDTO itemDTO) throws Exception;

    void updateItem(ItemDTO item) throws Exception;

    void deleteItem(String itemCode) throws Exception;

    List<ItemDTO> findAllItems() throws Exception;

    String getLastItemCode() throws Exception;

    ItemDTO findItem(String itemCode) throws Exception;

    List<String> getAllItemCodes() throws Exception;

}
