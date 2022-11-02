package com.ssafy.apartment.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apartment.model.StarDto;

@Mapper
public interface StarMapper {
	
	void addStar(StarDto starDto) throws SQLException;
	List<StarDto> listStar(String userId) throws SQLException;
	void deleteStar(int starNo) throws SQLException;
}
