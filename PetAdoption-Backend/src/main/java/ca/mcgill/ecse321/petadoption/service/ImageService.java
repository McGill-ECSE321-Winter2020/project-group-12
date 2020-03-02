package ca.mcgill.ecse321.petadoption.service;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ImageService {

    @Autowired(required = true)
    AdvertisementRepository advertisementRepository;
    @Autowired(required = true)
    ImageRepository imageRepository;

    /**
     * Creates and adds a new Image object to the database.
     *
     * @param name(String)
     * @param link(String)
     * @param advertisement_id
     * @return Image object
     */
    @Transactional
    public Image createImage(String advertisement_id, String name, String link) {
        Image image = new Image();
        String error = "";
        //validate input
        if (advertisement_id == null || advertisement_id.length() == 0) {
            error = error + "A Image must have an Advertisement";
        }
        if (link == null || link.trim().length() == 0) {
            error = error + "link can not be empty ";
        }
        if (name == null || name.trim().length() == 0) {
            error = error + "name can not be empty ";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }
        //assign attributes
        image.setImageId();
        image.setName(name);
        image.setLink(link);

        Advertisement ad = advertisementRepository.findAdvertisementByAdvertisementId(advertisement_id);
        image.setAdvertisement(ad);
        return imageRepository.save(image);
    }


    /**
     * Returns the Image with specified id from the database.
     *
     * @param id
     * @return Image object
     */
    @Transactional
    public Image getImageByID(String id) {
        String error = "";
        if (id == null || id.trim().length() == 0) {
            error = "Image must have an ID";
        }
        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }
        Image image = imageRepository.findImageByImageId(id);
        if (image == null) {
            throw new IllegalArgumentException("There is no such Image!");
        }
        return image;
    }

    /**
     * Returns all Images of a specific advertisement.
     *
     * @return List of Image objects
     */
    @Transactional
    public List<Image> getAllImagesOfAdvertisement(Advertisement ad) {
        ArrayList<Image> listOfImages = new ArrayList<>(imageRepository.findImagesByAdvertisement_AdvertisementId(ad.getAdvertisementId()));
        return listOfImages;
    }


    /////////////////////////////Image Delete Method////////////////////////////////////////

    /**
     * Deletes the image with specified id from the database.
     *
     * @param id
     */
    @Transactional
    public boolean deleteImage(String id) {
        boolean b = false ;
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("You must provide an ID in order to delete!");
        }
        Image image = imageRepository.findImageByImageId(id);
        if(image == null){
            throw new IllegalArgumentException("The id you provided doesn't exist");
        }else{
            imageRepository.deleteImageByImageId(id);
            b = true;
        }
        return b;
    }
}
