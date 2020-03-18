package ca.mcgill.ecse321.petadoption.integration_controller;

import ca.mcgill.ecse321.petadoption.PetAdoptionApplication;
import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.dto.AdvertisementDto;
import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.model.Sex;
import ca.mcgill.ecse321.petadoption.model.Species;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PetAdoptionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AdvertisementControllerTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private DonationRepository donationRepository;

    // Constants to create test appUser and advertisement objects
    private static final String USER_NAME_1 = "user 1";
    private static final String USER_EMAIL_1 = "user1@mcgill.ca";
    private static final String USER_PASSWORD_1 = "password 1";
    private static final String USER_BIO_1 = "empty";
    private static final String USER_HOME_1 = "its nice";
    private static final Integer USER_AGE_1 = 34;
    private static final Sex USER_SEX_1 = Sex.M;
    private static final boolean USER_ADMIN_1 = true;

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

    private static final Date DATE_1 = Date.valueOf("2020-07-02");
    private static final Date DATE_2 = Date.valueOf("2020-06-07");

    private static final String INVALID_ID = "a47sxj9";

    private static final String PROFILE_NOT_FOUND_MESSAGE = "User profile not found. App user does not exist!";
    private static final String NO_SUCH_AD_MESSAGE = "There is no such Advertisement!";


    @BeforeEach
    public void cleanDataBase() {
        imageRepository.deleteAll();
        applicationRepository.deleteAll();
        advertisementRepository.deleteAll();
        donationRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    private String formatLink(String endpoint) {
        return "http://localhost:" + port + endpoint;
    }

    @Test
    public void testCreateAdvertisement() {
        AdvertisementDto returned_advertisement = createAdvertisementAndReturn();

        assertEquals(USER_EMAIL_1,returned_advertisement.getUserEmail());
        assertFalse(returned_advertisement.isExpired());
        assertEquals(PET_NAME_1,returned_advertisement.getPetName());
        assertEquals(PET_AGE_1,returned_advertisement.getPetAge());
        assertEquals(PET_DESCRIPTION_1, returned_advertisement.getPetDescription());
        assertEquals(PET_SEX_1, returned_advertisement.getPetSex());
        assertEquals(PET_SPECIES_1, returned_advertisement.getPetSpecies());
    }

    private AdvertisementDto createAdvertisementAndReturn() {
        AppUserDto appUserDto = createAppUserDto();
        AdvertisementDto adDto = new AdvertisementDto(appUserDto.getEmail(), DATE_1, null, false,
                PET_NAME_1, PET_AGE_1, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
        HttpEntity<AdvertisementDto> entity = new HttpEntity<>(adDto, headers);

        AdvertisementDto returned_advertisement = restTemplate.postForObject(formatLink("/"+
                appUserDto.getEmail()+"/advertisement/create/?date="+DATE_1), entity, AdvertisementDto.class);
        assertNotNull(returned_advertisement);

        return returned_advertisement;
    }

    @Test
    public void testUpdateAdvertisement() {
        AdvertisementDto returned_advertisement = createAdvertisementAndReturn();
        //Updating advertisement
        String advertisementId = returned_advertisement.getAdvertisementId();
        returned_advertisement = null;
        AdvertisementDto adDto2 = new AdvertisementDto(USER_EMAIL_1, DATE_1, null, false,
                PET_NAME_2, PET_AGE_2, PET_DESCRIPTION_2, PET_SEX_2, PET_SPECIES_2);
        HttpEntity<AdvertisementDto> entity = new HttpEntity<>(adDto2, headers);
        restTemplate.put(formatLink("/advertisement/update/?adId="+advertisementId), entity);

        returned_advertisement = restTemplate.getForObject(formatLink("/advertisement/"+advertisementId),
                AdvertisementDto.class);

        assertNotNull(returned_advertisement);
        assertEquals(USER_EMAIL_1,returned_advertisement.getUserEmail());
        assertEquals(advertisementId, returned_advertisement.getAdvertisementId());
        assertFalse(returned_advertisement.isExpired());
        assertEquals(PET_NAME_2,returned_advertisement.getPetName());
        assertEquals(PET_AGE_2,returned_advertisement.getPetAge());
        assertEquals(PET_DESCRIPTION_2, returned_advertisement.getPetDescription());
        assertEquals(PET_SEX_2, returned_advertisement.getPetSex());
        assertEquals(PET_SPECIES_2, returned_advertisement.getPetSpecies());
    }

    @Test
    public void testUpdateAdvertisementExpiry() {
        AdvertisementDto returned_advertisement = createAdvertisementAndReturn();
        assertFalse(returned_advertisement.isExpired());
        //Updating advertisement
        AdvertisementDto adDto2 = new AdvertisementDto(USER_EMAIL_1, DATE_1, null, false,
                PET_NAME_2, PET_AGE_2, PET_DESCRIPTION_2, PET_SEX_2, PET_SPECIES_2);
        HttpEntity<AdvertisementDto> entity = new HttpEntity<>(adDto2, headers);
        String advertisementId = returned_advertisement.getAdvertisementId();
        restTemplate.put(formatLink("/advertisement/updateExpiry/?adId="+advertisementId+"&expired="+true), entity);

        returned_advertisement = restTemplate.getForObject(formatLink("/advertisement/"+advertisementId),
                AdvertisementDto.class);

        assertNotNull(returned_advertisement);
        assertTrue(returned_advertisement.isExpired());
    }

    @Test
    public void testGetAllAdvertisements() {
        AdvertisementDto returned_advertisement = createAdvertisementAndReturn();
        AdvertisementDto adDto2 = new AdvertisementDto(USER_EMAIL_1, DATE_1, null, false,
                PET_NAME_2, PET_AGE_2, PET_DESCRIPTION_2, PET_SEX_2, PET_SPECIES_2);

        //Make the advertisement to update
        HttpEntity<AdvertisementDto> entity2 = new HttpEntity<>(adDto2, headers);
        AdvertisementDto returned_advertisement2 = restTemplate.postForObject(formatLink("/"+
                USER_EMAIL_1+"/advertisement/create/?date="+DATE_2), entity2, AdvertisementDto.class);

        ResponseEntity<AdvertisementDto[]> responseEntity = restTemplate.getForEntity(
                formatLink("/advertisements"), AdvertisementDto[].class);

        AdvertisementDto[] advertisementDtos = responseEntity.getBody();

        assertNotNull(responseEntity);
        assertEquals(2, Objects.requireNonNull(advertisementDtos).length);
        if (advertisementDtos[0].getAdvertisementId().equals(returned_advertisement.getAdvertisementId())) {
            assertAdvertisementEquality(returned_advertisement, advertisementDtos[0]);
        } else if (advertisementDtos[0].getAdvertisementId().equals(adDto2.getAdvertisementId())) {
            assertAdvertisementEquality(returned_advertisement2, advertisementDtos[0]);
        } else if (advertisementDtos[1].getAdvertisementId().equals(returned_advertisement.getAdvertisementId())) {
            assertAdvertisementEquality(returned_advertisement, advertisementDtos[1]);
        } else if (advertisementDtos[1].getAdvertisementId().equals(adDto2.getAdvertisementId())) {
            assertAdvertisementEquality(returned_advertisement2, advertisementDtos[1]);
        }
    }

    @Test
    public void testGetAdvertisementsByAppUser() {
        AdvertisementDto returned_advertisement = createAdvertisementAndReturn();

        String advertisementId = returned_advertisement.getAdvertisementId();

        ResponseEntity<AdvertisementDto[]> responseEntity = restTemplate.getForEntity(
                formatLink("/"+USER_EMAIL_1+"/advertisements/"),
                AdvertisementDto[].class);
        AdvertisementDto[] advertisementDtos = responseEntity.getBody();

        assertNotNull(advertisementDtos);
        assertEquals(1, Objects.requireNonNull(advertisementDtos).length);
        assertEquals(advertisementId, advertisementDtos[0].getAdvertisementId());
    }

    @Test
    public void testGetAdvertisementsByInvalidAppUser() {
        createAdvertisementAndReturn();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                formatLink("/"+NON_EXISTING_APP_USER+"/advertisements/"),
                String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).contains(PROFILE_NOT_FOUND_MESSAGE));
    }

    @Test
    public void testGetAdvertisementById() {
        AdvertisementDto returned_advertisement = createAdvertisementAndReturn();
        String advertisementId = returned_advertisement.getAdvertisementId();
        returned_advertisement = restTemplate.getForObject(
                formatLink("/advertisement/"+advertisementId), AdvertisementDto.class);

        assertNotNull(returned_advertisement);
        assertEquals(advertisementId, returned_advertisement.getAdvertisementId());
        assertEquals(USER_EMAIL_1,returned_advertisement.getUserEmail());
        assertFalse(returned_advertisement.isExpired());
        assertEquals(PET_NAME_1,returned_advertisement.getPetName());
        assertEquals(PET_AGE_1,returned_advertisement.getPetAge());
        assertEquals(PET_DESCRIPTION_1, returned_advertisement.getPetDescription());
        assertEquals(PET_SEX_1, returned_advertisement.getPetSex());
        assertEquals(PET_SPECIES_1, returned_advertisement.getPetSpecies());
    }

    @Test
    public void testGetAdvertisementByInvalidId() {
        createAdvertisementAndReturn();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                formatLink("/advertisement/"+INVALID_ID), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).contains(NO_SUCH_AD_MESSAGE));
    }

    @Test
    public void testDeleteAdvertisement() {
        AppUserDto appUserDto = createAppUserDto();
        AdvertisementDto adDto1 = new AdvertisementDto(appUserDto.getEmail(), DATE_1, null, false,
                PET_NAME_1, PET_AGE_1, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);

        HttpEntity<AdvertisementDto> entity = new HttpEntity<>(adDto1, headers);
        AdvertisementDto returned_advertisement = restTemplate.postForObject(formatLink("/"+
                appUserDto.getEmail()+"/advertisement/create/?date="+DATE_1), entity, AdvertisementDto.class);
        assertNotNull(returned_advertisement);

        String advertisementId = returned_advertisement.getAdvertisementId();
        restTemplate.delete(formatLink("/advertisement/delete?adId="+advertisementId), entity);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                formatLink("/advertisement/"+advertisementId), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).contains(NO_SUCH_AD_MESSAGE));
    }

    private AppUserDto createAppUserDto() {
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        HttpEntity<AppUserDto> entity = new HttpEntity<AppUserDto>(user, headers);

        return restTemplate.postForObject(formatLink("/register"),
                entity, AppUserDto.class);
    }

    private static void assertAdvertisementEquality(AdvertisementDto expected, AdvertisementDto actual) {
        assertEquals(expected.getUserEmail(), actual.getUserEmail());
        assertEquals(expected.getAdvertisementId(), actual.getAdvertisementId());
        assertEquals(expected.getPetName(), actual.getPetName());
        assertEquals(expected.getPetAge(), actual.getPetAge());
        assertEquals(expected.getPetDescription(),actual.getPetDescription());
        assertEquals(expected.getPetSex(), actual.getPetSex());
        assertEquals(expected.getPetSpecies(), actual.getPetSpecies());
    }
}
