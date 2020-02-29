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
     * @param advertisementID
     * @param name(String)
     * @param link(String)
     * @param id(String)
     * @return Image object
     */
    @Transactional
    public Image createImage(String advertisementID, String name, String link, String id) {
        Image image = new Image();
        String error = "";

        if (advertisementID == null) {
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
//        image.setAdvertisement(advertisementRepository.findAdvertisementByAdvertisementId(advertisementID));
        java.sql.Date datePosted = Date.valueOf(LocalDate.of(2020, Month.FEBRUARY, 7));

        AppUser newUser1 = new AppUser();
        newUser1.setEmail("dr.nafario@evil.com");
        newUser1.setName("dr.Nafario");
        newUser1.setAge(99);
        newUser1.setBiography("Male");
        newUser1.setHomeDescription("homeless");
        newUser1.setIsAdmin(true);
        newUser1.setPassword("abcdefg");
        newUser1.setSex(Sex.M);
        appUserRepository.save(newUser1);

        Advertisement newAd = new Advertisement();
        newAd.setAdvertisementId();
        newAd.setDatePosted(datePosted);
        newAd.setPetDescription("lovely,cute,adorable");
        newAd.setPetAge(2);
        newAd.setPetName("Mojo");
        newAd.setIsExpired(false);
        newAd.setPostedBy(newUser1);
        advertisementRepository.save(newAd);
        image.setAdvertisement(newAd);
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

    /**
     * Returns all Images of a specific advertisement.
     *
     * @return List of Image objects
     */
    @Transactional
    public List<Image> getAllImagesOfAdvertisement(Advertisement ad) {
        Set<Image> imageSet = ad.getPetImages();
        ArrayList<Image> listOfImages = new ArrayList<>(imageSet);
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

    /////////////////////////////Image Delete Method////////////////////////////////////////
    /**
     * update the name attribute of image class with specified id from the database.
     *
     * @param id
     * @param name
     */
    @Transactional
    public Image updateImageName(String id, String name) {
        Image image = imageRepository.findImageByImageId(id);
        image.setName(name);
        imageRepository.save(image);
        return image;
    }
}
