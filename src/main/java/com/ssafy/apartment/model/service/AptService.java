package com.ssafy.apartment.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apartment.model.Apt;
import com.ssafy.apartment.model.dao.AptDaoImpl;
import com.ssafy.apartment.model.dao.IAptDao;

public class AptService {
	
	IAptDao aptdao = AptDaoImpl.getInstance();
	
	private static AptService aptservice = new AptService();
	
	private AptService(){
	}
	
	public static AptService getInstance() {
		return aptservice;
	}
	
	public int insert(Apt apt) throws SQLException {
		return aptdao.insert(apt);
	}

	public List<Apt> list() throws SQLException{
		return aptdao.list();
	}

}
