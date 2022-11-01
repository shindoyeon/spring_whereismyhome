package com.ssafy.apartment.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apartment.model.HouseDeal;
import com.ssafy.apartment.model.dao.HouseDealDaoImpl;
import com.ssafy.apartment.model.dao.IHouseDealDao;

public class HouseDealService {

    IHouseDealDao housedealdao = HouseDealDaoImpl.getInstance();

    private static HouseDealService housedealservice = new HouseDealService();

    private HouseDealService() {
    }

    public static HouseDealService getInstance() {
        return housedealservice;
    }

    public List<HouseDeal> list() throws SQLException {
        return housedealdao.list();
    }
    
    public List<HouseDeal> listAll(String dong,String ym, String aptName) throws SQLException {
        return housedealdao.listAll(dong, ym, aptName);
    }

}
