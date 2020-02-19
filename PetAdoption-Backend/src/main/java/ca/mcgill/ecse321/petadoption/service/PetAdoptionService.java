package ca.mcgill.ecse321.petadoption.service;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

/**
 * This class contains the database service methods.
 *
 * @author Bozhong Lu
 */

public class PetAdoptionService {
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
     * Creates and adds a new Advertisement object to the database.
     *
     * @param user
     * @param id
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
    public Advertisement createAdvertisement(AppUser user, String id, Date datePosted, boolean isExpired, String petName, Integer petAge, String petDescription, Sex petSex, Species petSpecies) {
        Advertisement ad = new Advertisement();
        String error = "";

        if(user == null){
            error = error + "An Advertisement must have a AppUser ";
        }
        if (id == null || id.trim().length() == 0) {
            error = error + "id is not valid! ";
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
        if (petSpecies != Species.bird && petSpecies != Species.cat && petSpecies != Species.dog && petSpecies != Species.rabbit && petSpecies != Species.other) {
            error = error + "petSpecies is invalid! ";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }

        ad.setDatePosted(datePosted);
        ad.setAdvertisementId(id);
        ad.setPetDescription(petDescription);
        ad.setPetName(petName);
        ad.setPetAge(petAge);
        ad.setPetSex(petSex);
        ad.setPetSpecies(petSpecies);
        ad.setPostedBy(user);

        advertisementRepository.save(ad);
        return ad;
    }

    /**
     * Creates and adds a new Application object to the database.
     *
     * @param advertisement
     * @param aUser
     * @param id
     * @param dateOfSubmission
     * @param note
     * @param status
     * @return Application object
     */
    @Transactional
    public Application createApplication(Advertisement advertisement, AppUser aUser, String id, Date dateOfSubmission, String note, Status status) {
        Application app = new Application();
        String error = "";

        if (advertisement == null || aUser == null) {
            error = error + "An Application must have an Advertisement and a AppUser ";
        }
        if (id == null || id.trim().length() == 0) {
            error = error + "id is not valid! ";
        }
        if (dateOfSubmission == null) {
            error = "dateOfSubmission can not be empty! ";
        }
        if (note == null || note.trim().length() == 0) {
            error = error + "note cannot be empty ";
        }
        if (status != Status.accepted && status != Status.pending && status != Status.rejected) {
            error = error + "status is not valid! ";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }

        app.setApplicant(aUser);
        app.setAdvertisement(advertisement);
        app.setApplicationId(id);
        app.setDateOfSubmission(dateOfSubmission);
        app.setNote(note);
        app.setStatus(status);

        applicationRepository.save(app);
        return app;
    }

    /**
     * Creates and adds a new AppUser object to the database.
     *
     * @param name(String)
     * @param email(String)
     * @param password(String)
     * @param biography(String)
     * @param homeDescription(String)
     * @param age(Integer)
     * @param isAdmin(boolean)
     * @param sex(Sex)
     * @return AppUser object
     */
    @Transactional
    public  AppUser createAppUser(String name, String email, String password, String biography, String homeDescription, Integer age, boolean isAdmin, Sex sex ) {
        AppUser user1 = new AppUser();
        String error = "";
        if (name == null || name.trim().length() == 0) {
            error = "name can not be empty! ";
        }
        if (email == null || email.trim().length() == 0) {
            error = error + "email cannot be empty ";
        }
        if (password == null || password.trim().length() == 0) {
            error = error + "password cannot be empty ";
        }
        if (biography == null || biography.trim().length() == 0) {
            error = error + "biography cannot be empty ";
        }
        if (homeDescription == null || homeDescription.trim().length() == 0) {
            error = error + "homeDescription cannot be empty ";
        }
        if (age <= 0) {
            error = error + "age is not valid ";
        }
        if (sex != Sex.F && sex != Sex.M) {
            error = error + "sex is invalid ";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }

        user1.setSex(sex);
        user1.setPassword(password);
        user1.setIsAdmin(isAdmin);
        user1.setHomeDescription(homeDescription);
        user1.setBiography(biography);
        user1.setAge(age);
        user1.setEmail(email);
        user1.setName(name);

        appUserRepository.save(user1);
        return user1;
    }

    /**
     * Creates and adds a new Donation object to the database.
     *
     * @param amount(Integer)
     * @param dateOfPayment(Date)
     * @param transactionNumber(String)
     * @return Donation object
     */
    @Transactional
    public Donation createDonation(AppUser user2, Integer amount, Date dateOfPayment, String transactionNumber) {
        Donation donation = new Donation();
        String error = "";

        if (user2 == null) {
            error = "A Donation must have a AppUser ";
        }
        if (amount <= 0) {
            error = "Go take care of yourself! ";
        }
        if (dateOfPayment == null) {
            error = error + "dateOfPayment cannot be empty ";
        }
        if (transactionNumber == null || transactionNumber.trim().length() == 0) {
            error = error + "transactionNumber is invalid ";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }

        donation.setAmount(amount);
        donation.setDateOfPayment(dateOfPayment);
        donation.setTransactionID(transactionNumber);
        donation.setDonor(user2);

        donationRepository.save(donation);
        return donation;
    }

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
    //-------------------------------------------------------------------------------------//
    /////////////////////////////////AppUser methods/////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////AppUser getter//////////////////////////////////////////
    /**
     * Returns the AppUser with specified email from the database.
     *
     * @param email
     * @return AppUser object
     */
    @Transactional
    public AppUser getAppUserByEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("AppUser must have an email");
        }
        AppUser a = appUserRepository.findAppUserByEmail(email);
        return a;
    }

    /**
     * Returns all AppUsers in the database.
     *
     * @return List of AppUser objects
     */
    @Transactional
    public List<AppUser> getAllAppUsers() {
        return toList(appUserRepository.findAll());
    }

    //////////////////////////////////AppUser delete method//////////////////////////////////
    /**
     * Deletes the AppUser with specified email from the database.
     *
     * @param email
     */
    @Transactional
    public void deleteAppUser(String email) {
        AppUser user1 = appUserRepository.findAppUserByEmail(email);

        while(user1.getAdvertisements().size() != 0) {
            Set<Advertisement> advertisements = user1.getAdvertisements();
            Advertisement ad = advertisements.iterator().next();
            deleteAdvertisement(ad.getAdvertisementId());
        }

        while(user1.getDonations().size() != 0) {
            Set<Donation> donations = user1.getDonations();
            Donation donation = donations.iterator().next();
            deleteDonation(donation.getTransactionID());
        }

        while(user1.getApplications().size() != 0) {
            Set<Application> apps = user1.getApplications();
            Application app = apps.iterator().next();
            deleteApplication(app.getApplicationId());
        }

        appUserRepository.delete(user1);
    }


    //-------------------------------------------------------------------------------------//
    /////////////////////////////Advertisement methods///////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////Advertisement getter////////////////////////////////////////
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
    public void deleteAdvertisement(String id) {
        Advertisement adToDelete = advertisementRepository.findAdvertisementByAdvertisementId(id);

        while(adToDelete.getApplications().size() != 0) {
            Set<Application> applications = adToDelete.getApplications();
            Application app = applications.iterator().next();
            deleteApplication(app.getApplicationId());
        }

        while(adToDelete.getPetImages().size() != 0) {
            Set<Image> petImages = adToDelete.getPetImages();
            Image image = petImages.iterator().next();
            deleteImage(image.getImageId());
        }

        advertisementRepository.delete(adToDelete);
    }


    //-------------------------------------------------------------------------------------//
    /////////////////////////////////Application methods/////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////Application getter//////////////////////////////////////
    /**
     * Returns the Application with specified id from the database.
     *
     * @param id
     * @return Application object
     */
    @Transactional
    public Application getApplicationByID(String id) {
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("Application must have an ID");
        }
        Application a = applicationRepository.findApplicationByApplicationId(id);
        return a;
    }

    /**
     * Returns all Applications in the database.
     *
     * @return List of Application objects
     */
    @Transactional
    public List<Application> getAllApplications() {
        return toList(applicationRepository.findAll());
    }

    /////////////////////////////Application Delete Method////////////////////////////////////////
    /**
     * Deletes the Application with specified id from the database.
     *
     * @param id
     */
    @Transactional
    public void deleteApplication(String id) {
        applicationRepository.deleteApplicationByApplicationId(id);
    }

    //-------------------------------------------------------------------------------------//
    /////////////////////////////////Donation methods///////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////Donation getter////////////////////////////////////////
    /**
     * Returns the Donation with specified transactionNumber from the database.
     *
     * @param transactionNumber
     * @return Donation object
     */
    @Transactional
    public Donation getDonationByTransactionID(String transactionNumber) {
        if (transactionNumber == null || transactionNumber.trim().length() == 0) {
            throw new IllegalArgumentException("Donation must have a transactionNumber!");
        }
        Donation a = donationRepository.findDonationByTransactionID(transactionNumber);
        return a;
    }

    /**
     * Returns all Donations in the database.
     *
     * @return List of Donation objects
     */
    @Transactional
    public List<Donation> getAllDonations() {
        return toList(donationRepository.findAll());
    }

    /////////////////////////////Donation Delete Method////////////////////////////////////////
    /**
     * Deletes the Donation with specified transactionNumber from the database.
     *
     * @param transactionNumber
     */
    @Transactional
    public void deleteDonation(String transactionNumber) {
        donationRepository.deleteDonationByTransactionID(transactionNumber);
    }


    //-------------------------------------------------------------------------------------//
    /////////////////////////////////Image methods///////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////Image getter////////////////////////////////////////////
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
