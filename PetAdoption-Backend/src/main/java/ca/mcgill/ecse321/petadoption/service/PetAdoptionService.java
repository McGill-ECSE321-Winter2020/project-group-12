package ca.mcgill.ecse321.petadoption.service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.petadoption.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.ApplicationRepository;
import ca.mcgill.ecse321.petadoption.dao.DonationRepository;
import ca.mcgill.ecse321.petadoption.dao.ImageRepository;

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

}
