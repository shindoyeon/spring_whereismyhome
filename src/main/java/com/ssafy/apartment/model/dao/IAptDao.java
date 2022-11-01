package com.ssafy.apartment.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apartment.model.Apt;

public interface IAptDao {
	public int insert(Apt apt) throws SQLException;
	public List<Apt> list() throws SQLException;
}
