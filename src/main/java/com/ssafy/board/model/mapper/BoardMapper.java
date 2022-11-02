package com.ssafy.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardDto;

@Mapper
public interface BoardMapper {
	void write(BoardDto board) throws Exception;
	void update(BoardDto board) throws Exception;
	void delete(int articleNo) throws Exception;
	
	void updateHit(int articleNo) throws Exception;
	
	List<BoardDto> list() throws Exception;
	BoardDto getBoard(int articleNo) throws Exception;

}
