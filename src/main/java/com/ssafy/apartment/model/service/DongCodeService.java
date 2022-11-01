package com.ssafy.apartment.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apartment.model.DongCode;
import com.ssafy.apartment.model.dao.DongCodeDaoImpl;
import com.ssafy.apartment.model.dao.IDongCodeDao;

public class DongCodeService {

    IDongCodeDao dongcodedao = DongCodeDaoImpl.getInstance();

    private static DongCodeService dongcodeservice = new DongCodeService();

    private DongCodeService() {
    }

    public static DongCodeService getInstance() {
        return dongcodeservice;
    }

    public List<DongCode> list() throws SQLException {
        return dongcodedao.list();
    }

}
