package comjalbertolrz.city_service.service;

import comjalbertolrz.city_service.exceptions.CityNotFoundByIdException;
import comjalbertolrz.city_service.model.City;
import comjalbertolrz.city_service.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City findCityById(Long id){
        return cityRepository.findById(id).orElseThrow(()->new CityNotFoundByIdException(id));
    }

    public City createCity(City city){
        return cityRepository.save(city);
    }

    public List<City> findAllCities(){
        return cityRepository.findAll();
    }

    public City updateCity(Long id,City city){
        City cityDB= cityRepository.findById(id).orElseThrow(()->new CityNotFoundByIdException(id));
        cityDB.setCountry(city.getCountry());
        cityDB.setName(city.getName());
        cityDB.setPopulation(city.getPopulation());
        cityDB.setCountryCode(city.getCountryCode());
        City saveCity=cityRepository.save(cityDB);
        return saveCity;

    }





}
