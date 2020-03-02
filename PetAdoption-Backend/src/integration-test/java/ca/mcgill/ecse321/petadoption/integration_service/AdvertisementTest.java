package ca.mcgill.ecse321.petadoption.integration_service;

import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Sex;
import ca.mcgill.ecse321.petadoption.model.Species;
import ca.mcgill.ecse321.petadoption.service.AdvertisementService;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AdvertisementTest {

    // Constants to create test appUser and advertisement objects
    private static final String USER_NAME_1 = "user 1";
    private static final String USER_EMAIL_1 = "user1@mcgill.ca";
    private static final String USER_PASSWORD_1 = "password 1";
    private static final String USER_BIO_1 = "empty";
    private static final String USER_HOME_1 = "its nice";
    private static final Integer USER_AGE_1 = 34;
    private static final Sex USER_SEX_1 = Sex.M;
    private static final boolean USER_ADMIN_1 = true;

    private static final String USER_EMAIL_2 = "user2@mcgill.ca";
    private static final String NON_EXISTING_APP_USER = "user3@mcgill.ca";

    private static final String PET_NAME_1 = "Rusty";
    private static final Integer PET_AGE_1 = 10;
    private static final String PET_DESCRIPTION_1 = "Not Rusty at all";
    private static final Sex PET_SEX_1 = Sex.M;
    private static final Species PET_SPECIES_1 = Species.dog;

    private static final String PET_NAME_2 = "Snow";
    private static final Integer PET_AGE_2 = 15;
    private static final String PET_DESCRIPTION_2 = "Not Snowy at all";
    private static final Sex PET_SEX_2 = Sex.F;
    private static final Species PET_SPECIES_2 = Species.cat;

    private static final String PET_NAME_3 = "Chirpy";
    private static final Integer PET_AGE_3 = 20;
    private static final String PET_DESCRIPTION_3 = "Not Chirpy at all";
    private static final Sex PET_SEX_3 = Sex.F;
    private static final Species PET_SPECIES_3 = Species.bird;

    private static final Integer NEGATIVE_AGE = -7;

    private static final Date DATE_1 = Date.valueOf("2020-07-02");
    private static final Date DATE_2 = Date.valueOf("2020-06-07");

    private static final String INVALID_ID = "a47sxj9";

    private static final String INVALID_AD_ID_MESSAGE = "Invalid advertisement requested. Please check advertisement ID.";
    private static final String APP_USER_ERROR_MESSAGE = "User not found. Cannot make advertisement without user profile!";
    private static final String DATE_ERROR_MESSAGE = "Date posted cannot be an empty field!";
    private static final String PET_NAME_ERROR_MESSAGE = "Pet name cannot be empty! ";
    private static final String PET_AGE_ERROR_MESSAGE = "Pet age cannot be less than or equal to 0! ";
    private static final String PET_DESCRIPTION_ERROR_MESSAGE = "Pet description cannot be empty! ";
    private static final String PET_SEX_ERROR_MESSAGE = "Pet sex must be specified! ";
    private static final String PET_SPECIES_ERROR_MESSAGE = "Pet Species is invalid! ";
    private static final String AD_REQUIRE_ID_MESSAGE = "Advertisement must have an ID";
    private static final String PROFILE_NOT_FOUND_MESSAGE = "User profile not found. App user does not exist!";

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @BeforeEach
    public void cleanDB() {
        advertisementRepository.deleteAll();
        appUserRepository.deleteAll();
    }


    @Test
    public void testCreateAdvertisement(){
        Advertisement advertisement = createAppUserThenAdvertisement();
        String advertisementID = advertisement.getAdvertisementId();

        assertNotNull(advertisement);
        assertEquals(USER_EMAIL_1, advertisement.getPostedBy().getEmail());
        assertEquals(DATE_1, advertisement.getDatePosted());
        assertEquals(advertisementID,
                advertisementRepository.findAdvertisementByAdvertisementId(advertisementID).getAdvertisementId());
        assertEquals(PET_NAME_1, advertisement.getPetName());
        assertEquals(PET_AGE_1, advertisement.getPetAge());
        assertEquals(PET_DESCRIPTION_1, advertisement.getPetDescription());
        assertEquals(PET_SEX_1, advertisement.getPetSex());
        assertEquals(PET_SPECIES_1, advertisement.getPetSpecies());
    }

    @Test
    public void testCreateAdvertisementEmailEmpty() {
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(" ", DATE_1, PET_NAME_1,
                    PET_AGE_1, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(APP_USER_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementEmailNull() {
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(null, DATE_1, PET_NAME_1,
                    PET_AGE_1, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(APP_USER_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementNullDate() {
        Advertisement advertisement = null;
        AppUser appUser = createAppUser();
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(appUser.getEmail(), null, PET_NAME_1,
                    PET_AGE_1, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(DATE_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementNoAppUser() {
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(NON_EXISTING_APP_USER, DATE_1, PET_NAME_1,
                    PET_AGE_1, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(APP_USER_ERROR_MESSAGE, error);
    }


    @Test
    public void testCreateAdvertisementNullPetName() {
        Advertisement advertisement = null;
        AppUser appUser = createAppUser();
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(appUser.getEmail(), DATE_1, null,
                    PET_AGE_1, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(PET_NAME_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementEmptyPetName() {
        Advertisement advertisement = null;
        AppUser appUser = createAppUser();
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(appUser.getEmail(), DATE_1, " ",
                    PET_AGE_1, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(PET_NAME_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementNullAge() {
        Advertisement advertisement = null;
        AppUser appUser = createAppUser();
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(appUser.getEmail(), DATE_1, PET_NAME_1,
                    null, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(PET_AGE_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementNegativeAge() {
        Advertisement advertisement = null;
        AppUser appUser = createAppUser();
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(appUser.getEmail(), DATE_1, PET_NAME_1,
                    NEGATIVE_AGE, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(PET_AGE_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementNullDescription() {
        Advertisement advertisement = null;
        AppUser appUser = createAppUser();
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(appUser.getEmail(), DATE_1, PET_NAME_1,
                    PET_AGE_1, null, PET_SEX_1, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(PET_DESCRIPTION_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementEmptyDescription() {
        Advertisement advertisement = null;
        AppUser appUser = createAppUser();
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(appUser.getEmail(), DATE_1, PET_NAME_1,
                    PET_AGE_1, " ", PET_SEX_1, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(PET_DESCRIPTION_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementNullSex() {
        Advertisement advertisement = null;
        AppUser appUser = createAppUser();
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(appUser.getEmail(), DATE_1, PET_NAME_1,
                    PET_AGE_1, PET_DESCRIPTION_1, null, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(PET_SEX_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementNullSpecies() {
        Advertisement advertisement = null;
        AppUser appUser = createAppUser();
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(appUser.getEmail(), DATE_1, PET_NAME_1,
                    PET_AGE_1, PET_DESCRIPTION_1, PET_SEX_1, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(PET_SPECIES_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementAllEmpty() {
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(null, null, null,
                    null, null, null, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(APP_USER_ERROR_MESSAGE, error);
    }

    @Test
    public void testCreateAdvertisementAllPetFieldsEmpty() {
        Advertisement advertisement = null;
        AppUser appUser = createAppUser();
        String error = null;
        try {
            advertisement = advertisementService.createAdvertisement(appUser.getEmail(), DATE_1, null,
                    null, null, null, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(PET_NAME_ERROR_MESSAGE + PET_AGE_ERROR_MESSAGE + PET_DESCRIPTION_ERROR_MESSAGE +
                PET_SEX_ERROR_MESSAGE + PET_SPECIES_ERROR_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisement() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        String advertisementId = advertisement.getAdvertisementId();
        try {
            advertisement = advertisementService.updateAdvertisement(
                    advertisementRepository.findAdvertisementByAdvertisementId(advertisementId).getAdvertisementId(),
                    PET_NAME_2, PET_AGE_2, PET_DESCRIPTION_2, PET_SEX_2, PET_SPECIES_2);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(advertisement);
        assertEquals(advertisementId, advertisement.getAdvertisementId());
        assertEquals(PET_NAME_2, advertisement.getPetName());
        assertEquals(PET_AGE_2, advertisement.getPetAge());
        assertEquals(PET_DESCRIPTION_2, advertisement.getPetDescription());
        assertEquals(PET_SEX_2, advertisement.getPetSex());
        assertEquals(PET_SPECIES_2, advertisement.getPetSpecies());
    }

    @Test
    public void testUpdateAdvertisementInvalidId() {
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisement(INVALID_ID, PET_NAME_2, PET_AGE_2,
                    PET_DESCRIPTION_2, PET_SEX_2, PET_SPECIES_2);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(INVALID_AD_ID_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementNullId() {
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisement(null, PET_NAME_2, PET_AGE_2,
                    PET_DESCRIPTION_2, PET_SEX_2, PET_SPECIES_2);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(INVALID_AD_ID_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementEmptyId() {
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisement(" ", PET_NAME_2, PET_AGE_2,
                    PET_DESCRIPTION_2, PET_SEX_2, PET_SPECIES_2);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(INVALID_AD_ID_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementNullPetName() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisement(advertisement.getAdvertisementId(),
                    null, PET_AGE_3, PET_DESCRIPTION_3, PET_SEX_3, PET_SPECIES_3);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(advertisement);
        assertEquals(advertisement, advertisement);
        assertEquals(PET_NAME_1, advertisement.getPetName());
        assertEquals(PET_NAME_ERROR_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementEmptyPetName() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisement(advertisement.getAdvertisementId(),
                    " ", PET_AGE_3, PET_DESCRIPTION_3, PET_SEX_3, PET_SPECIES_3);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(advertisement);
        assertEquals(advertisement, advertisement);
        assertEquals(PET_NAME_1, advertisement.getPetName());
        assertEquals(PET_NAME_ERROR_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementNullPetAge() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisement(advertisement.getAdvertisementId(), PET_NAME_3,
                    null, PET_DESCRIPTION_3, PET_SEX_3, PET_SPECIES_3);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(advertisement);
        assertEquals(advertisement, advertisement);
        assertEquals(PET_AGE_1, advertisement.getPetAge());
        assertEquals(PET_AGE_ERROR_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementNegativePetAge() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisement(advertisement.getAdvertisementId(),
                    PET_NAME_3, NEGATIVE_AGE, PET_DESCRIPTION_3, PET_SEX_3, PET_SPECIES_3);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(advertisement);
        assertEquals(advertisement, advertisement);
        assertEquals(PET_AGE_1, advertisement.getPetAge());
        assertEquals(PET_AGE_ERROR_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementNullPetDescription() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisement(advertisement.getAdvertisementId(),
                    PET_NAME_3, PET_AGE_3, null, PET_SEX_3, PET_SPECIES_3);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(advertisement);
        assertEquals(advertisement, advertisement);
        assertEquals(PET_DESCRIPTION_1, advertisement.getPetDescription());
        assertEquals(PET_DESCRIPTION_ERROR_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementEmptyPetDescription() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisement(advertisement.getAdvertisementId(),
                    PET_NAME_3, PET_AGE_3, " ", PET_SEX_3, PET_SPECIES_3);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(advertisement);
        assertEquals(advertisement, advertisement);
        assertEquals(PET_DESCRIPTION_1, advertisement.getPetDescription());
        assertEquals(PET_DESCRIPTION_ERROR_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementNullSex() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisement(advertisement.getAdvertisementId(),
                    PET_NAME_3, PET_AGE_3, PET_DESCRIPTION_3, null, PET_SPECIES_3);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(advertisement);
        assertEquals(advertisement, advertisement);
        assertEquals(PET_SEX_1, advertisement.getPetSex());
        assertEquals(PET_SEX_ERROR_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementNullSpecies() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisement(advertisement.getAdvertisementId(), PET_NAME_3
                    , PET_AGE_3, PET_DESCRIPTION_3, PET_SEX_3, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(advertisement);
        assertEquals(advertisement, advertisement);
        assertEquals(PET_SPECIES_1, advertisement.getPetSpecies());
        assertEquals(PET_SPECIES_ERROR_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementExpire() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        try {
            advertisement = advertisementService.updateAdvertisementIsExpired(advertisement.getAdvertisementId(), true);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(advertisement);
        assertTrue(advertisement.isIsExpired());
    }

    @Test
    public void testUpdateAdvertisementExpireInvalidId() {
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisementIsExpired(INVALID_ID, true);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(INVALID_AD_ID_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementExpireNullId(){
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisementIsExpired(null, true);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(INVALID_AD_ID_MESSAGE, error);
    }

    @Test
    public void testUpdateAdvertisementExpireEmptyId() {
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.updateAdvertisementIsExpired(" ", true);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(INVALID_AD_ID_MESSAGE, error);
    }

    @Test
    public void testGetAdvertisementById() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        String advertisementId = advertisement.getAdvertisementId();
        Advertisement adGet = null;
        try {
            adGet = advertisementService.getAdvertisementByID(advertisementId);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(advertisement);
        assertAdvertisementEquality(advertisement, adGet);
    }

    @Test
    public void testGetAdvertisementByInvalidId() {
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.getAdvertisementByID(INVALID_ID);
            System.out.println(advertisement);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNull(advertisement);
    }

    @Test
    public void testGetAdvertisementByNullId() {
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.getAdvertisementByID(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(AD_REQUIRE_ID_MESSAGE, error);
    }

    @Test
    public void testGetAdvertisementByEmptyId(){
        Advertisement advertisement = null;
        String error = null;
        try {
            advertisement = advertisementService.getAdvertisementByID(" ");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(advertisement);
        assertEquals(AD_REQUIRE_ID_MESSAGE, error);
    }

    @Test
    public void testGetAllAdvertisementsOfAppUser() {
        List<Advertisement> advertisements = null;
        Advertisement advertisement = createAppUserThenAdvertisement();
        Advertisement advertisement2 = null;
        try {
            advertisement2 = advertisementService.createAdvertisement(advertisement.getPostedBy().getEmail(), DATE_2, PET_NAME_2, PET_AGE_2,
                    PET_DESCRIPTION_2, PET_SEX_2, PET_SPECIES_2);
        } catch (IllegalArgumentException e) {
            fail();
        }

        AppUser appUser = advertisement.getPostedBy();
        appUser.addAdvertisement(advertisement2);
        String advertisementId = advertisement.getAdvertisementId();
        String advertisement2Id = advertisement2.getAdvertisementId();

        try {
            advertisements = advertisementService.getAdvertisementsOfAppUser(appUser.getEmail());
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(advertisements);
        if (advertisements.get(0).getAdvertisementId().equals(advertisementId)) {
            assertAdvertisementEquality(advertisement, advertisements.get(0));
        } else if (advertisements.get(0).getAdvertisementId().equals(advertisement2Id)) {
            assertAdvertisementEquality(advertisement2, advertisements.get(0));
        } else if (advertisements.get(1).getAdvertisementId().equals(advertisementId)) {
            assertAdvertisementEquality(advertisement, advertisements.get(1));
        } else {
            assertAdvertisementEquality(advertisement2, advertisements.get(1));
        }
    }

    @Test
    public void testGetAllAdvertisementsOfInvalidAppUser() {
        List<Advertisement> advertisements = null;
        String error = null;
        try{
            advertisements = advertisementService.getAdvertisementsOfAppUser(NON_EXISTING_APP_USER);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(advertisements);
        assertEquals(PROFILE_NOT_FOUND_MESSAGE, error);
    }

    @Test
    public void testGetAllAdvertisementsOfNullAppUser() {
        List<Advertisement> advertisements = null;
        String error = null;
        try{
            advertisements = advertisementService.getAdvertisementsOfAppUser(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(advertisements);
        assertEquals(PROFILE_NOT_FOUND_MESSAGE, error);
    }

    @Test
    public void testGetAllAdvertisementsOfEmptyAppUser() {
        List<Advertisement> advertisements = null;
        String error = null;
        try{
            advertisements = advertisementService.getAdvertisementsOfAppUser(" ");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(advertisements);
        assertEquals(PROFILE_NOT_FOUND_MESSAGE, error);
    }

    @Test
    public void testGetAllAdvertisements() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        AppUser appUser = advertisement.getPostedBy();
        AppUser appUser2 = createAppUser2();

        Advertisement advertisement2 = null;
        Advertisement advertisement3 = null;
        Advertisement advertisement4 = null;
        try {
            advertisement2 = advertisementService.createAdvertisement(appUser.getEmail(), DATE_2, PET_NAME_2, PET_AGE_2,
                    PET_DESCRIPTION_2, PET_SEX_2, PET_SPECIES_2);
            advertisement3 = advertisementService.createAdvertisement(appUser.getEmail(), DATE_2, PET_NAME_3, PET_AGE_3,
                    PET_DESCRIPTION_3, PET_SEX_3, PET_SPECIES_3);
            advertisement4 = advertisementService.createAdvertisement(appUser2.getEmail(), DATE_1, PET_NAME_3, PET_AGE_3,
                    PET_DESCRIPTION_3, PET_SEX_3, PET_SPECIES_3);

        } catch (IllegalArgumentException e) {
            fail();
        }

        appUser.addAdvertisement(advertisement2);
        appUser.addAdvertisement(advertisement3);
        appUser2.addAdvertisement(advertisement4);
        List<Advertisement> advertisements = advertisementService.getAllAdvertisements();

        assertNotNull(advertisements);
        assertEquals(4, advertisements.size());
    }

    @Test
    public void testDeleteAdvertisement() {
        Advertisement advertisement = createAppUserThenAdvertisement();
        String advertisementId = advertisement.getAdvertisementId();
        String error = null;
        try {
            advertisementService.deleteAdvertisement(advertisementId);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(error);
        assertNull(advertisementService.getAdvertisementByID(advertisementId));
    }

    @Test
    public void testDeleteAdvertisementNullId() {
        String error = null;
        try {
            advertisementService.deleteAdvertisement(null);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertEquals(INVALID_AD_ID_MESSAGE, error);
    }

    @Test
    public void testDeleteAdvertisementByEmptyId() {
        String error = null;
        try {
            advertisementService.deleteAdvertisement(" ");
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertEquals(INVALID_AD_ID_MESSAGE, error);
    }

    //Helper methods
    private AppUser createAppUser() {
        AppUser appUser = null;
        try {
            appUser = appUserService.createAppUser(USER_NAME_1, USER_EMAIL_1,USER_PASSWORD_1, USER_BIO_1, USER_HOME_1,
                    USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
        } catch (IllegalArgumentException e) {
            fail();
        }
        return appUser;
    }

    private AppUser createAppUser2() {
        AppUser appUser = null;
        try {
            appUser = appUserService.createAppUser(USER_NAME_1, USER_EMAIL_2,USER_PASSWORD_1, USER_BIO_1, USER_HOME_1,
                    USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
        } catch (IllegalArgumentException e) {
            fail();
        }
        return appUser;
    }

    private Advertisement createAppUserThenAdvertisement() {
        Advertisement advertisement = null;
        AppUser appUser = createAppUser();

        try {
            advertisement = advertisementService.createAdvertisement(appUser.getEmail(), DATE_1, PET_NAME_1, PET_AGE_1,
                    PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
        } catch (IllegalArgumentException e) {
            fail();
        }

        return advertisement;
    }


    private static void assertAdvertisementEquality(Advertisement expected, Advertisement actual) {
        assertEquals(expected.getPostedBy().getEmail(), actual.getPostedBy().getEmail());
        assertEquals(expected.getDatePosted(), actual.getDatePosted());
        assertEquals(expected.getAdvertisementId(), actual.getAdvertisementId());
        assertEquals(expected.getPetName(), actual.getPetName());
        assertEquals(expected.getPetAge(), actual.getPetAge());
        assertEquals(expected.getPetDescription(),actual.getPetDescription());
        assertEquals(expected.getPetSex(), actual.getPetSex());
        assertEquals(expected.getPetSpecies(), actual.getPetSpecies());
    }
}
