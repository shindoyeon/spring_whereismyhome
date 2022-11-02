package com.ssafy.apartment.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.service.HousedealService;


@Controller
@RequestMapping(value="/house")
public class HousedealController {
	
	private HousedealService housedealService;	//관심지역
	
	@Autowired
	public HousedealController(HousedealService housedealService) {
		this.housedealService = housedealService;
	}

	private static final Logger logger = LoggerFactory.getLogger(HousedealController.class);

	@GetMapping(value = "/mvaptapi")
	public String mvaptapi(Model model) throws Exception {
		logger.debug("ApartmentController ! mvaptapi  ");
		return "/apartment/aptapi";
	}

	@GetMapping(value = "/search")
	public String search(@RequestParam Map<String, String> map, Model model) throws Exception {
		logger.debug("ApartmentController ! search : {}", map);
		
		if(map.get("apartmentName")==null) {
			logger.debug("ApartmentController ! apartmentName null : {}", map);
		}
		else {
			logger.debug("ApartmentController ! apartmentName null XX : {}", map);
		}
		List<HouseDealDto> list = housedealService.list();
		System.out.println(list.size());
		return "/apartment/aptapi";
	}
	
}
