package com.ssafy.apartment.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apartment.model.HouseInfo;

public interface IHouseInfoDao {

    public List<HouseInfo> list() throws SQLException;
}
