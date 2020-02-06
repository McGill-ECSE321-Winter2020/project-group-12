package ca.mcgill.ecse321.petadoption.service;


import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.sql.Date;

@Service
public class PetAdoptionService {

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    DonationRepository donationRepository;

    @Autowired
    ImageRepository imageRepository;

    @Transactional
    public AppUser createAppUser(String name, String email, String password, String biography, String homeDescription,
                                 int age, Sex sex, boolean isAdmin) {
        AppUser new_user = new AppUser();
        new_user.setName(name);
        new_user.setEmail(email);
        new_user.setPassword(password);
        new_user.setBiography(biography);
        new_user.setHomeDescription(homeDescription);
        new_user.setAge(age);
        new_user.setSex(sex);
        new_user.setIsAdmin(isAdmin);
        appUserRepository.save(new_user);
        return new_user;
    }

    @Transactional
    public AppUser getAppUser(String email) {
        return appUserRepository.findAppUserByEmail(email);
    }



    @Transactional
    public Advertisement createAdvertisement(Date date_posted, Boolean is_expired,
                                             String name, Integer age, String description, Sex sex, Species species){
        Advertisement new_advertisement = new Advertisement();
        new_advertisement.setDatePosted(date_posted); //requires java.sql.date
        new_advertisement.setIsExpired(is_expired);
        new_advertisement.setName(name);
        new_advertisement.setAge(age);
        new_advertisement.setDescription(description);
        new_advertisement.setSex(sex);
        new_advertisement.setSpecies(species);
        advertisementRepository.save(new_advertisement);
        return new_advertisement;
    }

    @Transactional
    public Advertisement getAdvertisement(Long id){
        return advertisementRepository.findAdvertisementById(id);
    }

    @Transactional
    public Application createApplication(Date date_of_submission, Status status, String note, Advertisement ad){
        Application new_application = new Application();
        new_application.setDateOfSubmission(date_of_submission);
        new_application.setStatus(status);
        new_application.setNote(note);
        new_application.setAdvertisement(ad);
        applicationRepository.save(new_application);
        return new_application;
    }

    @Transactional
    public Application getApplication(Long id){
        return applicationRepository.findApplicationById(id);
    }

    @Transactional
    public Donation createDonation(AppUser donor, Integer amount, Date date_of_payment){
        Donation new_donation = new Donation();
        new_donation.setDonor(donor);
        new_donation.setAmount(amount);
        new_donation.setDateOfPayment(date_of_payment);
        donationRepository.save(new_donation);
        return new_donation;
    }

    @Transactional
    public Donation getDonation(Long transaction_number){
        return donationRepository.findDonationByTransactionNumber(transaction_number);
    }

    @Transactional
    public Image createImage(String name, String link, Advertisement advertisement){
        Image new_image = new Image();
        new_image.setName(name);
        new_image.setLink(link);
        new_image.setAdvertisement(advertisement);
        imageRepository.save(new_image);
        return new_image;
    }

    @Transactional
    public Image getImage(Long id){
        return imageRepository.findImageById(id);
    }
}
