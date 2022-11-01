package com.ssafy.apartment.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.apartment.model.HouseInfo;
import com.ssafy.util.DBUtil;

public class HouseInfoDaoImpl implements IHouseInfoDao {
    DBUtil db;
    private static IHouseInfoDao houseinfodao = new HouseInfoDaoImpl();

    private HouseInfoDaoImpl() {
        db = DBUtil.getInstance();
    }

    public static IHouseInfoDao getInstance() {
        return houseinfodao;
    }

    @Override
    public List<HouseInfo> list() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" select aptCode,aptName,dongCode,dongName,buildYear,jibun,lat,lng,img ");
        sql.append(" from houseinfo order by aptCode ");
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        List<HouseInfo> houseinfos = new ArrayList<>();
        try {
            conn = db.getConnection();
            psmt = conn.prepareStatement(sql.toString());
            rs = psmt.executeQuery();
            while (rs.next()) {
                int i = 1;
                HouseInfo houseinfo = new HouseInfo(
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++),
                        rs.getString(i++));
                houseinfos.add(houseinfo);
            }
        } finally {
            db.close(rs, psmt, conn);
        }
        return houseinfos;
    }

}
