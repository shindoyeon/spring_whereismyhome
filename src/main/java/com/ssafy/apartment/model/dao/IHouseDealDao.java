package com.ssafy.apartment.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apartment.model.HouseDeal;

public interface IHouseDealDao {

    public List<HouseDeal> list() throws SQLException;
    public List<HouseDeal> listAll(String dong,String ym, String aptName) throws SQLException;
}
