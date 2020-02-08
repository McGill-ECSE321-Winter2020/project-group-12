package ca.mcgill.ecse321.petadoption.dao;

import ca.mcgill.ecse321.petadoption.model.Image;
import org.springframework.data.repository.CrudRepository;


public interface ImageRepository extends CrudRepository<Image, Long>{

    Image findImageByName(String name);
    Image findImageByLink(String link);
    Image findImageByImageId(String id);
}
