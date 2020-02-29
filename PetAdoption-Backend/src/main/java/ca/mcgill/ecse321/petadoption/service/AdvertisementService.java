package ca.mcgill.ecse321.petadoption.service;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

@Service
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
     * @param petName
     * @param petAge
     * @param petDescription
     * @param petSex
     * @param petSpecies
     * @return Advertisement object
     */
    @Transactional
    public Advertisement createAdvertisement(String userEmail, Date datePosted, String petName,
                                             Integer petAge, String petDescription, Sex petSex, Species petSpecies) {
        AppUser user = appUserRepository.findAppUserByEmail(userEmail);
        if (user == null) {
            throw new IllegalArgumentException("User not found. Cannot make advertisement without user profile!");
        }
        //TODO: Check if date posted upon advertisement creation can be null or not
        if (datePosted == null) {
            throw new IllegalArgumentException("Date posted cannot be an empty field!");
        }
        advertisementParamCheck(petName, petAge, petDescription, petSex, petSpecies);
        Advertisement ad = new Advertisement();

        ad.setDatePosted(datePosted);
        ad.setIsExpired(false); // Advertisement on creation is not expired
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
        return new ArrayList<>((Collection<? extends Advertisement>) advertisementRepository.findAll());
    }

    /**
     * Returns all advertisements made by an App-user
     *
     * @param appUser
     * @return List of Advertisements made by App-user
     */
    @Transactional
    public List<Advertisement> getAdvertisementsOfAppUser(AppUser appUser) {
        return advertisementRepository.findAdvertisementsByPostedBy(appUser);
    }

    /**
     * Deletes the Advertisement with specified id from the database.
     *
     * @param id
     */
    @Transactional
    public boolean deleteAdvertisement(String id) {
        Advertisement adToDelete = advertisementRepository.findAdvertisementByAdvertisementId(id);
        if (adToDelete != null) {
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

    /**
     * Updates the isExpired attribute of Advertisement class in the database.
     *
     * @param isExpired
     * @return Advertisement
     */
    @Transactional
    public Advertisement updateAdvertisementIsExpired(String adId, boolean isExpired) {
        Advertisement advertisement = advertisementRepository.findAdvertisementByAdvertisementId(adId);
        if (advertisement == null) {
            throw new IllegalArgumentException("Invalid advertisement requested. Please check advertisement ID.");
        }
        advertisement.setIsExpired(isExpired);
        advertisementRepository.save(advertisement);
        return advertisement;
    }

    /**
     * Updates advertisement details for a pet in the database.
     * @param adId
     * @param petName
     * @param petAge
     * @param petDescription
     * @param petSex
     * @param petSpecies
     * @return Updated Advertisement
     */
    @Transactional
    public Advertisement updateAdvertisement(String adId, String petName, Integer petAge, String petDescription,
                                             Sex petSex, Species petSpecies) {
        Advertisement advertisement = advertisementRepository.findAdvertisementByAdvertisementId(adId);
        if (advertisement == null) {
            throw new IllegalArgumentException("Invalid advertisement requested. Please check advertisement ID.");
        }
        advertisementParamCheck(petName, petAge, petDescription, petSex, petSpecies);
        advertisement.setPetName(petName);
        advertisement.setPetAge(petAge);
        advertisement.setPetDescription(petDescription);
        advertisement.setPetSex(petSex);
        advertisement.setPetSpecies(petSpecies);
        advertisementRepository.save(advertisement);
        return advertisement;
    }

    private void advertisementParamCheck(String petName, Integer petAge, String petDescription,
                                         Sex petSex, Species petSpecies) {
        String error = "";
        if (petName == null || petName.trim().length() == 0) {
            error = error + "Pet name cannot be empty! ";
        }
        if (petAge <= 0) {
            error = error + "Pet age cannot be less than or equal to 0! ";
        }
        if (petDescription == null || petDescription.trim().length() == 0) {
            error = error + "Pet description cannot be empty! ";
        }
        if (petSex != Sex.F && petSex != Sex.M) {
            error = error + "Pet sex must be specified! ";
        }
        if (petSpecies != Species.bird && petSpecies != Species.cat && petSpecies != Species.dog &&
                petSpecies != Species.rabbit && petSpecies != Species.other) {
            error = error + "Pet Species is invalid! ";
        }
        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }
    }
}
