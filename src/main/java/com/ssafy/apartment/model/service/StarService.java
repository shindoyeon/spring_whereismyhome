package com.ssafy.apartment.model.service;

import java.util.List;

import com.ssafy.apartment.model.StarDto;

public interface StarService {

	void addStar(StarDto starDto) throws Exception;
	List<StarDto> listStar(String userid) throws Exception;
	void deleteStar(int starNo) throws Exception;
}
