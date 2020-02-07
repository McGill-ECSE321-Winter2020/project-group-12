package ca.mcgill.ecse321.petadoption.TestSuits;


import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Sex;
import ca.mcgill.ecse321.petadoption.service.PetAdoptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @Autowired
    private PetAdoptionService service;

    @Autowired
    private AppUserRepository appUserRepository;

    @BeforeEach
    public void cleanDataBase(){
        appUserRepository.deleteAll();
    }

    @Test
    public void userCreateTest() {
        service.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1, USER_HOME_1,
                                            USER_AGE_1, USER_SEX_1, USER_ADMIN_1);


        
        AppUser user = service.getAppUser(USER_EMAIL_1);

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

        AppUser user = service.getAppUser(USER_EMAIL_2);

        assertEquals(USER_NAME_2, user.getName());
        assertEquals(USER_EMAIL_2, user.getEmail());
        assertEquals(USER_PASSWORD_2, user.getPassword());
        assertEquals(USER_BIO_2, user.getBiography());
        assertEquals(USER_HOME_2, user.getHomeDescription());
        assertEquals(USER_AGE_2, user.getAge());
        assertEquals(USER_SEX_2, user.getSex());
        assertEquals(USER_ADMIN_2, user.isIsAdmin());

        user = service.getAppUser(USER_EMAIL_1);

        assertEquals(USER_NAME_1, user.getName());
        assertEquals(USER_EMAIL_1, user.getEmail());
        assertEquals(USER_PASSWORD_1, user.getPassword());
        assertEquals(USER_BIO_1, user.getBiography());
        assertEquals(USER_HOME_1, user.getHomeDescription());
        assertEquals(USER_AGE_1, user.getAge());
        assertEquals(USER_SEX_1, user.getSex());
        assertEquals(USER_ADMIN_1, user.isIsAdmin());
    }




}
