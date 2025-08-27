package com.first.journalapp.service;

import com.first.journalapp.api_response.WeatherResponse;
import com.first.journalapp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}") // API key is stored in the application.yml file
    private String apiKey; //we don't pass the actual key value here instead we call it through @Value

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city){
        String finalAPI = appCache.APP_CACHE.get("weather_api").replace("<apikey>", apiKey).replace("<city>", city);
        System.out.println("Final API key is "+ finalAPI);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
