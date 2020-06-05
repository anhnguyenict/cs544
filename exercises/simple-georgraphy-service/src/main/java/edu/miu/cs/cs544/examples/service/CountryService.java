package edu.miu.cs.cs544.examples.service;

import java.util.List;

import edu.miu.cs.cs544.examples.service.response.CountryResponse;

public interface CountryService {
	
	CountryResponse getCountryById(Integer id);
	
	CountryResponse getCountryByName(String name);

	List<CountryResponse> getAllCountries();

}
