package edu.miu.cs.cs544.examples.service;

import java.util.List;

import edu.miu.cs.cs544.examples.service.response.CountryResponse;

public interface AggregatorService {
    public List<CountryResponse> getAllCountries();
    public List<CountryResponse> defaultGetAllCountries();
}
