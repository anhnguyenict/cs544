package edu.miu.cs.cs544.examples.service;

import java.util.List;

import edu.miu.cs.cs544.examples.service.response.CityResponse;

public interface CityService {
	
	List<CityResponse> getAllCities();
	
	List<CityResponse> getAllCitiesByCountryId(Integer countryId);
	
}
