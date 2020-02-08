package ca.mcgill.ecse321.petadoption.TestSuits;


import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.dao.ApplicationRepository;
import ca.mcgill.ecse321.petadoption.dao.DonationRepository;
import ca.mcgill.ecse321.petadoption.model.*;
import ca.mcgill.ecse321.petadoption.service.PetAdoptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppUserTest {

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
    public static long DATE_CST = 687731489;

    public static String PET_NAME = "Bubbles";
    public static Integer PET_AGE = 5;
    public static String PET_DESCRIPTION = "Cute birdy";
    public static Species PET_SPECIES = Species.bird;
    public static Sex PET_SEX = Sex.F;


    @Autowired
    private PetAdoptionService service;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @BeforeEach
    public void cleanDataBase(){
        donationRepository.deleteAll();
        advertisementRepository.deleteAll();
        applicationRepository.deleteAll();
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
        service.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1, USER_HOME_1,
                USER_AGE_1, USER_SEX_1, USER_ADMIN_1);

        service.createAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2, USER_HOME_2,
                USER_AGE_2, USER_SEX_2, USER_ADMIN_2);

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

        assertEquals(USER_NAME_2, user2.getName());
        assertEquals(USER_EMAIL_2, user2.getEmail());
        assertEquals(USER_PASSWORD_2, user2.getPassword());
        assertEquals(USER_BIO_2, user2.getBiography());
        assertEquals(USER_HOME_2, user2.getHomeDescription());
        assertEquals(USER_AGE_2, user2.getAge());
        assertEquals(USER_SEX_2, user2.getSex());
        assertEquals(USER_ADMIN_2, user2.isIsAdmin());

        user1 = appUserRepository.findAppUserByEmail(USER_EMAIL_1);

        assertEquals(USER_NAME_1, user1.getName());
        assertEquals(USER_EMAIL_1, user1.getEmail());
        assertEquals(USER_PASSWORD_1, user1.getPassword());
        assertEquals(USER_BIO_1, user1.getBiography());
        assertEquals(USER_HOME_1, user1.getHomeDescription());
        assertEquals(USER_AGE_1, user1.getAge());
        assertEquals(USER_SEX_1, user1.getSex());
        assertEquals(USER_ADMIN_1, user1.isIsAdmin());
    }


//    @Test
//    public void userDonationTest() {
//        AppUser user = new AppUser();
//        user.setName(USER_NAME_1);
//        user.setEmail(USER_EMAIL_1);
//        user.setPassword(USER_PASSWORD_1);
//        user.setBiography(USER_BIO_1);
//        user.setHomeDescription(USER_HOME_1);
//        user.setAge(USER_AGE_1);
//        user.setSex(USER_SEX_1);
//        user.setIsAdmin(USER_ADMIN_1);
//
//        Donation donation = new Donation();
//        donation.setAmount(DONATION_AMOUNT);
//
//        Date date = new Date(DATE_CST);
//
//        donation.setDateOfPayment(date);
//
//        donation.setDonor(user);
//
//        user.addDonation(donation);
//
//        appUserRepository.save(user);
//        donationRepository.save(donation);
//
//        user = null;
//        donation = null;
//        user = appUserRepository.findAppUserByEmail(USER_EMAIL_1);
//
//        try {
//            donation = (Donation) (user.getDonations().toArray()[0]);
//
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        assertEquals(user.getEmail(), donation.getDonor().getEmail());
//        assertEquals(DONATION_AMOUNT, donation.getAmount());
//        assertEquals(DATE_CST, donation.getDateOfPayment().getTime());
//    }

//    @Test
//    public void userAdvertisementTest() {
//        AppUser user = new AppUser();
//        user.setName(USER_NAME_1);
//        user.setEmail(USER_EMAIL_1);
//        user.setPassword(USER_PASSWORD_1);
//        user.setBiography(USER_BIO_1);
//        user.setHomeDescription(USER_HOME_1);
//        user.setAge(USER_AGE_1);
//        user.setSex(USER_SEX_1);
//        user.setIsAdmin(USER_ADMIN_1);
//
//        Advertisement advertisement = new Advertisement();
//        advertisement.setDatePosted(new Date(DATE_CST)); //requires java.sql.date
//        advertisement.setIsExpired(false);
//        advertisement.setPetName(PET_NAME);
//        advertisement.setPetAge(PET_AGE);
//        advertisement.setPetDescription(PET_DESCRIPTION);
//        advertisement.setPetSex(PET_SEX);
//        advertisement.setPetSpecies(PET_SPECIES);
//        advertisement.setPostedBy(user);
//
//        user.addAdvertisement(advertisement);
//
//
//        appUserRepository.save(user);
//        advertisementRepository.save(advertisement);
//
//        user = null;
//        advertisement = null;
//        user = appUserRepository.findAppUserByEmail(USER_EMAIL_1);
//
//        try {
//            advertisement = (Advertisement) (user.getAdvertisements().toArray()[0]);
//
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        assertEquals(DATE_CST, advertisement.getDatePosted().getTime());
//        assertEquals(false, advertisement.isIsExpired());
//        assertEquals(PET_NAME, advertisement.getPetName());
//        assertEquals(PET_AGE, advertisement.getPetAge());
//        assertEquals(PET_DESCRIPTION, advertisement.getPetDescription());
//        assertEquals(PET_SEX, advertisement.getPetSex());
//        assertEquals(PET_SPECIES, advertisement.getPetSpecies());
//        assertEquals(USER_EMAIL_1, advertisement.getPostedBy().getEmail());
//
//    }


    @Test
    public void userApplicationTest() {

    }

}
