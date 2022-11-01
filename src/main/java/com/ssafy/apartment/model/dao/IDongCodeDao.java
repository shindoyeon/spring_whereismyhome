package com.ssafy.apartment.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apartment.model.DongCode;

public interface IDongCodeDao {
    public List<DongCode> list() throws SQLException;
}
