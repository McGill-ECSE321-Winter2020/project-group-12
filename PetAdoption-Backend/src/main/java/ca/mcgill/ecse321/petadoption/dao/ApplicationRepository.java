package ca.mcgill.ecse321.petadoption.dao;


import ca.mcgill.ecse321.petadoption.model.Application;
import org.springframework.data.repository.CrudRepository;


public interface ApplicationRepository extends CrudRepository<Application, Integer> {

    Application findApplicationById(Integer id);

}
