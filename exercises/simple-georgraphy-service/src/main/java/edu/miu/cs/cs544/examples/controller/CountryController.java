package edu.miu.cs.cs544.examples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.cs.cs544.examples.service.CityService;
import edu.miu.cs.cs544.examples.service.CountryService;
import edu.miu.cs.cs544.examples.service.response.CityResponse;
import edu.miu.cs.cs544.examples.service.response.CountryResponse;

@RestController
@RequestMapping("/countries")
public class CountryController {
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private CountryService countryService;
	
	@GetMapping
	public List<CountryResponse> getAllCountries() {
		return countryService.getAllCountries();
	}

	@GetMapping("/{id}")
	public CountryResponse getCountryById(@PathVariable Integer id) {
		return countryService.getCountryById(id);
	}
	
	@GetMapping(params = {"name"})
	public CountryResponse getCountryById(@RequestParam String name) {
		return countryService.getCountryByName(name);
	}
	
	@GetMapping("/{countryId}/cities")
	public List<CityResponse> getAllCitiesByCountryId(@PathVariable Integer countryId) {
		return cityService.getAllCitiesByCountryId(countryId);
	}


}
