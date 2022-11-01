package com.ssafy.member.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.member.model.MemberDto;

public interface MemberDao {

	int idCheck(String userId) throws SQLException;
	void joinMember(MemberDto memberDto) throws SQLException;
	MemberDto loginMember(String userId, String userPwd) throws SQLException;
	MemberDto getMember(String userId) throws SQLException;
	void deleteMember(String userId) throws SQLException;
	void updateMember(MemberDto memberDto) throws SQLException;
	List<MemberDto> listMember() throws SQLException;
	
}
