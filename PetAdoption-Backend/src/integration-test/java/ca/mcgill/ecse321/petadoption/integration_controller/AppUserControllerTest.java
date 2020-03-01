package ca.mcgill.ecse321.petadoption.integration_controller;


import ca.mcgill.ecse321.petadoption.PetAdoptionApplication;
import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;
import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Sex;
import org.junit.Before;
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
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PetAdoptionApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AppUserControllerTest {
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

    private static final String USER_NAME_2 = "user 2";
    private static final String USER_EMAIL_2 = "user2@mcgill.ca";
    private static final String USER_PASSWORD_2 = "password 2";
    private static final String USER_BIO_2 = "empty_ish";
    private static final String USER_HOME_2 = "not so nice";
    private static final Integer USER_AGE_2 = 23;
    private static final Sex USER_SEX_2 = Sex.F;
    private static final boolean USER_ADMIN_2 = false;

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

    }

    private String formatLink(String endpoint){
        return "http://localhost:" + port + endpoint;
    }

    @Test
    public void createAppUser(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        HttpEntity<AppUserDto> entity = new HttpEntity<AppUserDto>(user, headers);

        AppUserDto returned_user = restTemplate.postForObject(formatLink("/register"),
                entity, AppUserDto.class);

        TestUtils.assertAppUser(returned_user,USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1  );
    }

    @Test
    public void testCreateDuplicateAppUser(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        HttpEntity<AppUserDto> entity = new HttpEntity<AppUserDto>(user, headers);

        AppUserDto returned_user = restTemplate.postForObject(formatLink("/register"),
                entity, AppUserDto.class);

        TestUtils.assertAppUser(returned_user,USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1  );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("The email " + USER_EMAIL_1+ " is already used."));
    }

    @Test
    public void testCreateAppUserNullName(){

        AppUserDto user = new AppUserDto(null, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("name cannot be empty! "));
    }

    @Test
    public void testCreateAppUserEmptyName(){

        AppUserDto user = new AppUserDto("", USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("name cannot be empty! "));
    }
    @Test
    public void testCreateAppUserNullEmail(){

        AppUserDto user = new AppUserDto(USER_NAME_1, null, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("email cannot be empty "));

    }
    @Test
    public void testCreateAppUserEmptyEmail(){

        AppUserDto user = new AppUserDto(USER_NAME_1, "", USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("email cannot be empty "));

    }
    @Test
    public void testCreateAppUserInvalidEmail(){
        String invalidEmail = "inavlid";

        AppUserDto user = new AppUserDto(USER_NAME_1, invalidEmail, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("the email " + invalidEmail + " doesn't have a valid format."));
    }

    @Test
    public void testCreateAppUserNullPassword(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, null, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("password cannot be empty "));
    }
    @Test
    public void testCreateAppUserEmptyPassword(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("password cannot be empty "));
    }
    @Test
    public void testCreateAppUserNullBiography(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, null,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("biography cannot be empty "));
    }
    @Test
    public void testCreateAppUserEmptyBiography(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, "",
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("biography cannot be empty "));
    }
    @Test
    public void testCreateAppUserNullHomeDescription(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                null,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("homeDescription cannot be empty "));
    }
    @Test
    public void testCreateAppUserEmptyHomeDescription(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                "",USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("homeDescription cannot be empty "));
    }

    @Test
    public void testCreateAppUserZeroAge(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,0,USER_ADMIN_1, USER_SEX_1 );

        ResponseEntity<String> response =restTemplate.postForEntity(
                formatLink("/register"),user,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("age is not valid "));
    }

    @Test
    public void getAppUser(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        HttpEntity<AppUserDto> entity = new HttpEntity<AppUserDto>(user, headers);

        AppUserDto returned_user = restTemplate.postForObject(formatLink("/register"),
                entity, AppUserDto.class);

        TestUtils.assertAppUser(returned_user,USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1  );
        returned_user = null;
        returned_user = restTemplate.getForObject(formatLink("/getUser/"+USER_EMAIL_1),
                AppUserDto.class);
        TestUtils.assertAppUser(returned_user,USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1  );
    }

    @Test
    public void getAppUserNonExistent(){

        ResponseEntity<String> response =restTemplate.getForEntity(
                formatLink("/getUser/"+USER_EMAIL_1),
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("The user with email "+ USER_EMAIL_1 +" does not exist."));

    }

    @Test
    public void getAppUserNullEmail(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        HttpEntity<AppUserDto> entity = new HttpEntity<AppUserDto>(user, headers);

        AppUserDto returned_user = restTemplate.postForObject(formatLink("/register"),
                entity, AppUserDto.class);

        TestUtils.assertAppUser(returned_user,USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1  );
        ResponseEntity<String> response =restTemplate.getForEntity(
                formatLink("/getUser/"+null),
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("The user with email "+ null +" does not exist."));
    }

    @Test
    public void getAppUserEmptyEmail(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        HttpEntity<AppUserDto> entity = new HttpEntity<AppUserDto>(user, headers);

        AppUserDto returned_user = restTemplate.postForObject(formatLink("/register"),
                entity, AppUserDto.class);

        TestUtils.assertAppUser(returned_user,USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1  );
        ResponseEntity<String> response =restTemplate.getForEntity(
                formatLink("/getUser/"+""),
                String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void updateAppUser(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        HttpEntity<AppUserDto> entity = new HttpEntity<AppUserDto>(user, headers);

        AppUserDto returned_user = restTemplate.postForObject(formatLink("/register"),
                entity, AppUserDto.class);

        TestUtils.assertAppUser(returned_user,USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1  );
        returned_user = null;
        user = new AppUserDto(USER_NAME_2, USER_EMAIL_1, USER_PASSWORD_2, USER_BIO_2,
                USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2 );
        entity = new HttpEntity<AppUserDto>(user, headers);
        restTemplate.put(formatLink("/updateUser/"),
                entity);
        returned_user = restTemplate.getForObject(formatLink("/getUser/"+USER_EMAIL_1),
                AppUserDto.class);
        TestUtils.assertAppUser(returned_user,USER_NAME_2, USER_EMAIL_1, "", USER_BIO_2,
                USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2   );
    }

    @Test
    public void testGetAllUsers(){

        AppUserDto user1 = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        HttpEntity<AppUserDto> entity1 = new HttpEntity<AppUserDto>(user1, headers);

        AppUserDto returned_user1 = restTemplate.postForObject(formatLink("/register"),
                entity1, AppUserDto.class);

        AppUserDto user2 = new AppUserDto(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2 );
        HttpEntity<AppUserDto> entity2 = new HttpEntity<AppUserDto>(user2, headers);

        AppUserDto returned_user2 = restTemplate.postForObject(formatLink("/register"),
                entity2, AppUserDto.class);

        ResponseEntity<AppUserDto[]> response =restTemplate.getForEntity(
                formatLink("/getAllUsers"),
                AppUserDto[].class);
        AppUserDto[] lst = response.getBody();

        TestUtils.assertAppUser(lst[0],USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        TestUtils.assertAppUser(lst[1],USER_NAME_2, USER_EMAIL_2, "", USER_BIO_2,
                USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2);
    }

    @Test
    public void deleteAppUser(){
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        HttpEntity<AppUserDto> entity = new HttpEntity<AppUserDto>(user, headers);

        AppUserDto returned_user = restTemplate.postForObject(formatLink("/register"),
                entity, AppUserDto.class);

        TestUtils.assertAppUser(returned_user,USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1  );
        returned_user = null;
        restTemplate.delete(formatLink("/delete/user/"+USER_EMAIL_1));
        ResponseEntity<String> response =restTemplate.getForEntity(
                formatLink("/getUser/"+USER_EMAIL_1),
                String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("The user with email "+ USER_EMAIL_1 +" does not exist."));
    }

}
