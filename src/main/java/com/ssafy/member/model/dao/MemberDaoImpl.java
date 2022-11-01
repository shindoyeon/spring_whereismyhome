package com.ssafy.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.member.model.MemberDto;
import com.ssafy.util.DBUtil;

public class MemberDaoImpl implements MemberDao {
	
	private static MemberDao memberDao = new MemberDaoImpl();
	private DBUtil dbUtil;
	
	private MemberDaoImpl() {
		dbUtil = DBUtil.getInstance();
	}
	
	public static MemberDao getMemberDao() {
		return memberDao;
	}

	@Override
	public int idCheck(String userId) throws SQLException {
		int cnt = 1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select count(user_id) \n");
			sql.append("from members \n");
			sql.append("where user_id = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			rs.next();
			cnt = rs.getInt(1);
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return cnt;
	}

	@Override
	public void joinMember(MemberDto memberDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into members (user_id, user_name, user_password, user_email, user_phone, user_role) \n");
			sql.append("values (?, ?, md5(?), ?, ?, ?) ");
			pstmt = conn.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setString(++idx, memberDto.getUserId());
			pstmt.setString(++idx, memberDto.getUserName());
			pstmt.setString(++idx, memberDto.getUserPwd());
			pstmt.setString(++idx, memberDto.getUserEmail());
			pstmt.setString(++idx, memberDto.getUserPhone());
			pstmt.setString(++idx, memberDto.getUserRole());
			
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
		
	}

	@Override
	public MemberDto loginMember(String userId, String userPwd) throws SQLException {
		MemberDto memberDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select user_name, user_password, user_role \n");
			sql.append("from members \n");
			sql.append("where user_id = ? and user_password = md5(?) and user_del = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			pstmt.setInt(3, 0);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				memberDto = new MemberDto();
				memberDto.setUserId(userId);
				memberDto.setUserName(rs.getString("user_name"));
				memberDto.setUserRole(rs.getString("user_role"));
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return memberDto;
	}

	@Override
	public MemberDto getMember(String userId) throws SQLException {
		MemberDto memberDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select user_id, user_name, user_password, user_email, user_phone, user_role \n");
			sql.append("from members \n");
			sql.append("where user_id = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				memberDto = new MemberDto();
				memberDto.setUserId(rs.getString("user_id"));
				memberDto.setUserName(rs.getString("user_name"));
				memberDto.setUserPwd(rs.getString("user_password"));
				memberDto.setUserEmail(rs.getString("user_email"));
				memberDto.setUserPhone(rs.getString("user_phone"));
				memberDto.setUserRole(rs.getString("user_role"));
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return memberDto;
	}

	
	@Override
	public void deleteMember(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update members set user_del = 1 \n");
			sql.append("where user_id = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userId);
			
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
		
	}

	@Override
	public void updateMember(MemberDto memberDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println(memberDto);
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update members set user_name = ?, user_password = md5(?), user_email = ?, user_phone = ? \n");
			sql.append("where user_id = ? ");
			System.out.println(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setString(++idx, memberDto.getUserName());
			pstmt.setString(++idx, memberDto.getUserPwd());
			pstmt.setString(++idx, memberDto.getUserEmail());
			pstmt.setString(++idx, memberDto.getUserPhone());
			pstmt.setString(++idx, memberDto.getUserId());
			
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
		
	}

	@Override
	public List<MemberDto> listMember() throws SQLException {
		List<MemberDto> list = new ArrayList<MemberDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select user_id, user_name, user_email, user_phone, user_role, user_del \n");
			sql.append("from members \n");
			sql.append("order by user_role asc \n");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberDto memberDto = new MemberDto();
				memberDto.setUserId(rs.getString("user_id"));
				memberDto.setUserName(rs.getString("user_name"));
				memberDto.setUserEmail(rs.getString("user_email"));
				memberDto.setUserPhone(rs.getString("user_phone"));
				memberDto.setUserRole(rs.getString("user_role"));
				memberDto.setUserDel(rs.getString("user_del"));
				
				list.add(memberDto);
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return list;

	}

}
