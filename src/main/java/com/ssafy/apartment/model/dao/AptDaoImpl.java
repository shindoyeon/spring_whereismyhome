package com.ssafy.apartment.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.apartment.model.Apt;
import com.ssafy.util.DBUtil;

public class AptDaoImpl implements IAptDao {

	DBUtil db;
	private static IAptDao aptdao = new AptDaoImpl();
	
	private AptDaoImpl(){
		db=DBUtil.getInstance();
	}
	
	public static IAptDao getInstance() {
		return aptdao;
	}
	
	
	
	@Override
	public int insert(Apt apt) throws SQLException {
		
		
		StringBuilder sql=new StringBuilder();
		sql.append(" insert into apt(dong,apt_name,floor,size,price) ");
		sql.append(" values(?,?,?,?,?) ");
		Connection conn=null;
		PreparedStatement psmt=null;
		int count=0;
		try {
			conn=db.getConnection();
			psmt=conn.prepareStatement(sql.toString());
			int i=1;
			psmt.setString(i++, apt.getDong());
			psmt.setString(i++, apt.getApt_name());
			psmt.setString(i++, apt.getFloor());
			psmt.setString(i++, apt.getSize());
			psmt.setString(i++, apt.getPrice());
			count=psmt.executeUpdate();
			
		} finally {
			db.close(psmt, conn);
		}
		return count;
	}

	@Override
	public List<Apt> list() throws SQLException{
		StringBuilder sql=new StringBuilder();
		sql.append(" select dong,apt_name,floor,size,price ");
		sql.append(" from apt order by dong,apt_name ");
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		List<Apt> apts=new ArrayList<>();
		try {
			conn=db.getConnection();
			psmt=conn.prepareStatement(sql.toString());
			rs=psmt.executeQuery();
			while(rs.next()) {
				int i=1;
				Apt apt=new Apt(
						rs.getString(i++),
						rs.getString(i++),
						rs.getString(i++), 
						rs.getString(i++), 
						rs.getString(i++));
				apts.add(apt);
			}
		} finally {
			db.close(rs,psmt, conn);
		}
		return apts;
	}

}
