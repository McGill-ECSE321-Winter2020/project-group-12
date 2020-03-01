package ca.mcgill.ecse321.petadoption.integration_controller;


import ca.mcgill.ecse321.petadoption.PetAdoptionApplication;
import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;
import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Sex;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @Autowired
    private AppUserRepository appUserRepository;

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

    @Before
    public void cleanDataBase() {
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


}
