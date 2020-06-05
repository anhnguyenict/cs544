package edu.miu.cs.cs544.examples.service.response;

public class CityResponse {
	
	private Integer id;
	
	private String name;
	
	private CountryResponse country;

	public CityResponse(Integer id, String name, CountryResponse country) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CountryResponse getCountry() {
		return country;
	}

	public void setCountry(CountryResponse country) {
		this.country = country;
	}

}
