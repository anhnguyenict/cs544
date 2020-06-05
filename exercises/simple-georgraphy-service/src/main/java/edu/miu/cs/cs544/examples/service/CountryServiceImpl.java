package edu.miu.cs.cs544.examples.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.miu.cs.cs544.examples.domain.Country;
import edu.miu.cs.cs544.examples.repository.CountryRepository;
import edu.miu.cs.cs544.examples.service.response.CountryResponse;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {
	
	@Autowired
	private CountryRepository countryRepository;

	@Override
	public List<CountryResponse> getAllCountries() {
		return countryRepository.findAll()
				.stream()
				.parallel()
				.map(this::convertCountryToCountryResponse)
				.collect(Collectors.toList());
	}

	@Override
	public CountryResponse getCountryById(Integer id) {
		return countryRepository.findById(id)
				.map(this::convertCountryToCountryResponse).get();
	}

	@Override
	public CountryResponse getCountryByName(String name) {
		return convertCountryToCountryResponse(countryRepository.findByName(name));
	}
	
	private CountryResponse convertCountryToCountryResponse(Country country) {
		return new CountryResponse(country.getId(), country.getName());
	}

}
