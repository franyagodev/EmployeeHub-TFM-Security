package com.jalbertolrz.department_service.clients;


import com.jalbertolrz.department_service.model.City;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "city-service")
public interface CityClient {

    @GetMapping("/api/v1/cities/{id}")
    City getCity(@PathVariable("id") Long id);

}
