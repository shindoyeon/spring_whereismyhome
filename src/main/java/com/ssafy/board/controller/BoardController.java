package com.ssafy.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.member.model.MemberDto;
import com.ssafy.util.*;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("/list")
	private String list(@RequestParam Map<String,String> map, HttpSession session, Model model) {
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		if (memberDto != null) {

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
				
				model.addAttribute("articles", list);
				model.addAttribute("lastpage", lastpage);
				model.addAttribute("endpage", endpage);
				model.addAttribute("startpageno", startpage);
			
				return "/board/list";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "글목록 얻기 중 에러발생!!!");
				return "/error/error";
			}
//		} else {
//			model.addAttribute("msg", "로그인이 필요합니다.");
//			return "/user/login";
//		}
	}
	
	@GetMapping("/mvwrite")
	private String mvWrite() {
		return "board/write";
	}

	@PostMapping("/write")
	private String write(BoardDto boardDto, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		if(memberDto != null) {
//			boardDto.setUserId(memberDto.getUserId());
			try {
				boardDto.setUserId("ssafy");
				boardService.writeArticle(boardDto);
				redirectAttributes.addAttribute("pgno",1);
				redirectAttributes.addAttribute("key","");
				redirectAttributes.addAttribute("word","");
				return "redirect:/board/list";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "글작성 중 에러발생!!!");
				return "/error/error";
			}
//		} else {
//			return "/user/login";
//		}
	}
	
	@GetMapping("/detail")
	private String view(@RequestParam Map<String,String> map, HttpSession session, Model model) {
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		if(memberDto != null) {
			try {
				int articleNo = Integer.parseInt(map.get("articleno"));
				BoardDto boardDto = boardService.getArticle(articleNo);
				boardService.updateHit(articleNo);
				model.addAttribute("article", boardDto);
				model.addAttribute("param", map);
				System.out.println(map);
				return "/board/view";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "글 얻기 중 에러발생!!!");
				return "/error/error";
			}
//		} else {
//			return "/user/login";
//		}
	}

	@GetMapping("/mvmodify")
	private String mvModify(@RequestParam Map<String,String> map, HttpSession session, Model model) {
		try {
//			MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//			if(memberDto != null) {
				BoardDto boardDto = boardService.getArticle(Integer.parseInt(map.get("articleno")));
				model.addAttribute("article", boardDto);
				System.out.println(map);
				return "/board/modify";
//			} else
//				return "/user/login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "글내용 얻는 중 문제 발생!!!");
			return "/error/error";
		}
	}

	@PostMapping("/modify")
	private String modify(@RequestParam("articleno") int articleNo, BoardDto boardDto, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		if(memberDto != null) {
			try {
				System.out.println(articleNo);
				System.out.println(boardDto);
				boardDto.setArticleNo(articleNo);
				boardService.modifyArticle(boardDto);
				redirectAttributes.addAttribute("pgno",1);
				redirectAttributes.addAttribute("key","");
				redirectAttributes.addAttribute("word","");
				return "redirect:/board/list";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "글수정 중 문제 발생!!!");
				return "/error/error";
			}
			
//		} else
//			return "/user/login";
	}


	@GetMapping("/delete")
	private String delete(@RequestParam("articleno") int articleNo, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		if(memberDto != null) {
			try {
				boardService.deleteArticle(articleNo);
				redirectAttributes.addAttribute("pgno",1);
				redirectAttributes.addAttribute("key","");
				redirectAttributes.addAttribute("word","");
				return "redirect:/board/list";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "글 삭제 중 에러발생!!!");
				return "/error/error";
			}
//		} else {
//			return "/user/login";
//		}
	}
	
}
