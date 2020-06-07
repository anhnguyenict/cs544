package edu.miu.cs.cs544.examples.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import edu.miu.cs.cs544.examples.service.response.CountryResponse;

@Service
public class AggregatorServiceImpl implements AggregatorService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient eurekaClient;

    @Value("${geography-service.service-name}")
    private String geographyServiceName;

    @Value("${geography-service.username}")
    private String username;

    @Value("${geography-service.password}")
    private String password;

    private List<CountryResponse> countryListCache = new ArrayList<>();

    @Override
    @HystrixCommand(fallbackMethod = "defaultGetAllCountries")
    public List<CountryResponse> getAllCountries() {
        String url = lookupUrlFor(geographyServiceName) + "/countries";
        countryListCache = Arrays.asList(
                restTemplate.exchange(url, HttpMethod.GET, createHttpEntity(), CountryResponse[].class).getBody());
        return countryListCache;
    }

    public List<CountryResponse> defaultGetAllCountries() {
        return countryListCache;
    }

    private HttpEntity<Object> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(username, password);

        return new HttpEntity<>(headers);
    }

    private String lookupUrlFor(String appName) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
        return instanceInfo.getHomePageUrl();
    }

}
