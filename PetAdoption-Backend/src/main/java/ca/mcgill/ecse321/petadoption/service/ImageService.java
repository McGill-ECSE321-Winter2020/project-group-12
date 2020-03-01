package ca.mcgill.ecse321.petadoption.service;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Image;
import ca.mcgill.ecse321.petadoption.model.Sex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

@Service
public class ImageService {

    @Autowired(required = true)
    AppUserRepository appUserRepository;
    @Autowired(required = true)
    ApplicationRepository applicationRepository;
    @Autowired(required = true)
    AdvertisementRepository advertisementRepository;
    @Autowired(required = true)
    DonationRepository donationRepository;
    @Autowired(required = true)
    ImageRepository imageRepository;

    /**
     * Creates and adds a new Image object to the database.
     *
     * @param advertisement_id
     * @param name(String)
     * @param link(String)
     * @param id(String)
     * @return Image object
     */
    @Transactional
    public Image createImage(String advertisement_id, String name, String link, String id) {
        Image image = new Image();
        String error = "";

        if (advertisement_id == null) {
            error = error + "A Image must have an Advertisement";
        }
        if (link == null || link.trim().length() == 0) {
            error = error + "link can not be empty ";
        }
        if (name == null || name.trim().length() == 0) {
            error = error + "name can not be empty ";
        }
        if (id == null || id.trim().length() == 0) {
            error = error + "id is invalid ";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }

        image.setImageId();
        image.setName(name);
        image.setLink(link);

        Advertisement ad = advertisementRepository.findAdvertisementByAdvertisementId(advertisement_id);
        image.setAdvertisement(ad);
        imageRepository.save(image);
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
        Image a = imageRepository.findImageByImageId(id);
        return a;
    }

    /**
     * Returns all Images in the database.
     *
     * @return List of Image objects
     */
    @Transactional
    public List<Image> getAllImages() {
        return new ArrayList<Image>((Collection<? extends Image>) imageRepository.findAll());
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
            b = true ;
        }

        return b;
    }

}
