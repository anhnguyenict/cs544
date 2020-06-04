package edu.miu.cs.cs544.examples.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.miu.cs.cs544.examples.domain.Country;
import edu.miu.cs.cs544.examples.repository.CountryRepositoy;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepositoy countryRepositoy;
    
    public Country getCountryById(Integer id) {
        return countryRepositoy.findById(id).get();
    }
    
    public Country getCountryByName(String name) {
        return countryRepositoy.findByName(name);
    }
    
    public List<Country> getAllCountries() {
        return countryRepositoy.findAll();
    }

}
