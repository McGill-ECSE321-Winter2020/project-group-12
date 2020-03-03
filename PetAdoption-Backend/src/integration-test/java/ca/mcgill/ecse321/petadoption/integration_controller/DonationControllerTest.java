package ca.mcgill.ecse321.petadoption.integration_controller;

import ca.mcgill.ecse321.petadoption.PetAdoptionApplication;
import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;
import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.dto.DonationDto;
import ca.mcgill.ecse321.petadoption.model.Sex;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PetAdoptionApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DonationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    private static final String USER_NAME_1 = "user 1";
    private static final String USER_EMAIL_1 = "user1@mcgill.ca";
    private static final String USER_PASSWORD_1 = "password 1";
    private static final String USER_BIO_1 = "empty";
    private static final String USER_HOME_1 = "its nice";
    private static final Integer USER_AGE_1 = 34;
    private static final Sex USER_SEX_1 = Sex.M;
    private static final boolean USER_ADMIN_1 = true;

    private static final Integer DONATION_AMT_1 = 50;
    private static final Date DONATION_DATE_1 = Date.valueOf("2020-01-10");
    private static final Integer DONATION_AMT_2 = 30;
    private static final Date DONATION_DATE_2 = Date.valueOf("2020-02-14");

    private static final String NON_EXISTING_USER_EMAIL = "nonexisting@gmail.com";

    @BeforeEach
    public void cleanDataBase() {
        donationRepository.deleteAll();
        imageRepository.deleteAll();
        applicationRepository.deleteAll();
        advertisementRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    private String formatLink(String endpoint){
        return "http://localhost:" + port + endpoint;
    }

    public void createUser(String userEmail) {
        AppUserDto user = new AppUserDto(USER_NAME_1, userEmail, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        HttpEntity<AppUserDto> entity = new HttpEntity<AppUserDto>(user, headers);

        AppUserDto returned_user = restTemplate.postForObject(formatLink("/register"),
                entity, AppUserDto.class);

        TestUtils.assertAppUser(returned_user,USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1);
    }

    @Test
    public void testCreateDonation(){
        createUser(USER_EMAIL_1);
        DonationDto donation = new DonationDto(USER_EMAIL_1, DONATION_DATE_1, DONATION_AMT_1, null); // when donation is created it gets assigned a random UUID string as an ID
        HttpEntity<DonationDto> entity = new HttpEntity<DonationDto>(donation, headers);
        DonationDto returned_donation = restTemplate.postForObject(formatLink("/"+USER_EMAIL_1+"/donations"), entity, DonationDto.class);
        TestUtils.assertDonation(returned_donation, USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
    }

    @Test
    public void testCreateDonationDonorNullEmail() {
        DonationDto donation = new DonationDto(null, DONATION_DATE_1, DONATION_AMT_1, null);
        ResponseEntity<String> response = restTemplate.postForEntity(formatLink("/"+ null + "/donations"), donation, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Donation cannot be created without a donor!"));
    }

    @Test
    public void testCreateDonationDonorEmailEmpty() {
        DonationDto donation = new DonationDto("", DONATION_DATE_1, DONATION_AMT_1, null);
        ResponseEntity<String> response = restTemplate.postForEntity(formatLink("/"+ "" +"/donations"), donation, String.class);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    public void testCreateDonationNonExistingDonor() {
        DonationDto donation = new DonationDto(NON_EXISTING_USER_EMAIL, DONATION_DATE_1, DONATION_AMT_1, null);
        ResponseEntity<String> response = restTemplate.postForEntity(formatLink("/"+NON_EXISTING_USER_EMAIL+"/donations"), donation, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Donation cannot be created without a donor!"));
    }

    @Test
    public void testCreateDonationAmountNull() {
        createUser(USER_EMAIL_1);
        DonationDto donation = new DonationDto(USER_EMAIL_1, DONATION_DATE_1, null, null);
        ResponseEntity<String> response = restTemplate.postForEntity(formatLink("/"+USER_EMAIL_1+"/donations"), donation, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Cannot make a donation with unspecified amount"));
    }

    @Test
    public void testCreateDonationInvalidAmount0() {
        createUser(USER_EMAIL_1);
        DonationDto donation = new DonationDto(USER_EMAIL_1, DONATION_DATE_1, 0, null);
        ResponseEntity<String> response = restTemplate.postForEntity(formatLink("/"+USER_EMAIL_1+"/donations"), donation, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Cannot make a donation of $0 or less"));
    }

    @Test
    public void testCreateDonationInvalidAmountNegative() {
        createUser(USER_EMAIL_1);
        DonationDto donation = new DonationDto(USER_EMAIL_1, DONATION_DATE_1, -5, null);
        ResponseEntity<String> response = restTemplate.postForEntity(formatLink("/"+USER_EMAIL_1+"/donations"), donation, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Cannot make a donation of $0 or less"));
    }

    @Test
    public void testCreateDonationDateNull() {
        createUser(USER_EMAIL_1);
        DonationDto donation = new DonationDto(USER_EMAIL_1, null, DONATION_AMT_1, null);
        ResponseEntity<String> response = restTemplate.postForEntity(formatLink("/"+USER_EMAIL_1+"/donations"), donation, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Donation made cannot be missing a date of payment"));
    }

    @Test
    public void testGetAllDonations() {
        createUser(USER_EMAIL_1);
        DonationDto donation1 = new DonationDto(USER_EMAIL_1, DONATION_DATE_1, DONATION_AMT_1, null); // when donation is created it gets assigned a random UUID string as an ID
        HttpEntity<DonationDto> entity1 = new HttpEntity<DonationDto>(donation1, headers);
        restTemplate.postForObject(formatLink("/"+USER_EMAIL_1+"/donations"), entity1, DonationDto.class);

        DonationDto donation2 = new DonationDto(USER_EMAIL_1, DONATION_DATE_2, DONATION_AMT_2, null); // when donation is created it gets assigned a random UUID string as an ID
        HttpEntity<DonationDto> entity2 = new HttpEntity<DonationDto>(donation2, headers);
        restTemplate.postForObject(formatLink("/"+USER_EMAIL_1+"/donations"), entity2, DonationDto.class);

        ResponseEntity<DonationDto[]> response =restTemplate.getForEntity(
                formatLink("/donations"),
                DonationDto[].class);

        DonationDto[] lst = response.getBody();

        TestUtils.assertDonation(lst[0], USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
        TestUtils.assertDonation(lst[1], USER_EMAIL_1, DONATION_AMT_2, DONATION_DATE_2);
    }

    // fails weirdly; come bck to it ltr
    @Test
    public void testGetDonationByID() {
        createUser(USER_EMAIL_1);
        DonationDto donation = new DonationDto(USER_EMAIL_1, DONATION_DATE_1, DONATION_AMT_1, null);
        HttpEntity<DonationDto> entity = new HttpEntity<DonationDto>(donation, headers);
        DonationDto returned_donation = restTemplate.postForObject(formatLink("/"+USER_EMAIL_1+"/donations"), entity, DonationDto.class);
        String id = returned_donation.getTransactionID();

        returned_donation = null;
        returned_donation = restTemplate.getForObject(formatLink("/donations/ids/"+id),
                DonationDto.class);
        TestUtils.assertDonation(returned_donation, USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
    }

    @Test
    public void testGetDonationByUserEmail() {
        createUser(USER_EMAIL_1);
        DonationDto donation = new DonationDto(USER_EMAIL_1, DONATION_DATE_1, DONATION_AMT_1, null); // when donation is created it gets assigned a random UUID string as an ID
        HttpEntity<DonationDto> entity = new HttpEntity<DonationDto>(donation, headers);
        restTemplate.postForObject(formatLink("/"+USER_EMAIL_1+"/donations"), entity, DonationDto.class);

        DonationDto[] donationFound = restTemplate.getForObject(formatLink("/donations/users/" + USER_EMAIL_1),
               DonationDto[].class);

        TestUtils.assertDonation(donationFound[0], USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
    }

//    @Test
//    public void testGetDonationByDateOfPayment() {
//        createUser(USER_EMAIL_1);
//        DonationDto donation = new DonationDto(USER_EMAIL_1, DONATION_DATE_1, DONATION_AMT_1, null); // when donation is created it gets assigned a random UUID string as an ID
//        HttpEntity<DonationDto> entity = new HttpEntity<DonationDto>(donation, headers);
//        restTemplate.postForObject(formatLink("/"+USER_EMAIL_1+"/donations"), entity, DonationDto.class);
//
//        DonationDto[] donationFound = restTemplate.getForObject(formatLink("/donations/" + DONATION_DATE_1),
//                DonationDto[].class);
//
//        TestUtils.assertDonation(donationFound[0], USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
//    }

//    @Test
//    public void testGetDonationByDateAndDonor() {
//        createUser(USER_EMAIL_1);
//        DonationDto donation = new DonationDto(USER_EMAIL_1, DONATION_DATE_1, DONATION_AMT_1, null); // when donation is created it gets assigned a random UUID string as an ID
//        HttpEntity<DonationDto> entity = new HttpEntity<DonationDto>(donation, headers);
//        restTemplate.postForObject(formatLink("/"+USER_EMAIL_1+"/donations"), entity, DonationDto.class);
//
//
//
//    }

    @Test
    public void testGetDonationByNonExistingUserEmail() {
        ResponseEntity<String> response =restTemplate.getForEntity(
                formatLink("/donations/users/"+NON_EXISTING_USER_EMAIL),
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("A donation with such an email does not exist."));
    }

    @Test
    public void testGetDonationByNullUserEmail() {
        ResponseEntity<String> response =restTemplate.getForEntity(
                formatLink("/donations/users/"+null),
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("A donation with such an email does not exist."));
    }

//    @Test
//    public void testGetDonationByEmptyUserEmail() {
//        ResponseEntity<String> response =restTemplate.getForEntity(
//                formatLink("/"+""+"/donations"),
//                String.class);
//        //assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertTrue(response.getBody().contains("A valid email is needed to make a donation"));
//
//    }

}
