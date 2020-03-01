package ca.mcgill.ecse321.petadoption.integration_controller;

import ca.mcgill.ecse321.petadoption.PetAdoptionApplication;
import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;
import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.dto.ImageDto;
import ca.mcgill.ecse321.petadoption.model.*;
import ca.mcgill.ecse321.petadoption.service.ImageService;
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

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PetAdoptionApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ImageControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    private static final String USER_NAME_1 = "user 1";
    private static final String USER_EMAIL_1 = "user1@mcgill.ca";
    private static final String USER_PASSWORD_1 = "password 1";
    private static final String USER_BIO_1 = "empty";
    private static final String USER_HOME_1 = "its nice";
    private static final Integer USER_AGE_1 = 34;
    private static final Sex USER_SEX_1 = Sex.M;
    private static final boolean USER_ADMIN_1 = true;

    private static final String IMAGE_NAME_1 = "doggy 1";
    private static final String IMAGE_LINK_1 = "doggy_1.com";
    private static final String IMAGE_ID_1 = "id1";

    private static final String IMAGE_NAME_2 = "doggy 2";
    private static final String IMAGE_LINK_2 = "doggy_2.com";
    private static final String IMAGE_ID_2 = "id2";

    private static final Date ADVERTISEMENT_POSTDATE_1 = Date.valueOf(LocalDate.of(2020, Month.FEBRUARY, 7));
    private static final String ADVERTISEMENT_ID_1 = "ad-id";
    private static final boolean ADVERTISEMENT_ISEXPIRED_1 = false;
    private static final String PET_NAME = "";
    private static final int  PET_AGE = 3;
    private static final String PET_DESCRIPTION = "cute lovely";
    private static final Sex PET_SEX = Sex.M;
    private static final Species PET_SPECIE = Species.bird;

    private static final AppUser user = TestUtils.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1, USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
    private static Advertisement ad = TestUtils.createAdvertisement(user, ADVERTISEMENT_POSTDATE_1, ADVERTISEMENT_ID_1, ADVERTISEMENT_ISEXPIRED_1, PET_NAME, PET_AGE, PET_DESCRIPTION, PET_SEX, PET_SPECIE );



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
    public void cleanAndSetup(){
        applicationRepository.deleteAll();
        imageRepository.deleteAll();
        advertisementRepository.deleteAll();
        donationRepository.deleteAll();
        appUserRepository.deleteAll();
        appUserRepository.save(user);
        advertisementRepository.save(ad);
    }


    private String formatLink(String endpoint){
        return "http://localhost:" + port + endpoint;
    }

    @Test
    public void testCreateImage(){
        ImageDto image = new ImageDto(IMAGE_NAME_1, IMAGE_LINK_1, "", ADVERTISEMENT_ID_1);

        HttpEntity<ImageDto> entity = new HttpEntity<ImageDto>(image, headers);

        ImageDto returned_image = restTemplate.postForObject(formatLink("/image/create/"),
                entity, ImageDto.class);

        assertEquals(ADVERTISEMENT_ID_1, returned_image.getAdvertisementId());
        assertEquals(IMAGE_NAME_1, returned_image.getName());
        assertEquals(IMAGE_LINK_1, returned_image.getLink());
    }

    @Test
    public void testCreateImageNullName(){
        ImageDto image = new ImageDto(null, IMAGE_LINK_1, "", ADVERTISEMENT_ID_1);


        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/image/create/"),image,
                String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("name can not be empty "));
    }

    @Test
    public void testCreateImageEmptyName(){
        ImageDto image = new ImageDto("", IMAGE_LINK_1, "", ADVERTISEMENT_ID_1);


        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/image/create/"),image,
                String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("name can not be empty "));
    }

    @Test
    public void testCreateImageNullLink(){
        ImageDto image = new ImageDto(null, IMAGE_LINK_1, "", ADVERTISEMENT_ID_1);


        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/image/create/"),image,
                String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("link can not be empty "));
    }

    @Test
    public void testCreateImageEmptyLink(){
        ImageDto image = new ImageDto("", IMAGE_LINK_1, "", ADVERTISEMENT_ID_1);


        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/image/create/"),image,
                String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("link can not be empty "));
    }



}
