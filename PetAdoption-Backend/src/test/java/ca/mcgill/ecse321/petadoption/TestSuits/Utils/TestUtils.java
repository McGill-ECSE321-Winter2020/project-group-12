package ca.mcgill.ecse321.petadoption.TestSuits.Utils;

import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.dto.DonationDto;
import ca.mcgill.ecse321.petadoption.model.*;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestUtils {

    public static Donation createDonation(AppUser donor, Integer amount, Date dateOfPayment) {
        Donation newDonation = new Donation();
        newDonation.setDonor(donor);
        newDonation.setAmount(amount);
        newDonation.setTransactionID();
        newDonation.setDateOfPayment(dateOfPayment);
        return newDonation;
    }

    public static void assertDonation(Donation donation, String userEmail, Integer amount, Date dateOfPayment) {
        assertNotNull(donation);
        assertEquals(userEmail, donation.getDonor().getEmail());
        assertEquals(amount, donation.getAmount());
        assertEquals(dateOfPayment, donation.getDateOfPayment());
    }

    public static void assertDonation(DonationDto donation, String userEmail, Integer amount, Date dateOfPayment) {
        assertNotNull(donation);
        assertEquals(userEmail, donation.getDonorEmail());
        assertEquals(amount, donation.getAmount());
        assertEquals(dateOfPayment, donation.getDateOfPayment());
    }
    public static AppUser createAppUser(String name, String email, String password,
                                        String biography, String homeDescription, Integer age, boolean isAdmin, Sex sex) {
        AppUser new_user = new AppUser();
        new_user.setSex(sex);
        new_user.setPassword(password);
        new_user.setIsAdmin(isAdmin);
        new_user.setHomeDescription(homeDescription);
        new_user.setBiography(biography);
        new_user.setAge(age);
        new_user.setEmail(email);
        new_user.setName(name);

//        Advertisement ad = new Advertisement();
//        ad.setAdvertisementId();
//        ;
//        new_user.addAdvertisement(ad);
//
//        Application app = new Application();
//        app.setApplicationId();
//        new_user.addApplication(app);

        // TODO: ZAK: use createDonation AFTER CONSTRUCTING a user
//        Donation don = new Donation();
//        don.setTransactionID();
//        new_user.addDonation(don);
          return new_user;
    }

    public static void assertAppUser(AppUser user, String name, String email, String password,
                                     String biography, String homeDescription, Integer age, boolean isAdmin, Sex sex) {
        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(biography, user.getBiography());
        assertEquals(homeDescription, user.getHomeDescription());
        assertEquals(age, user.getAge());
        assertEquals(sex, user.getSex());
        assertEquals(isAdmin, user.isIsAdmin());
    }

    public static Application createApplication(Advertisement advertisement, AppUser appUser, Date dateOfSubmission, String note, Status status) {
        Application app = new Application();
        app.setApplicationId();
        app.setDateOfSubmission(dateOfSubmission);
        app.setApplicant(appUser);
        app.setAdvertisement(advertisement);
        app.setNote(note);
        app.setStatus(status);
        return app;
    }

    public static void assertApplication(Application application, Advertisement advertisement, AppUser appUser, Date dateOfSubmission, String note, Status status) {
        assertNotNull(application);
        assertEquals(dateOfSubmission, application.getDateOfSubmission());
        assertEquals(note, application.getNote());
        assertEquals(status, application.getStatus());
        assertEquals(appUser.getEmail(), application.getApplicant().getEmail());
        assertEquals(advertisement.getPetAge(), application.getAdvertisement().getPetAge());
        assertEquals(advertisement.getPetSex(), application.getAdvertisement().getPetSex());
        assertEquals(advertisement.getDatePosted(), application.getAdvertisement().getDatePosted());
        assertEquals(advertisement.getPostedBy(), application.getAdvertisement().getPostedBy());
    }

    public static Advertisement createAdvertisement(Date datePosted, boolean isExpired, AppUser postedBy, String petName, Integer age, String description) {
        Advertisement ad = new Advertisement();
        ad.setAdvertisementId();
        ad.setDatePosted(datePosted);
        ad.setIsExpired(isExpired);
        ad.setPostedBy(postedBy);
        ad.setPetName(petName);
        ad.setPetAge(age);
        return ad;
    }

    public static void assertAppUser(AppUserDto user, String name, String email, String password,
                                     String biography, String homeDescription, Integer age, boolean isAdmin, Sex sex ){
        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(biography, user.getBiography());
        assertEquals(homeDescription, user.getHomeDescription());
        assertEquals(age, user.getAge());
        assertEquals(sex, user.getSex());
        assertEquals(isAdmin, user.isIsAdmin());
    }

    public static Image createImage(Advertisement ad, String name, String link, String id) {

        Image newImage = new Image();
        newImage.setName(name);
        newImage.setLink(link);
        newImage.setImageId(id);
        newImage.setAdvertisement(ad);

        return newImage;
    }

    public static Advertisement createAdvertisement( AppUser user, Date datePosted, String id, boolean isExpired, String name,
                                                     Integer age, String description, Sex sex, Species specie) {
        Advertisement ad = new Advertisement();
        ad.setDatePosted(datePosted);
        ad.setAdvertisementId(id);
        ad.setIsExpired(isExpired);
        ad.setPetName(name);
        ad.setPetAge(age);
        ad.setPetDescription(description);
        ad.setPetSex(sex);
        ad.setPetSpecies(specie);
        ad.setPostedBy(user);
        return ad;
    }


}
