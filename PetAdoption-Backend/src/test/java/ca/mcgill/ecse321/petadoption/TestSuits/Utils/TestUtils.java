package ca.mcgill.ecse321.petadoption.TestSuits.Utils;

import ca.mcgill.ecse321.petadoption.model.*;

import java.sql.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestUtils {

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

        Advertisement ad = new Advertisement();
        ad.setAdvertisementId();
        ;
        new_user.addAdvertisement(ad);

        Application app = new Application();
        app.setApplicationId();
        new_user.addApplication(app);

        Donation don = new Donation();
        don.setTransactionID();
        new_user.addDonation(don);
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

}
