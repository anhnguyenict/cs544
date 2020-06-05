package edu.miu.cs.cs544.examples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.cs.cs544.examples.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
	
	List<City> findByCountryId(Integer countryId);
	
}
