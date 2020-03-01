package ca.mcgill.ecse321.petadoption.dao;

import ca.mcgill.ecse321.petadoption.model.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ImageRepository extends CrudRepository<Image, Long>{

    Image findImageByName(String name);
    Image findImageByLink(String link);
    Image findImageByImageId(String id);
    void deleteImageByImageId(String id);
    List<Image> findImagesByAdvertisement_AdvertisementId(String id);
}
