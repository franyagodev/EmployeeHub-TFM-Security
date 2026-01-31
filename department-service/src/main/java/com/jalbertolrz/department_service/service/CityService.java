package com.jalbertolrz.department_service.service;

import com.jalbertolrz.department_service.clients.CityClient;
import com.jalbertolrz.department_service.model.City;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.rmi.server.LogStream.log;

@Service
@AllArgsConstructor
@Slf4j
public class CityService {
    private final CityClient cityClient;

    @CircuitBreaker(name = "department-service",fallbackMethod = "fallbackFindCityById")
    @Retry(name = "departement-service")
    public City findCityById(Long id){
        return cityClient.getCity(id);
    }

    private City fallbackFindCityById(Long id,Throwable ex){
        System.out.println("Fallback ejecutado: city-service no disponible. " +
                "Exception: " + ex.getMessage());
        return null;
    }


}
