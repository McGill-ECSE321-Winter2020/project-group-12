package ca.mcgill.ecse321.petadoption.TestSuits.UnitTests;

import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;
import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.dao.ApplicationRepository;
import ca.mcgill.ecse321.petadoption.dao.DonationRepository;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Sex;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class AppUserUnitTest {
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

    @InjectMocks
    private AppUserService service;

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    ApplicationRepository applicationRepository;
    @Mock
    AdvertisementRepository advertisementRepository;
    @Mock
    DonationRepository donationRepository;

    @BeforeEach
    public void mockSetUp(){
        lenient().when(appUserRepository.save(any(AppUser.class))).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if(((AppUser) invocation.getArgument(0)).getName().equals(USER_NAME_1)){
                        return TestUtils.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
                    }else if (((AppUser) invocation.getArgument(0)).getName().equals(USER_NAME_2)){
                        return TestUtils.createAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                                USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2 );
                    } else {
                        return null;
                    }
                }
        );
        lenient().when(appUserRepository.findAppUserByEmail(anyString())).thenAnswer(
            (InvocationOnMock invocation) -> {
                if (invocation.getArgument(0).equals(USER_EMAIL_2)) {
                    return TestUtils.createAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                            USER_HOME_2, USER_AGE_2, USER_ADMIN_2, USER_SEX_2);
                } else {
                    return null;
                }
            }
        );
        lenient().when(appUserRepository.findAll()).thenAnswer(
                (InvocationOnMock invocation) -> {
                    ArrayList<AppUser> lst = new ArrayList<AppUser>();
                    lst.add(TestUtils.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                            USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 ));
                    lst.add(TestUtils.createAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                            USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2 ));
                    return lst;
                }
        );
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
        String error = "";
        try{
            user = service.createAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                    USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertEquals("The email " + USER_EMAIL_2+ " is already used.", error);
    }

    @Test
    public void testCreateAppUserNullName(){
        AppUser user = null;
        String error ="";
        try{
            user = service.createAppUser(null, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                    USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("name cannot be empty! " , error);
    }

    @Test
    public void testCreateAppUserEmptyName(){
        AppUser user = null;
        String error ="";
        try{
            user = service.createAppUser("", USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                    USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("name cannot be empty! ", error);
    }
    @Test
    public void testCreateAppUserNullEmail(){
        AppUser user = null;
        String error ="";
        try{
            user = service.createAppUser(USER_NAME_1, null, USER_PASSWORD_1, USER_BIO_1,
                    USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("email cannot be empty ", error );
    }
    @Test
    public void testCreateAppUserEmptyEmail(){
        AppUser user = null;
        String error ="";
        try{
            user = service.createAppUser(USER_NAME_1, "", USER_PASSWORD_1, USER_BIO_1,
                    USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("email cannot be empty ", error);
    }
    @Test
    public void testCreateAppUserInvalidEmail(){
        AppUser user = null;
        String error ="";
        String invalidEmail = "inavlid";
        try{
            user = service.createAppUser(USER_NAME_1, invalidEmail, USER_PASSWORD_1, USER_BIO_1,
                    USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("the email " + invalidEmail + " doesn't have a valid format.", error);
    }

    @Test
    public void testCreateAppUserNullPassword(){
        AppUser user = null;
        String error ="";
        try{
            user = service.createAppUser(USER_NAME_1, USER_EMAIL_1, null, USER_BIO_1,
                    USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("password cannot be empty ", error);
    }
    @Test
    public void testCreateAppUserEmptyPassword(){
        AppUser user = null;
        String error ="";
        try{
            user = service.createAppUser(USER_NAME_1, USER_EMAIL_1, "", USER_BIO_1,
                    USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("password cannot be empty ",error);
    }

    @Test
    public void testCreateAppUserNullBiography(){
        AppUser user = null;
        String error ="";
        try{
            user = service.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, null,
                    USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals(error, "biography cannot be empty ");
    }

    @Test
    public void testCreateAppUserEmptyBiography(){
        AppUser user = null;
        String error ="";
        try{
            user = service.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, "",
                    USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("biography cannot be empty ", error );
    }

    @Test
    public void testCreateAppUserNullHomeDescription(){
        AppUser user = null;
        String error ="";
        try{
            user = service.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                    null,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("homeDescription cannot be empty ", error);
    }

    @Test
    public void testCreateAppUserEmptyHomeDescription(){
        AppUser user = null;
        String error ="";
        try{
            user = service.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                    "",USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("homeDescription cannot be empty ", error);
    }

    // TODO: test negative age and UNREASONABLE age (>100)
    @Test
    public void testCreateAppUserZeroAge(){
        AppUser user = null;
        String error ="";
        try{
            user = service.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                    USER_HOME_1,0,USER_ADMIN_1, USER_SEX_1 );
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("age is not valid ", error);
    }

    @Test
    public void testGetAppUser(){
        AppUser user = null;
        try{
            user = service.getAppUserByEmail(USER_EMAIL_2);
        }catch (IllegalArgumentException e){
            fail();
        }
        TestUtils.assertAppUser(user,USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2  );
    }

    @Test
    public void testGetAppUserNonExistent(){
        AppUser user = null;
        String error = "";
        try{
            user = service.getAppUserByEmail( USER_EMAIL_1);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("The user with email "+ USER_EMAIL_1 +" does not exist." ,error);
    }

    @Test
    public void testGetAppUserNullEmail(){
        AppUser user = null;
        String error = "";
        try{
            user = service.getAppUserByEmail( null);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("The email entered is not valid.", error);
    }
    @Test
    public void testGetAppUserEmptyEmail(){
        AppUser user = null;
        String error = "";
        try{
            user = service.getAppUserByEmail("");
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(user);
        assertEquals("The email entered is not valid.",error);
    }

    @Test
    public void testUpdateAppUser(){
        AppUser user = null;
        try{
            user = service.updateAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                    USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2);
        }catch (IllegalArgumentException e){
            fail();
        }
        TestUtils.assertAppUser(user,USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2  );
    }

    @Test
    public void testUpdateNullAppUser(){
        AppUser user = null;
        String error = "";
        try{
            user = service.updateAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                    USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertEquals("The User with email: " + USER_EMAIL_1 + " does not exist",error);
    }
    @Test
    public void testGetAllUsers(){
        List<AppUser> lst = service.getAllAppUsers();
        TestUtils.assertAppUser(lst.get(0),USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1 );
        TestUtils.assertAppUser(lst.get(1),USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                USER_HOME_2,USER_AGE_2,USER_ADMIN_2, USER_SEX_2);
    }

//    @Test
//    public void testDeleteUsers(){
//        service.deleteAppUser(USER_EMAIL_2);
//    }

}
