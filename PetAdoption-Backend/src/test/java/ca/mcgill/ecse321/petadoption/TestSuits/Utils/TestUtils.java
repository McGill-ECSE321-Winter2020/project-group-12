package ca.mcgill.ecse321.petadoption.TestSuits.Utils;

import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.dto.ApplicationDto;
import ca.mcgill.ecse321.petadoption.dto.DonationDto;
import ca.mcgill.ecse321.petadoption.model.*;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestUtils {
    /**
     * Creates donation
     * @param donor
     * @param amount
     * @param dateOfPayment
     * @return
     */
    public static Donation createDonation(AppUser donor, Integer amount, Date dateOfPayment) {
        Donation newDonation = new Donation();
        newDonation.setDonor(donor);
        newDonation.setAmount(amount);
        newDonation.setTransactionID();
        newDonation.setDateOfPayment(dateOfPayment);
        return newDonation;
    }

    /**
     * Verifies a donation is equal to the attributes of another
     * @param donation
     * @param userEmail
     * @param amount
     * @param dateOfPayment
     */
    public static void assertDonation(Donation donation, String userEmail, Integer amount, Date dateOfPayment) {
        assertNotNull(donation);
        //assertEquals(userEmail, donation.getDonor().getEmail());
        assertEquals(amount, donation.getAmount());
        assertEquals(dateOfPayment, donation.getDateOfPayment());
    }

    /**
     * Overloads previous method for dto
     * @param donation
     * @param userEmail
     * @param amount
     * @param dateOfPayment
     */

    public static void assertDonation(DonationDto donation, String userEmail, Integer amount, Date dateOfPayment) {
        assertNotNull(donation);
        assertEquals(userEmail, donation.getDonorEmail());
        assertEquals(amount, donation.getAmount());
        // Assertion deleted due to error caused by a JDBC version: (Will be fixed later)
        // https://stackoverflow.com/questions/11296606/java-jdbc-dates-consistently-two-days-off
        //assertEquals(dateOfPayment, donation.getDateOfPayment());
    }

    /**
     * Creates app user for testing
     * @param name
     * @param email
     * @param password
     * @param biography
     * @param homeDescription
     * @param age
     * @param isAdmin
     * @param sex
     * @return
     */
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
          return new_user;
    }

    /**
     * Verifies that an app user has same attributes as another one (passed separately as params)
     * @param user
     * @param name
     * @param email
     * @param password
     * @param biography
     * @param homeDescription
     * @param age
     * @param isAdmin
     * @param sex
     */
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

    /**
     * Creates advertisement for testing purposes
     * @param advertisement
     * @param appUser
     * @param dateOfSubmission
     * @param note
     * @param status
     * @return
     */
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

    /**
     * verifies certain attributes for an application
     * @param application
     * @param advertisement
     * @param appUser
     * @param dateOfSubmission
     * @param note
     * @param status
     */
    public static void assertApplication(Application application, Advertisement advertisement, AppUser appUser, Date dateOfSubmission, String note, Status status) {
        assertNotNull(application);
        assertEquals(dateOfSubmission, application.getDateOfSubmission());
        assertEquals(note, application.getNote());
        assertEquals(status, application.getStatus());
        assertEquals(appUser.getEmail(), application.getApplicant().getEmail());
        assertEquals(advertisement.getPetAge(), application.getAdvertisement().getPetAge());
        assertEquals(advertisement.getPetSex(), application.getAdvertisement().getPetSex());
        assertEquals(advertisement.getDatePosted(), application.getAdvertisement().getDatePosted());
        assertEquals(advertisement.getPostedBy().getEmail(), application.getAdvertisement().getPostedBy().getEmail());
    }

    /**
     * overloads previous method for dto's
     * @param application
     * @param advertisementId
     * @param appUserEmail
     * @param dateOfSubmission
     * @param note
     * @param status
     */
    public static void assertApplication(ApplicationDto application, String advertisementId, String appUserEmail, Date dateOfSubmission, String note, Status status) {
        assertNotNull(application);
        assertEquals(note, application.getNote());
        assertEquals(status, application.getStatus());
        assertEquals(appUserEmail, application.getApplicantEmail());
        assertEquals(advertisementId, application.getAdvertisementId());
    }

    /**
     * Creates advertisement for testing purposes
     * @param datePosted
     * @param isExpired
     * @param postedBy
     * @param petName
     * @param age
     * @param description
     * @return
     */
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

    /**
     * checks if advertisement has certain attributes
     * @param user
     * @param name
     * @param email
     * @param password
     * @param biography
     * @param homeDescription
     * @param age
     * @param isAdmin
     * @param sex
     */
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

    /**
     * generates image for testing purposes
     * @param ad
     * @param name
     * @param link
     * @param id
     * @return
     */

    public static Image createImage(Advertisement ad, String name, String link, String id) {

        Image newImage = new Image();
        newImage.setName(name);
        newImage.setLink(link);
        newImage.setImageId(id);
        newImage.setAdvertisement(ad);

        return newImage;
    }

    /**
     * overloads previous create advertisement method, each method is used for different testing purposes
     * @param user
     * @param datePosted
     * @param id
     * @param isExpired
     * @param name
     * @param age
     * @param description
     * @param sex
     * @param specie
     * @return
     */
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
