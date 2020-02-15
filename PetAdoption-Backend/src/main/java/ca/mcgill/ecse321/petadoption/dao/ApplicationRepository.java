package ca.mcgill.ecse321.petadoption.dao;


import ca.mcgill.ecse321.petadoption.model.Application;
import org.springframework.data.repository.CrudRepository;


public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Application findApplicationByApplicationId(String id);

    void deleteApplicationById(String id);
}
