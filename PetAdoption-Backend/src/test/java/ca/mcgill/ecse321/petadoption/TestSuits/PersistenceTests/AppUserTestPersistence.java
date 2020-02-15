package ca.mcgill.ecse321.petadoption.TestSuits.PersistenceTests;


import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppUserTestPersistence {

    public static String USER_NAME_1 = "user 1";
    public static String USER_EMAIL_1 = "user1@mcgill.ca";
    public static String USER_PASSWORD_1 = "password 1";
    public static String USER_BIO_1 = "empty";
    public static String USER_HOME_1 = "its nice";
    public static Integer USER_AGE_1 = 34;
    public static Sex USER_SEX_1 = Sex.M;
    public static boolean USER_ADMIN_1 = true;

    public static String USER_NAME_2 = "user 2";
    public static String USER_EMAIL_2 = "user2@mcgill.ca";
    public static String USER_PASSWORD_2 = "password 2";
    public static String USER_BIO_2 = "empty_ish";
    public static String USER_HOME_2 = "not so nice";
    public static Integer USER_AGE_2 = 23;
    public static Sex USER_SEX_2 = Sex.F;
    public static boolean USER_ADMIN_2 = false;

    public static Integer DONATION_AMOUNT = 50;


    public static String PET_NAME = "Bubbles";
    public static Integer PET_AGE = 5;
    public static String PET_DESCRIPTION = "Cute birdy";
    public static Species PET_SPECIES = Species.bird;
    public static Sex PET_SEX = Sex.F;

    public static String note = "note";
    public static Status status = Status.pending;

    public static Date date = java.sql.Date.valueOf(LocalDate.of(2020, 1, 31));
    public static Date date2 = java.sql.Date.valueOf(LocalDate.of(2020, 2, 1));


    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private ImageRepository imageRepository;

    @BeforeEach
    public void clearDatabase() {
        applicationRepository.deleteAll();
        imageRepository.deleteAll();
        // First, we clear advertisement to avoid exceptions due to inconsistencies
        advertisementRepository.deleteAll();
        // Then we can clear the other tables
        donationRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    public void userCreateTest() {

        AppUser user = new AppUser();
        user.setName(USER_NAME_1);
        user.setEmail(USER_EMAIL_1);
        user.setPassword(USER_PASSWORD_1);
        user.setBiography(USER_BIO_1);
        user.setHomeDescription(USER_HOME_1);
        user.setAge(USER_AGE_1);
        user.setSex(USER_SEX_1);
        user.setIsAdmin(USER_ADMIN_1);
        appUserRepository.save(user);

        user = null;

        user = appUserRepository.findAppUserByEmail(USER_EMAIL_1);

        assertNotNull(user);
        assertEquals(USER_NAME_1, user.getName());
        assertEquals(USER_EMAIL_1, user.getEmail());
        assertEquals(USER_PASSWORD_1, user.getPassword());
        assertEquals(USER_BIO_1, user.getBiography());
        assertEquals(USER_HOME_1, user.getHomeDescription());
        assertEquals(USER_AGE_1, user.getAge());
        assertEquals(USER_SEX_1, user.getSex());
        assertEquals(USER_ADMIN_1, user.isIsAdmin());
    }


    @Test
    public void multipleUserCreateTest() {

        AppUser user1 = new AppUser();
        user1.setName(USER_NAME_1);
        user1.setEmail(USER_EMAIL_1);
        user1.setPassword(USER_PASSWORD_1);
        user1.setBiography(USER_BIO_1);
        user1.setHomeDescription(USER_HOME_1);
        user1.setAge(USER_AGE_1);
        user1.setSex(USER_SEX_1);
        user1.setIsAdmin(USER_ADMIN_1);
        appUserRepository.save(user1);

        AppUser user2 = new AppUser();
        user2.setName(USER_NAME_2);
        user2.setEmail(USER_EMAIL_2);
        user2.setPassword(USER_PASSWORD_2);
        user2.setBiography(USER_BIO_2);
        user2.setHomeDescription(USER_HOME_2);
        user2.setAge(USER_AGE_2);
        user2.setSex(USER_SEX_2);
        user2.setIsAdmin(USER_ADMIN_2);
        appUserRepository.save(user2);

        user1 = null;
        user2 = null;

        user2 = appUserRepository.findAppUserByEmail(USER_EMAIL_2);

        assertNotNull(user2);
        assertEquals(USER_NAME_2, user2.getName());
        assertEquals(USER_EMAIL_2, user2.getEmail());
        assertEquals(USER_PASSWORD_2, user2.getPassword());
        assertEquals(USER_BIO_2, user2.getBiography());
        assertEquals(USER_HOME_2, user2.getHomeDescription());
        assertEquals(USER_AGE_2, user2.getAge());
        assertEquals(USER_SEX_2, user2.getSex());
        assertEquals(USER_ADMIN_2, user2.isIsAdmin());

        user1 = appUserRepository.findAppUserByEmail(USER_EMAIL_1);

        assertNotNull(user1);
        assertEquals(USER_NAME_1, user1.getName());
        assertEquals(USER_EMAIL_1, user1.getEmail());
        assertEquals(USER_PASSWORD_1, user1.getPassword());
        assertEquals(USER_BIO_1, user1.getBiography());
        assertEquals(USER_HOME_1, user1.getHomeDescription());
        assertEquals(USER_AGE_1, user1.getAge());
        assertEquals(USER_SEX_1, user1.getSex());
        assertEquals(USER_ADMIN_1, user1.isIsAdmin());
    }


    @Test
    public void userDonationTest() {
        AppUser user = new AppUser();
        user.setName(USER_NAME_1);
        user.setEmail(USER_EMAIL_1);
        user.setPassword(USER_PASSWORD_1);
        user.setBiography(USER_BIO_1);
        user.setHomeDescription(USER_HOME_1);
        user.setAge(USER_AGE_1);
        user.setSex(USER_SEX_1);
        user.setIsAdmin(USER_ADMIN_1);
        appUserRepository.save(user);
        Donation donation = new Donation();
        donation.setAmount(DONATION_AMOUNT);

        Date date = java.sql.Date.valueOf(LocalDate.of(2020, 1, 31));

        donation.setDateOfPayment(date);

        donation.setDonor(user);

        donation.setTransactionID();

        String donId = donation.getTransactionID();

        user.addDonation(donation);
        donationRepository.save(donation);

        appUserRepository.save(user);

        user = null;
        donation = null;
        user = appUserRepository.findAppUserByEmail(USER_EMAIL_1);

        assertNotNull(user);

        try {
            donation = (Donation) (user.getDonations().toArray()[0]);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        assertNotNull(donation);
        assertEquals(user.getEmail(), donation.getDonor().getEmail());
        assertEquals(DONATION_AMOUNT, donation.getAmount());
        assertEquals(donation.getTransactionID(), donId);
        assertEquals(date, donation.getDateOfPayment());
    }

    @Test
    public void userAdvertisementTest() {
        AppUser user = new AppUser();
        user.setName(USER_NAME_1);
        user.setEmail(USER_EMAIL_1);
        user.setPassword(USER_PASSWORD_1);
        user.setBiography(USER_BIO_1);
        user.setHomeDescription(USER_HOME_1);
        user.setAge(USER_AGE_1);
        user.setSex(USER_SEX_1);
        user.setIsAdmin(USER_ADMIN_1);
        appUserRepository.save(user);

        Advertisement advertisement = new Advertisement();
        advertisement.setDatePosted(date); //requires java.sql.date
        advertisement.setIsExpired(false);
        advertisement.setPetName(PET_NAME);
        advertisement.setPetAge(PET_AGE);
        advertisement.setPetDescription(PET_DESCRIPTION);
        advertisement.setPetSex(PET_SEX);
        advertisement.setPetSpecies(PET_SPECIES);
        advertisement.setPostedBy(user);
        advertisement.setAdvertisementId();
        String ad_id = advertisement.getAdvertisementId();

        user.addAdvertisement(advertisement);



        advertisementRepository.save(advertisement);

        user = null;
        advertisement = null;
        user = appUserRepository.findAppUserByEmail(USER_EMAIL_1);


        advertisement = (Advertisement) (user.getAdvertisements().toArray()[0]);
        assertEquals(ad_id, advertisement.getAdvertisementId());
        assertEquals(date, advertisement.getDatePosted());
        assertEquals(false, advertisement.isIsExpired());
        assertEquals(PET_NAME, advertisement.getPetName());
        assertEquals(PET_AGE, advertisement.getPetAge());
        assertEquals(PET_DESCRIPTION, advertisement.getPetDescription());
        assertEquals(PET_SEX, advertisement.getPetSex());
        assertEquals(PET_SPECIES, advertisement.getPetSpecies());
        assertEquals(USER_EMAIL_1, advertisement.getPostedBy().getEmail());

    }


    @Test
    public void userApplicationTest() {
        //create 2 users : 1 owner and 1 adopter
        AppUser user = new AppUser();
        user.setName(USER_NAME_1);
        user.setEmail(USER_EMAIL_1);
        user.setPassword(USER_PASSWORD_1);
        user.setBiography(USER_BIO_1);
        user.setHomeDescription(USER_HOME_1);
        user.setAge(USER_AGE_1);
        user.setSex(USER_SEX_1);
        user.setIsAdmin(USER_ADMIN_1);
        appUserRepository.save(user);


        AppUser user2 = new AppUser();
        user2.setName(USER_NAME_2);
        user2.setEmail(USER_EMAIL_2);
        user2.setPassword(USER_PASSWORD_2);
        user2.setBiography(USER_BIO_2);
        user2.setHomeDescription(USER_HOME_2);
        user2.setAge(USER_AGE_2);
        user2.setSex(USER_SEX_2);
        user2.setIsAdmin(false);
        appUserRepository.save(user2);


        //create advertisement
        Advertisement ad = new Advertisement();
        ad.setDatePosted(date); //requires java.sql.date
        ad.setAdvertisementId();
        String ad_id = ad.getAdvertisementId();
        ad.setIsExpired(false);
        ad.setPetName(PET_NAME);
        ad.setPetAge(PET_AGE);
        ad.setPetDescription(PET_DESCRIPTION);
        ad.setPetSex(PET_SEX);
        ad.setPetSpecies(PET_SPECIES);
        ad.setPostedBy(user2);
        user2.addAdvertisement(ad);
        advertisementRepository.save(ad);


        //create application
        Application application = new Application();
        application.setApplicationId();
        String id = application.getApplicationId();
        application.setAdvertisement(ad);
        application.setApplicant(user);
        application.setNote(note);
        application.setStatus(status);
        application.setDateOfSubmission(date2);
        application = applicationRepository.save(application);
        user.addApplication(application);
        appUserRepository.save(user);
        appUserRepository.save(user2);


        //set everything to null
        user = null;
        user2 = null;
        application = null;
        ad = null;


        //retrieve the users, advertisement and ad -->check persistence
        AppUser retrieveUser = appUserRepository.findAppUserByEmail(USER_EMAIL_1);
        AppUser retrieveUser2 = appUserRepository.findAppUserByEmail(USER_EMAIL_2);
        Advertisement ret_ad = advertisementRepository.findAdvertisementByAdvertisementId(ad_id);

        assertNotNull(retrieveUser);
        assertNotNull(retrieveUser2);
        assertNotNull(ret_ad);

        Application ap = (Application) retrieveUser.getApplications().toArray()[0];
        assertNotNull(ap);

        assertEquals(id, ap.getApplicationId());
        assertEquals(ap.getAdvertisement().getAdvertisementId(), ret_ad.getAdvertisementId());
        assertEquals(ap.getApplicant().getEmail(), retrieveUser.getEmail());
        assertEquals(USER_EMAIL_1, ap.getApplicant().getEmail());
        assertEquals(date2, ap.getDateOfSubmission());
        assertEquals(note, ap.getNote());
        assertEquals(status, ap.getStatus());
        assertEquals(ap.getAdvertisement().getPostedBy().getEmail(), retrieveUser2.getEmail());

    }

}
