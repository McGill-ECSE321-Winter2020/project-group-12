package ca.mcgill.ecse321.petadoption.dao;


import ca.mcgill.ecse321.petadoption.model.Application;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ApplicationRepository extends CrudRepository<Application, String> {

    Application findApplicationByApplicationId(String id);

    void deleteApplicationByApplicationId(String id);

    Application findApplicationByAdvertisement_AdvertisementIdAndApplicant_Email(String id, String email);

    List<Application> findApplicationByAdvertisement_AdvertisementId(String id);
}
