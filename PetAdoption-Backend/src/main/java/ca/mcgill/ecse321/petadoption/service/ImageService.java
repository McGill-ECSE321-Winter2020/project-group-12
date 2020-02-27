package ca.mcgill.ecse321.petadoption.service;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

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
     * @param advertisement1
     * @param name(String)
     * @param link(String)
     * @param id(String)
     * @return Image object
     */
    @Transactional
    public Image createImage(Advertisement advertisement1, String name, String link, String id) {
        Image image = new Image();
        String error = "";

        if (advertisement1 == null) {
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

        image.setImageId(id);
        image.setName(name);
        image.setLink(link);
        image.setAdvertisement(advertisement1);

        imageRepository.save(image);
        return image;
    }


    /**
     * Returns the Image with specified id from the database.
     *
     * @param id
     * @return Image object
     */
    @Transactional
    public Image getImageByID(String id) {
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("Image must have an ID");
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
        return toList(imageRepository.findAll());
    }

    /////////////////////////////Image Delete Method////////////////////////////////////////

    /**
     * Deletes the image with specified id from the database.
     *
     * @param id
     */
    @Transactional
    public void deleteImage(String id) {
        imageRepository.deleteImageByImageId(id);
    }

}
