package com.ssafy.apartment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ssafy.apartment.model.HouseDeal;
import com.ssafy.apartment.model.StarDto;
import com.ssafy.apartment.model.service.ApartmentService;
import com.ssafy.apartment.model.service.ApartmentServiceImpl;
import com.ssafy.apartment.model.service.HouseDealService;
import com.ssafy.member.model.MemberDto;

@Controller
@RequestMapping(value="apartment")
public class ApartmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ApartmentService apartmentService;
	HouseDealService hdsservice = HouseDealService.getInstance(); //일단 이렇게
	
	public void init() {
		apartmentService = ApartmentServiceImpl.getApartmentService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act = request.getParameter("act");
		
		String path = "/index.jsp";
		if("addstar".equals(act)){
			path = addstar(request, response);
			forward(request, response, path);
		} else if("liststar".equals(act)){
			path = liststar(request, response);
			forward(request, response, path);
		} else if("deletestar".equals(act)){
			path = deletestar(request, response);
			forward(request, response, path);
		} else if("mvaptapi".equals(act)){
			path = "/apartment/aptapi.jsp";
			redirect(request, response, path);
		} else if("search".equals(act)){
			search(request, response);
		} else {
			redirect(request, response,path);
		}
	}
	
	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");

		String dong = request.getParameter("dong");
		String ym = request.getParameter("DEAL_YMD");
		String aptName = request.getParameter("aptName");
		
		XmlMapper xmlMapper = new XmlMapper();
        String xml="";
        try {
            xml = xmlMapper.writeValueAsString(hdsservice.listAll(dong, ym, aptName));
        } catch (Exception e) {

        }
        PrintWriter out = response.getWriter();
        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");
        out.print(xml);
        out.flush(); 
		
	}

	private String deletestar(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if(memberDto != null) {
			try {
				int starNo = Integer.parseInt(request.getParameter("starno"));
				apartmentService.deleteStar(starNo);
				return "/apartment?act=liststar";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "관심목록 삭제 중 에러발생!!!");
				return "/error/error.jsp";
			}
		} else {
			return "/user/login.jsp";
		}
	}

	private String liststar(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if(memberDto != null) {
			try {
				List<StarDto> list = apartmentService.listStar(memberDto.getUserId());
				request.setAttribute("stars", list);
				return "/apartment/liststar.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "관심지역목록 얻기 중 에러발생!!!");
				return "/error/error.jsp";
			}
		} else {
			return "/user/login.jsp";
		}
	}

	private String addstar(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if(memberDto != null) {
			StarDto starDto = new StarDto();
			starDto.setUserId(memberDto.getUserId());
			starDto.setDongCode(request.getParameter("dong"));
			starDto.setDealYM(request.getParameter("year")+request.getParameter("month"));
			try {
				apartmentService.addStar(starDto);
				return "/apartment?act=liststar";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "즐겨찾기 추가 중 에러발생!!!");
				return "/error/error.jsp";
			}
		} else {
			return "/user/login.jsp";
		}
	}

	private void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
	
	private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		response.sendRedirect(request.getContextPath() + path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}
	
}
