package edu.miu.cs.cs544.examples.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.miu.cs.cs544.examples.domain.City;
import edu.miu.cs.cs544.examples.repository.CityRepository;
import edu.miu.cs.cs544.examples.service.response.CityResponse;
import edu.miu.cs.cs544.examples.service.response.CountryResponse;

@Service
@Transactional
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;

	@Override
	public List<CityResponse> getAllCities() {
		return cityRepository.findAll()
				.stream()
				.map(this::cityToCityResponseConverter)
				.collect(Collectors.toList());
	}

	@Override
	public List<CityResponse> getAllCitiesByCountryId(Integer countryId) {
		return cityRepository.findByCountryId(countryId)
			.stream()
			.map(this::cityToCityResponseConverter)
			.collect(Collectors.toList());
	}

	private CityResponse cityToCityResponseConverter(City city) {
		return new CityResponse(city.getId(), city.getName(),
				new CountryResponse(city.getCountry().getId(), city.getCountry().getName()));
	}

}
