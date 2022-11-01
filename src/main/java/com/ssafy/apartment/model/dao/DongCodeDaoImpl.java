package com.ssafy.apartment.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.apartment.model.DongCode;
import com.ssafy.util.DBUtil;

public class DongCodeDaoImpl implements IDongCodeDao {

    DBUtil db;
    private static IDongCodeDao dongcodedao = new DongCodeDaoImpl();

    private DongCodeDaoImpl() {
        db = DBUtil.getInstance();
    }

    public static IDongCodeDao getInstance() {
        return dongcodedao;
    }

    @Override
    public List<DongCode> list() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" select dongCode,sidoName,gugunName,dongName ");
        sql.append(" from dongcode order by dongCode ");
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        List<DongCode> dongcodes = new ArrayList<>();
        try {
            conn = db.getConnection();
            psmt = conn.prepareStatement(sql.toString());
            rs = psmt.executeQuery();
            while (rs.next()) {
                int i = 1;
                DongCode dongcode = new DongCode(
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++));
                dongcodes.add(dongcode);
            }
        } finally {
            db.close(rs, psmt, conn);
        }
        return dongcodes;
    }

}
