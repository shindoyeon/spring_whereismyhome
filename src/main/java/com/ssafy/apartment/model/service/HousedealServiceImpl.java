package com.ssafy.apartment.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.dao.HouseDealDaoImpl;
import com.ssafy.apartment.model.dao.IHouseDealDao;
import com.ssafy.apartment.model.mapper.StarMapper;
import com.ssafy.apartment.model.mapper.HousedealMapper;

@Service
public class HousedealServiceImpl implements HousedealService{
	
	private HousedealMapper housedealMapper;
	
	@Autowired
	public HousedealServiceImpl(HousedealMapper housedealMapper) {
		this.housedealMapper = housedealMapper;
	}
	
	@Override
	public List<HouseDealDto> list() throws SQLException {
		return housedealMapper.list();
	}

	@Override
	public List<HouseDealDto> listAll(Map<String, String> map) throws SQLException {
		return housedealMapper.listAll(map);
	}

	
}
