package com.sula.dao.custom;

import com.sula.dao.SuperDAO;
import com.sula.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {

    public ArrayList<CustomEntity> getALLOrderInformation() throws SQLException;

}
