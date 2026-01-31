package comjalbertolrz.city_service.exceptions;

public class CityNotFoundByIdException extends RuntimeException {
    public CityNotFoundByIdException(Long id) {
        super("City with Id: "+id+", not found");
    }
}
