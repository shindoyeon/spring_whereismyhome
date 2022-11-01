package com.ssafy.member.model.service;

import java.util.List;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.dao.MemberDao;
import com.ssafy.member.model.dao.MemberDaoImpl;

public class MemberServiceImpl implements MemberService {
	
	private static MemberService memberService = new MemberServiceImpl();
	private MemberDao memberDao;
	
	private MemberServiceImpl() {
		memberDao = MemberDaoImpl.getMemberDao();
	}
	
	public static MemberService getMemberService() {
		return memberService;
	}

	@Override
	public int idCheck(String userId) throws Exception {
		return memberDao.idCheck(userId);
	}

	@Override
	public void joinMember(MemberDto memberDto) throws Exception {
		memberDao.joinMember(memberDto);
	}

	@Override
	public MemberDto loginMember(String userId, String userPwd) throws Exception {
		return memberDao.loginMember(userId, userPwd);
	}

	@Override
	public MemberDto getMember(String userId) throws Exception {
		return memberDao.getMember(userId);
	}

	@Override
	public void deleteMember(String userId) throws Exception{
		memberDao.deleteMember(userId);
		
	}

	@Override
	public void update(MemberDto memberDto) throws Exception {
		memberDao.updateMember(memberDto);
		
	}

	@Override
	public List<MemberDto> listMember() throws Exception {
		return memberDao.listMember();
	}

}
