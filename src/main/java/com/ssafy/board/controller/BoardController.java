package com.ssafy.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.board.model.service.BoardServiceImpl;
import com.ssafy.member.model.MemberDto;
import com.ssafy.util.*;

@Controller("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BoardService boardService;
	private Map<String, String> map;
	
	public void init() {
		boardService = BoardServiceImpl.getBoardService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act = request.getParameter("act");
		
		int pgNo = ParameterCheck.notNumberToOne(request.getParameter("pgno"));
		String key = ParameterCheck.nullToBlank(request.getParameter("key"));
		String word = ParameterCheck.nullToBlank(request.getParameter("word"));
		String queryString = "?pgno=" + pgNo + "&key=" + key + "&word=" + word;
		map = new HashMap<>();
		map.put("pgno", pgNo + "");
		map.put("key", key);
		map.put("word", word);
		
		String path = "/index.jsp";
		if("list".equals(act)) {
			path = list(request, response);
			forward(request, response, path + queryString);
		} else if("mvwrite".equals(act)) {
			path = "/board/write.jsp";
			redirect(request, response, path);
		} else if("write".equals(act)) {
			path = write(request, response);
			forward(request, response, path);
		} else if("view".equals(act)) {
			path = view(request, response);
			forward(request, response, path + queryString);
		} else if("mvmodify".equals(act)) {
			path = mvModify(request, response);
			forward(request, response, path);
		} else if("modify".equals(act)) {
			path = modify(request, response);
			forward(request, response, path);
		} else if("delete".equals(act)) {
			path = delete(request, response);
			redirect(request, response, path);
		} else {
			redirect(request, response,path);
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
	
	private String list(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		//UserDto memberDto = (UserDto) session.getAttribute("userinfo");
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		
		if (memberDto != null) {
			try {
				List<BoardDto> list = boardService.listArticle(map);
				int articlecnt = boardService.totalArticleCount(map);
				
				int spl = SizeConstant.SIZE_PER_LIST;
				int pl = SizeConstant.LIST_SIZE;
				
				int pageno = Integer.parseInt(map.get("pgno"));
				
				int startpage = (pageno-1)/10*pl+1;
				int endpage = startpage+9;
				int lastpage = articlecnt/spl + 1;
				
				//33-20 < 10
				if(articlecnt-spl*startpage<spl*pl) { //전체 개수-현재페이지까지 개수가 지정된 페이지 수 보다 작으면
					endpage = articlecnt/spl+1;
				}
				
				request.setAttribute("articles", list);
				request.setAttribute("lastpage", lastpage);
				request.setAttribute("endpage", endpage);
				request.setAttribute("startpageno", startpage);
				
				return "/board/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글목록 얻기 중 에러발생!!!");
				return "/error/error.jsp";
			}
		} else {
			request.setAttribute("msg", "로그인이 필요합니다.");
			return "/user/login.jsp";
		}
	}

	private String write(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if(memberDto != null) {
			BoardDto boardDto = new BoardDto();
			boardDto.setUserId(memberDto.getUserId());
			boardDto.setSubject(request.getParameter("subject"));
			boardDto.setContent(request.getParameter("content"));
			try {
				boardService.writeArticle(boardDto);	
				return "/board?act=list&pgno=1&key=&word=";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글작성 중 에러발생!!!");
				return "/error/error.jsp";
			}
		} else {
			return "/user/login.jsp";
		}
	}
	
	private String view(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if(memberDto != null) {
			try {
				int articleNo = Integer.parseInt(request.getParameter("articleno"));
				BoardDto boardDto = boardService.getArticle(articleNo);
				boardService.updateHit(articleNo);
				request.setAttribute("article", boardDto);
				return "/board/view.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글 얻기 중 에러발생!!!");
				return "/error/error.jsp";
			}
		} else {
			return "/user/login.jsp";
		}
	}

	private String mvModify(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
			if(memberDto != null) {
				int articleNo = Integer.parseInt(request.getParameter("articleno"));
				BoardDto boardDto = boardService.getArticle(articleNo);
				request.setAttribute("article", boardDto);
				
				return "/board/modify.jsp";
			} else
				return "/user/login.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글내용 얻는 중 문제 발생!!!");
			return "/error/error.jsp";
		}
	}

	private String modify(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if(memberDto != null) {
			BoardDto boardDto = new BoardDto();
			boardDto.setArticleNo(Integer.parseInt(request.getParameter("articleno")));
			boardDto.setSubject(request.getParameter("subject"));
			boardDto.setContent(request.getParameter("content"));
			
			try {
				boardService.modifyArticle(boardDto);
				return "/board?act=list&pgno=1&key=&word=";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글수정 중 문제 발생!!!");
				return "/error/error.jsp";
			}
			
		} else
			return "/user/login.jsp";
	}


	private String delete(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if(memberDto != null) {
			try {
				int articleNo = Integer.parseInt(request.getParameter("articleno"));
				boardService.deleteArticle(articleNo);
				return "/board?act=list&pgno=1&key=&word=";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글 삭제 중 에러발생!!!");
				return "/error/error.jsp";
			}
		} else {
			return "/user/login.jsp";
		}
	}
	
}
