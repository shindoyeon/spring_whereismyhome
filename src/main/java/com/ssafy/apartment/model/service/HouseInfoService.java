package com.ssafy.apartment.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apartment.model.HouseInfo;
import com.ssafy.apartment.model.dao.HouseInfoDaoImpl;
import com.ssafy.apartment.model.dao.IHouseInfoDao;

public class HouseInfoService {

    IHouseInfoDao houseinfodao = HouseInfoDaoImpl.getInstance();

    private static HouseInfoService houseinfoservice = new HouseInfoService();

    private HouseInfoService() {
    }

    public static HouseInfoService getInstance() {
        return houseinfoservice;
    }

    public List<HouseInfo> list() throws SQLException {
        return houseinfodao.list();
    }

}
