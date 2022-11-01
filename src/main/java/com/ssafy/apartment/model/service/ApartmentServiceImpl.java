package com.ssafy.apartment.model.service;

import java.util.List;

import com.ssafy.apartment.model.StarDto;
import com.ssafy.apartment.model.dao.ApartmentDao;
import com.ssafy.apartment.model.dao.ApartmentDaoImpl;

public class ApartmentServiceImpl implements ApartmentService {
	
	private static ApartmentService apartmentService = new ApartmentServiceImpl();
	private ApartmentDao apratmentDao;
	
	private ApartmentServiceImpl() {
		apratmentDao = ApartmentDaoImpl.getApartmentDao();
	}

	public static ApartmentService getApartmentService() {
		return apartmentService;
	}

	@Override
	public void addStar(StarDto starDto) throws Exception {
		apratmentDao.addStar(starDto);
		
	}

	@Override
	public List<StarDto> listStar(String userid) throws Exception {
		return apratmentDao.listStar(userid);
	}

	@Override
	public void deleteStar(int starNo) throws Exception {
		apratmentDao.deleteStar(starNo);
		
	}

}
