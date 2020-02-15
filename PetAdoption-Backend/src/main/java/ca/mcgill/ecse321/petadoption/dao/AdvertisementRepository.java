package ca.mcgill.ecse321.petadoption.dao;

import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {

    Advertisement findAdvertisementByAdvertisementId(String id);

    List<Advertisement> findAdvertisementsByPostedBy(AppUser postedBy);

    void deleteAdvertisementById(String id);
}
