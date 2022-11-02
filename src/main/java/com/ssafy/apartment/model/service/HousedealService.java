package com.ssafy.apartment.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.dao.HouseDealDaoImpl;
import com.ssafy.apartment.model.dao.IHouseDealDao;

public interface HousedealService {

	List<HouseDealDto> list() throws SQLException;
    List<HouseDealDto> listAll(Map<String, String> map) throws SQLException;
}
