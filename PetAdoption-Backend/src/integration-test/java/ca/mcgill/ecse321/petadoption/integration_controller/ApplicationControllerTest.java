package ca.mcgill.ecse321.petadoption.integration_controller;

import ca.mcgill.ecse321.petadoption.PetAdoptionApplication;
import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;
import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.dto.AdvertisementDto;
import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.dto.ApplicationDto;
import ca.mcgill.ecse321.petadoption.model.*;
import ca.mcgill.ecse321.petadoption.service.AdvertisementService;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PetAdoptionApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ApplicationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

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

    //3 users created: User 1 posts the advertisement while user 2 and 3 apply
    private static final String USER_NAME_1 = "user 1";
    private static final String USER_EMAIL_1 = "user1@mcgill.ca";
    private static final String USER_PASSWORD_1 = "password 1";
    private static final String USER_BIO_1 = "empty";
    private static final String USER_HOME_1 = "its nice";
    private static final Integer USER_AGE_1 = 34;
    private static final Sex USER_SEX_1 = Sex.M;
    private static final boolean USER_ADMIN_1 = true;

    private static final String USER_NAME_2 = "user 2";
    private static final String USER_EMAIL_2 = "user2@mcgill.ca";
    private static final String USER_PASSWORD_2 = "password 2";
    private static final String USER_BIO_2 = "empty_ish";
    private static final String USER_HOME_2 = "not so nice";
    private static final Integer USER_AGE_2 = 23;
    private static final Sex USER_SEX_2 = Sex.F;
    private static final boolean USER_ADMIN_2 = false;

    private static final String USER_NAME_3 = "user 3";
    private static final String USER_EMAIL_3 = "user3@gmail.com";
    private static final String USER_PASSWORD_3 = "password 3";
    private static final String USER_BIO_3 = "empty_ish";
    private static final String USER_HOME_3 = "cozy";
    private static final Integer USER_AGE_3 = 80;
    private static final Sex USER_SEX_3 = Sex.M;
    private static final boolean USER_ADMIN_3 = false;

    private static final Date datePosted = Date.valueOf("2020-02-13");
    private static final String petName = "cookie";
    private static final Integer petAge = 2;
    private static final String petDescription = "blablabla";

    private static final String NOTE = "loves cats";
    private static final Date DATE_OF_SUBMISSION = Date.valueOf("2020-02-19");

    private static AppUser user1;
    private static Advertisement advertisement;
    @Autowired
    private AppUserService userService;
    @Autowired
    private AdvertisementService advertisementService;

    @BeforeEach
    public void cleanAndSetup(){
        applicationRepository.deleteAll();
        imageRepository.deleteAll();
        advertisementRepository.deleteAll();
        donationRepository.deleteAll();
        appUserRepository.deleteAll();
        user1 = userService.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
        userService.createAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                USER_HOME_2, USER_AGE_2, USER_ADMIN_2, USER_SEX_2);
        userService.createAppUser(USER_NAME_3, USER_EMAIL_3, USER_PASSWORD_3, USER_BIO_3,
                USER_HOME_3, USER_AGE_3, USER_ADMIN_3, USER_SEX_3);
        String email = user1.getEmail();
        advertisement = advertisementService.createAdvertisement(email, datePosted, petName, petAge, petDescription, Sex.F, Species.cat);
    }

    private String formatLink(String endpoint){
        return "http://localhost:" + port + endpoint;
    }

    @Test
    public void createApplication(){
        ApplicationDto app = new ApplicationDto(DATE_OF_SUBMISSION, NOTE, advertisement.getAdvertisementId(), USER_EMAIL_2, null, Status.pending);
        HttpEntity<ApplicationDto> entity5 = new HttpEntity<ApplicationDto>(app, headers);

        ApplicationDto returned_app = restTemplate.postForObject(formatLink("/applications/create"),
                entity5, ApplicationDto.class);

        TestUtils.assertApplication(returned_app, advertisement.getAdvertisementId(), USER_EMAIL_2, DATE_OF_SUBMISSION, NOTE, Status.pending);
    }

    @Test
    public void getAllApplicationsForAd(){
        ApplicationDto app = new ApplicationDto(DATE_OF_SUBMISSION, NOTE+"user2", advertisement.getAdvertisementId(), USER_EMAIL_2, null, Status.pending);
        HttpEntity<ApplicationDto> entity5 = new HttpEntity<ApplicationDto>(app, headers);

        ApplicationDto returned_app = restTemplate.postForObject(formatLink("/applications/create"),
                entity5, ApplicationDto.class);

        app = new ApplicationDto(DATE_OF_SUBMISSION, NOTE+"user3", advertisement.getAdvertisementId(), USER_EMAIL_3, null, Status.pending);
        entity5 = new HttpEntity<ApplicationDto>(app, headers);

        returned_app = restTemplate.postForObject(formatLink("/applications/create"),
                entity5, ApplicationDto.class);
        ResponseEntity<ApplicationDto[]> response =restTemplate.getForEntity(
                formatLink("/applications/"+ advertisement.getAdvertisementId()),
                ApplicationDto[].class);
        ApplicationDto[] lst = response.getBody();
        TestUtils.assertApplication(lst[0], advertisement.getAdvertisementId(), USER_EMAIL_2, DATE_OF_SUBMISSION, NOTE+"user2", Status.pending);
        TestUtils.assertApplication(lst[1], advertisement.getAdvertisementId(), USER_EMAIL_3, DATE_OF_SUBMISSION, NOTE+"user3", Status.pending);
    }

    @Test
    public void deleteApplication(){
        ApplicationDto app = new ApplicationDto(DATE_OF_SUBMISSION, NOTE+"user2", advertisement.getAdvertisementId(), USER_EMAIL_2, null, Status.pending);
        HttpEntity<ApplicationDto> entity5 = new HttpEntity<ApplicationDto>(app, headers);

        ApplicationDto returned_app = restTemplate.postForObject(formatLink("/applications/create"),
                entity5, ApplicationDto.class);
        restTemplate.delete(formatLink("/application/delete/"+returned_app.getApplicationId()));
        ResponseEntity<String> response =restTemplate.getForEntity(
                formatLink("/application/"+returned_app.getApplicationId()),
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("This application does not exist."));
    }

    @Test
    public void updateApplication(){
        ApplicationDto app = new ApplicationDto(DATE_OF_SUBMISSION, NOTE, advertisement.getAdvertisementId(), USER_EMAIL_2, null, Status.pending);
        HttpEntity<ApplicationDto> entity5 = new HttpEntity<ApplicationDto>(app, headers);

        ApplicationDto returned_app = restTemplate.postForObject(formatLink("/applications/create"),
                entity5, ApplicationDto.class);
        app.setStatus(Status.accepted);
        app.setApplicationId(returned_app.getApplicationId());
        entity5 = new HttpEntity<ApplicationDto>(app, headers);
        restTemplate.put(formatLink("/application/update"),
                entity5);
        returned_app =restTemplate.getForObject(
                formatLink("/application/"+returned_app.getApplicationId()),
                ApplicationDto.class);
        TestUtils.assertApplication(returned_app, advertisement.getAdvertisementId(), USER_EMAIL_2, DATE_OF_SUBMISSION, NOTE, Status.accepted);
    }
    @Test
    public void getApplication(){
        ApplicationDto app = new ApplicationDto(DATE_OF_SUBMISSION, NOTE, advertisement.getAdvertisementId(), USER_EMAIL_2, null, Status.pending);
        HttpEntity<ApplicationDto> entity5 = new HttpEntity<ApplicationDto>(app, headers);

        ApplicationDto returned_app = restTemplate.postForObject(formatLink("/applications/create"),
                entity5, ApplicationDto.class);
        String id = returned_app.getApplicationId();
        returned_app = null;
        returned_app =restTemplate.getForObject(
                formatLink("/application/"+id),
                ApplicationDto.class);
        TestUtils.assertApplication(returned_app, advertisement.getAdvertisementId(), USER_EMAIL_2, DATE_OF_SUBMISSION, NOTE, Status.pending);
    }


}
