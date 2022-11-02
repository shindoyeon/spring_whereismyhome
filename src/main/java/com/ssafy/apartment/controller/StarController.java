package com.ssafy.apartment.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.apartment.model.StarDto;
import com.ssafy.apartment.model.service.StarService;
import com.ssafy.member.model.MemberDto;

@Controller
@RequestMapping(value="/star")
public class StarController {
	
	private StarService starService;	//관심지역
	
	@Autowired
	public StarController(StarService starService) {
		this.starService = starService;
	}

	private static final Logger logger = LoggerFactory.getLogger(StarController.class);
	

	@DeleteMapping(value = "deletestar")
	private String deletestar(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if(memberDto != null) {
			try {
				int starNo = Integer.parseInt(request.getParameter("starno"));
				starService.deleteStar(starNo);
				return "/apartment/liststar";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "관심목록 삭제 중 에러발생!!!");
				return "/error/error.jsp";
			}
		} else {
			return "/user/login.jsp";
		}
	}

	@GetMapping(value="/liststar")
	private String liststar(Model model) {
//		HttpSession session = request.getSession();
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		if(memberDto != null) {
			try {
				List<StarDto> list = starService.listStar("ssafy");
				model.addAttribute("stars", list);
				return "/apartment/liststar";
			} catch (Exception e) {
				e.printStackTrace();
				//request.setAttribute("msg", "관심지역목록 얻기 중 에러발생!!!");
				return "/error/error";
			}
//		} else {
//			return "/user/login.jsp";
//		}
	}

	@GetMapping(value = "/mvaptapi")
	public String mvaptapi(Model model) throws Exception {
		logger.debug("ApartmentController ! mvaptapi  ");
		return "/apartment/aptapi";
	}
	
	@PostMapping(value="addstar")
	private String addstar(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if(memberDto != null) {
			StarDto starDto = new StarDto();
			starDto.setUserId(memberDto.getUserId());
			starDto.setDongCode(request.getParameter("dong"));
			starDto.setDealYM(request.getParameter("year")+request.getParameter("month"));
			try {
				starService.addStar(starDto);
				return "/apartment/liststar";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "즐겨찾기 추가 중 에러발생!!!");
				return "/error/error.jsp";
			}
		} else {
			return "/user/login.jsp";
		}
	}
}
