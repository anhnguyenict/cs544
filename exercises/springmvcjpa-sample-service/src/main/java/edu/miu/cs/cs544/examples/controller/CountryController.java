package edu.miu.cs.cs544.examples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.cs.cs544.examples.domain.Country;
import edu.miu.cs.cs544.examples.service.CountryService;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;
    
    @GetMapping("/countries")
    public List<Country> getAllCountries(){
        return countryService.getAllCountries();
    }
    
    @GetMapping("/list")
    public List<Country> getAllCountries2(){
        return countryService.getAllCountries();
    }
    
    @GetMapping("/countries/{id}")
    public Country getCountryById(@PathVariable Integer id){
        return countryService.getCountryById(id);
    }
    
    @GetMapping("/{id}")
    public Country getCountryById2(@PathVariable Integer id){
        return countryService.getCountryById(id);
    }
    
    @GetMapping(value = "/countries", params = {"name"})
    public Country getCountryByName(@RequestParam String name){
        return countryService.getCountryByName(name);
    }
}
