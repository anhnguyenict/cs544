package edu.miu.cs.cs544.examples.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import edu.miu.cs.cs544.examples.service.response.CountryResponse;

@Service
public class AggregatorServiceImpl implements AggregatorService {

    List<CountryResponse> countryListCache;

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private EurekaClient eurekaClient;
    
    @Value("${simple-geography-service}")
    private String geographyServiceName;
    
    @Override
    @HystrixCommand(fallbackMethod = "defaultGetAllCountries")
    public List<CountryResponse> getAllCountries() {
        countryListCache = restTemplate.getForObject(lookupUrlFor(geographyServiceName) + "/countries", List.class);
        return countryListCache;
    }

    public List<CountryResponse> defaultGetAllCountries() {
        return countryListCache;
    }
    
    private String lookupUrlFor(String appName) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
        return instanceInfo.getHomePageUrl();
    }

}
