package edu.miu.cs.cs544.examples.service;

import java.util.List;

import edu.miu.cs.cs544.examples.domain.Country;

public interface CountryService {
    Country getCountryById(Integer id);
    Country getCountryByName(String name);
    List<Country> getAllCountries();
}
