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
import java.util.HashSet;

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

    private static final boolean IS_EXPIRED_1 = false;
    private static final String PET_NAME_1 = "Rusty";
    private static final Integer PET_AGE_1 = 10;
    private static final String PET_DESCRIPTION_1 = "Not Rusty at all";
    private static final Sex PET_SEX_1 = Sex.M;
    private static final Species PET_SPECIES_1 = Species.dog;

    private static final boolean IS_EXPIRED_2 = false;
    private static final String PET_NAME_2 = "Snow";
    private static final Integer PET_AGE_2 = 15;
    private static final String PET_DESCRIPTION_2 = "Not Snowy at all";
    private static final Sex PET_SEX_2 = Sex.F;
    private static final Species PET_SPECIES_2 = Species.cat;

    private static final boolean IS_EXPIRED_3 = false;
    private static final String PET_NAME_3 = "Chirpy";
    private static final Integer PET_AGE_3 = 20;
    private static final String PET_DESCRIPTION_3 = "Not Chirpy at all";
    private static final Sex PET_SEX_3 = Sex.F;
    private static final Species PET_SPECIES_3 = Species.bird;

    private static final Integer NEGATIVE_AGE = -7;

    private static final Date DATE_1 = Date.valueOf("2020-07-02");
    private static final Date DATE_2 = Date.valueOf("2020-06-07");

    //Mock unique ID keys for advertisements
    private static final String ADVERTISEMENT_1_ID = "zwVC9mb";
    private static final String ADVERTISEMENT_2_ID = "3klFrbp";
    private static final String ADVERTISEMENT_3_ID = "MEM6jFl";
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

    private static final AppUser APP_USER_1 = new AppUser();
    private static final AppUser APP_USER_2 = new AppUser();

    //Create Mock Advertisement Objects
    private static final Advertisement ADVERTISEMENT_1 = createAdvertisement(APP_USER_1, DATE_1, ADVERTISEMENT_1_ID,
            IS_EXPIRED_1, PET_NAME_1, PET_AGE_1, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
    private static final Advertisement ADVERTISEMENT_2 = createAdvertisement(APP_USER_2, DATE_1, ADVERTISEMENT_2_ID,
            IS_EXPIRED_2, PET_NAME_2, PET_AGE_2, PET_DESCRIPTION_2, PET_SEX_2, PET_SPECIES_2);
    private static final Advertisement ADVERTISEMENT_3 = createAdvertisement(APP_USER_2, DATE_2, ADVERTISEMENT_3_ID,
            IS_EXPIRED_3, PET_NAME_3, PET_AGE_3, PET_DESCRIPTION_3, PET_SEX_3, PET_SPECIES_3);

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

    private static Advertisement createAdvertisement(AppUser appUser, Date datePosted, String id, boolean isExpired, String petName,
                                                     Integer petAge, String petDescription, Sex petSex, Species petSpecies) {
        Advertisement advertisement = new Advertisement();
        advertisement.setDatePosted(datePosted);
        advertisement.setAdvertisementId(id);
        advertisement.setIsExpired(isExpired);
        advertisement.setPetName(petName);
        advertisement.setPetAge(petAge);
        advertisement.setPetDescription(petDescription);
        advertisement.setPetSex(petSex);
        advertisement.setPetSpecies(petSpecies);

        advertisement.setPostedBy(appUser);
        advertisement.setPetImages(new HashSet<>());
        advertisement.setApplications(new HashSet<>());

        return advertisement;
    }
}
