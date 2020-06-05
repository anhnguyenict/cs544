package edu.miu.cs.cs544.examples.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.cs.cs544.examples.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
	
	Country findByName(String name);

}
