package ca.mcgill.ecse321.petadoption.service;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

public class AdvertisementService {

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

    @Autowired
    ApplicationService applicationService;

    @Autowired
    ImageService imageService;

    /**
     * Creates and adds a new Advertisement object to the database.
     *
     * @param userEmail
     * @param datePosted
     * @param isExpired
     * @param petName
     * @param petAge
     * @param petDescription
     * @param petSex
     * @param petSpecies
     * @return Advertisement object
     */
    @Transactional
    public Advertisement createAdvertisement(String userEmail, Date datePosted, boolean isExpired, String petName,
                                             Integer petAge, String petDescription, Sex petSex, Species petSpecies) {
        Advertisement ad = new Advertisement();
        String error = "";
        // Find user profile by unique ID.
        AppUser user = appUserRepository.findAppUserByEmail(userEmail);

        if (user == null) {
            error = error + "An Advertisement must have a AppUser ";
        }
        if (datePosted == null) {
            error = "datePosted can not be empty! ";
        }
        if (isExpired == true) {
            error = error + "isExpired cannot be true at this point! ";
        }
        if (petName == null || petName.trim().length() == 0) {
            error = error + "petName cannot be empty! ";
        }
        if (petAge <= 0) {
            error = error + "petAge cannot be less than or equal to 0! ";
        }
        if (petDescription == null || petDescription.trim().length() == 0) {
            error = error + "petDescription cannot be empty! ";
        }
        if (petSex != Sex.F && petSex != Sex.M) {
            error = error + "petSex must be either male or female!";
        }
        if (petSpecies != Species.bird && petSpecies != Species.cat && petSpecies != Species.dog &&
                petSpecies != Species.rabbit && petSpecies != Species.other) {
            error = error + "petSpecies is invalid! ";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }

        ad.setDatePosted(datePosted);
        ad.setIsExpired(isExpired);
        ad.setAdvertisementId();
        ad.setPetDescription(petDescription);
        ad.setPetName(petName);
        ad.setPetAge(petAge);
        ad.setPetSex(petSex);
        ad.setPetSpecies(petSpecies);
        //Setting associations with appUser, applications and images for the advertisement
        ad.setPostedBy(user);
        ad.setApplications(new HashSet<>());
        ad.setPetImages(new HashSet<>());

        advertisementRepository.save(ad);
        return ad;
    }

    /**
     * Returns the Advertisement with specified id from the database.
     *
     * @param id
     * @return Advertisement object
     */
    @Transactional
    public Advertisement getAdvertisementByID(String id) {
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("Advertisement must have an ID");
        }
        Advertisement a = advertisementRepository.findAdvertisementByAdvertisementId(id);
        return a;
    }

    /**
     * Returns all Advertisements in the database.
     *
     * @return List of Advertisement objects
     */
    @Transactional
    public List<Advertisement> getAllAdvertisements() {
        return toList(advertisementRepository.findAll());
    }

    /////////////////////////////Advertisement Delete Method////////////////////////////////////////

    /**
     * Deletes the Advertisement with specified id from the database.
     *
     * @param id
     */
    @Transactional
    public boolean deleteAdvertisement(String id) {
        Advertisement adToDelete = advertisementRepository.findAdvertisementByAdvertisementId(id);
        if (adToDelete != null){
            //Delete all multiple associations with an application
            while (adToDelete.getApplications().size() != 0) {
                Set<Application> applications = adToDelete.getApplications();
                Application app = applications.iterator().next();
                applicationService.deleteApplication(app.getApplicationId());
            }

            while (adToDelete.getPetImages().size() != 0) {
                Set<Image> petImages = adToDelete.getPetImages();
                Image image = petImages.iterator().next();
                imageService.deleteImage(image.getImageId());
            }

            advertisementRepository.delete(adToDelete);
            return true;
        }
        return false;
    }

//////////////////////////////Advertisement update method////////////////////////////////////
    /**
     * Updates the isExpired attribute of Advertisement class in the database.
     *
     * @param isExpired
     * @return Advertisement
     */
    @Transactional
    public Advertisement updateAdvertisementIsExpired(Advertisement ad, boolean isExpired) {
        ad.setIsExpired(isExpired);
        advertisementRepository.save(ad);
        return ad;
    }
}
