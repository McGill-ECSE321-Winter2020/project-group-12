package ca.mcgill.ecse321.petadoption.integration_controller;

import ca.mcgill.ecse321.petadoption.PetAdoptionApplication;
import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;
import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.dao.DonationRepository;
import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

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

    private static final String USER_NAME_1 = "user 1";
    private static final String USER_EMAIL_1 = "user1@mcgill.ca";
    private static final String USER_PASSWORD_1 = "password 1";
    private static final String USER_BIO_1 = "empty";
    private static final String USER_HOME_1 = "its nice";
    private static final Integer USER_AGE_1 = 34;
    private static final Sex USER_SEX_1 = Sex.M;
    private static final boolean USER_ADMIN_1 = true;

    private static final Integer DONATION_AMT_1 = 50;
    private static final Date DONATION_DATE_1 = Date.valueOf("2020-01-14");
    private static final Integer DONATION_AMT_2 = 30;
    private static final Date DONATION_DATE_2 = Date.valueOf("2020-02-14");

    @BeforeEach
    public void cleanDataBase() {
        appUserRepository.deleteAll();
        donationRepository.deleteAll();
    }

    private String formatLink(String endpoint){
        return "http://localhost:" + port + endpoint;
    }

    @BeforeEach
    public void createUser() {
        AppUserDto user = new AppUserDto(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );

        HttpEntity<AppUserDto> entity = new HttpEntity<AppUserDto>(user, headers);

        AppUserDto returned_user = restTemplate.postForObject(formatLink("/register"),
                entity, AppUserDto.class);

        TestUtils.assertAppUser(returned_user,USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1);
    }
//    @Test
//    public void testCreateDonation(){
//        DonationDto donation = new DonationDto(USER_EMAIL_1, DONATION_DATE_1, DONATION_AMT_1, null); // when donation is created it gets assigned a random UUID string as an ID
//        HttpEntity<DonationDto> entity = new HttpEntity<DonationDto>(donation, headers);
//        DonationDto returned_donation = restTemplate.postForObject(formatLink("/donate"))
//        TestUtils.assertDonation(returned_donation, USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
//    }

}
