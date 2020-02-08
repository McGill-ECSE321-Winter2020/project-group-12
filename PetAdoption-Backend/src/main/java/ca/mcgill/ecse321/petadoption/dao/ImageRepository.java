package ca.mcgill.ecse321.petadoption.dao;

import ca.mcgill.ecse321.petadoption.model.Image;
import org.springframework.data.repository.CrudRepository;


public interface ImageRepository extends CrudRepository<Image, Long>{

    Image findImageByImageId(Long id);
    Image findImageByName(String name);
}
