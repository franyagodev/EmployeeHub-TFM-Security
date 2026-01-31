package comjalbertolrz.city_service.controller;

import comjalbertolrz.city_service.model.City;
import comjalbertolrz.city_service.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cities")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<?> findAllCities(){
        return ResponseEntity.ok().body(cityService.findAllCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCityById(@PathVariable Long id){
        return ResponseEntity.ok().body(cityService.findCityById(id));
    }

    @PostMapping
    public ResponseEntity<?> createCity(@RequestBody City city){
        City newCity=cityService.createCity(city);
        return ResponseEntity.created(URI.create("/api/v1/cities/"+newCity.getId())).body(newCity);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateCity(@PathVariable Long id , @RequestBody City city){
       return ResponseEntity.ok().body(cityService.updateCity(id,city));

    }



}
