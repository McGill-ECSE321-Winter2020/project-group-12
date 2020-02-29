package ca.mcgill.ecse321.petadoption.service_integration;

import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;

import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.model.*;
import ca.mcgill.ecse321.petadoption.service.AppUserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AppUserTest {
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
    private AppUserService service;
    @Autowired
    AppUserRepository appUserRepository;

    @BeforeEach
    public void cleanDB(){
        appUserRepository.deleteAll();
    }

    @Test
    public void testCreateAppUser(){
        AppUser user = null;

        try{
            user = service.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                    USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            fail();
        }
        TestUtils.assertAppUser(user,USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1  );
    }

    @Test
    public void testCreateDuplicateAppUser(){
        AppUser user = null;
        try{
            user = service.createAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                    USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2 );
        }catch (IllegalArgumentException e){
            fail();
        }
        user = null;
        String error = "";
        try{
            user = service.createAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                    USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertEquals(error,"The email " + USER_EMAIL_2+ " is already used.");
    }


}
