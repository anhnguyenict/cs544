package edu.miu.cs.cs544.examples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.cs.cs544.examples.service.CityService;
import edu.miu.cs.cs544.examples.service.response.CityResponse;

@RestController
@RequestMapping("/cities")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@GetMapping
	public List<CityResponse> getAllCities() {
		return cityService.getAllCities();
	}

}
