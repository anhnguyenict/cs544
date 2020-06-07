package edu.miu.cs.cs544.examples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.cs.cs544.examples.service.AggregatorService;
import edu.miu.cs.cs544.examples.service.response.CountryResponse;

@RestController
public class AggregatorController {
    @Autowired
    private AggregatorService aggregatorService;

    @GetMapping("/countries")
    public List<CountryResponse> getAllCountries() {
        return aggregatorService.getAllCountries();
    }
}
