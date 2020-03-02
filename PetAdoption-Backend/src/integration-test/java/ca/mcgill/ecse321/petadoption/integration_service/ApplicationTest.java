package ca.mcgill.ecse321.petadoption.integration_service;

import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;
import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.dao.ApplicationRepository;
import ca.mcgill.ecse321.petadoption.model.*;
import ca.mcgill.ecse321.petadoption.service.AdvertisementService;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
import ca.mcgill.ecse321.petadoption.service.ApplicationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationTest {
    @Autowired
    private ApplicationService service;
    @Autowired
    private AppUserService userService;
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    AdvertisementRepository advertisementRepository;
    @Autowired
    AppUserRepository appUserRepository;

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
    private static boolean isExpired = false;
    private Set<Application> applications;
    private static final String petName = "cookie";
    private static final Integer petAge = 2;
    private static final String petDescription = "blablabla";
    private Set<Image> petImages;

    private static final String NOTE = "loves cats";
    private static final Date DATE_OF_SUBMISSION = Date.valueOf("2020-02-19");
    private static final Status STATUS = Status.pending;

    private static final String NOTE2 = "loves dogs";
    private static final Date DATE_OF_SUBMISSION2 = Date.valueOf("2020-02-20");
    private static final Status STATUS2 = Status.pending;

    private String error;

    private static AppUser user1;
    private static AppUser user2;
    private static AppUser user3;
    private static Advertisement advertisement;

    @AfterEach
    public  void cleanDB(){
        applicationRepository.deleteAll();
        advertisementRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {
        applicationRepository.deleteAll();
        advertisementRepository.deleteAll();
        appUserRepository.deleteAll();
        user1 = userService.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
                USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
        user2 = userService.createAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                USER_HOME_2, USER_AGE_2, USER_ADMIN_2, USER_SEX_2);
        user3 = userService.createAppUser(USER_NAME_3, USER_EMAIL_3, USER_PASSWORD_3, USER_BIO_3,
                USER_HOME_3, USER_AGE_3, USER_ADMIN_3, USER_SEX_3);
        String email = user1.getEmail();
        advertisement = advertisementService.createAdvertisement(email, datePosted, petName, petAge, petDescription, Sex.F, Species.cat);
    }

    @Test
    public void createApplicationTest() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, NOTE, STATUS);
        } catch (IllegalArgumentException e) {
            fail();
        }
        TestUtils.assertApplication(app, advertisement, user2, DATE_OF_SUBMISSION, NOTE, STATUS);
    }

    @Test
    public void applicationWithoutNote() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, null, STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("note cannot be empty ", error);
    }

    @Test
    public void applicantIsTheAdvertisementPoster() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user1.getEmail(), DATE_OF_SUBMISSION, NOTE, STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("You cannot adopt your own pet!", error);
    }

    @Test
    public void applicationWithoutUser() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), null, DATE_OF_SUBMISSION, NOTE, STATUS);
            app = service.createApplication(advertisement.getAdvertisementId(), "", DATE_OF_SUBMISSION, NOTE, STATUS);
            app = service.createApplication(advertisement.getAdvertisementId(), " ", DATE_OF_SUBMISSION, NOTE, STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("An Application must have an Advertisement and a AppUser ", error);
    }

    @Test
    public void applicationWithEmptyUserEmail() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), "", DATE_OF_SUBMISSION, NOTE, STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("An Application must have an Advertisement and a AppUser ", error);
    }

    @Test
    public void applicationWithSpaceUserEmail() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), " ", DATE_OF_SUBMISSION, NOTE, STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("An Application must have an Advertisement and a AppUser ", error);
    }

    @Test
    public void applicationWithoutAdvertisement() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(null, user2.getEmail(), DATE_OF_SUBMISSION, NOTE, STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("An Application must have an Advertisement and a AppUser ", error);
    }

    @Test
    public void applicationWithExpiredAdvertisement() {
        Application app = null;
        advertisement.setIsExpired(true);
        advertisementService.updateAdvertisementIsExpired(advertisement.getAdvertisementId(), true);
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, NOTE, STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("The Advertisement has expired", error);
        isExpired = false;
    }

    @Test
    public void applicationWithNullDateOfSubmission() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), null, NOTE, STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("dateOfSubmission can not be empty! ", error);
    }

    @Test
    public void applicationWithNullNote() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, null, STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("note cannot be empty ", error);
    }

    @Test
    public void applicationWithEmptyNote() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, "", STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("note cannot be empty ", error);
    }

    @Test
    public void applicationWithSpaceNote() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, " ", STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("note cannot be empty ", error);
    }

    @Test
    public void getApplicationTest() {
        Application app = null;
        Application app2 = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, NOTE, STATUS);
            app2 = service.getApplicationByID(app.getApplicationId());
        } catch (IllegalArgumentException e) {
            fail();
        }
        TestUtils.assertApplication(app2, advertisement, user2, DATE_OF_SUBMISSION, NOTE, STATUS);
    }

    @Test
    public void getApplicationNullIdTest() {
        Application app = null;
        Application app2 = null;
        error = "";
        try {
            app2 = service.getApplicationByID(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Application must have an ID", error);
    }

    @Test
    public void getApplicationEmptyIdTest() {
        Application app = null;
        Application app2 = null;
        error = "";
        try {
            app2 = service.getApplicationByID("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Application must have an ID", error);
    }

    @Test
    public void getApplicationSpaceIdTest() {
        Application app = null;
        Application app2 = null;
        error = "";
        try {
            app2 = service.getApplicationByID("  ");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Application must have an ID", error);
    }

    @Test
    public void getApplicationIncorrectIdTest() {
        Application app = null;
        Application app2 = null;
        error = "";
        try {
            app2 = service.getApplicationByID("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(app2); //if the id passed does not match any other id, the application returned is null
    }

    @Test
    public void testGetAllApplications() {
        Application app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, NOTE, STATUS);
        Application app2 = service.createApplication(advertisement.getAdvertisementId(), user3.getEmail(), DATE_OF_SUBMISSION2, NOTE2, STATUS2);
        List<Application> list = service.getAllApplications();
        TestUtils.assertApplication(list.get(0), advertisement, user2, DATE_OF_SUBMISSION, NOTE, STATUS);
        TestUtils.assertApplication(list.get(1), advertisement, user3, DATE_OF_SUBMISSION2, NOTE2, STATUS2);
    }

    @Test
    public void testDelete() {
        Application app = null;
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, NOTE, STATUS);
            service.deleteApplication(app.getApplicationId());
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testUpdateApplicationStatus() {
        Application app = null;
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, NOTE, STATUS);
            app = service.updateApplicationStatus(app.getApplicationId(), Status.accepted);
        } catch (IllegalArgumentException e) {
            fail();
        }
        TestUtils.assertApplication(app, advertisement, user2, DATE_OF_SUBMISSION, NOTE, Status.accepted);
        try {
            app = service.updateApplicationStatus(app.getApplicationId(), Status.rejected);
        } catch (IllegalArgumentException e) {
            fail();
        }
        TestUtils.assertApplication(app, advertisement, user2, DATE_OF_SUBMISSION, NOTE, Status.rejected);
    }

    @Test
    public void duplicateApplicationsPerAdvertisement() {
        Application app = null;
        Application app2 = null;
        Advertisement ad = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, NOTE, STATUS);
            app2 = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION2, NOTE2, STATUS2);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("You already applied for this", error);
    }

    @Test
    public void ApplicationDateBeforeAdvertisement() {
        Application app = new Application();
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), Date.valueOf("2000-01-01"), NOTE, STATUS);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Advertisement Date Must Be Prior or Equal To Application Date", error);
    }

    @Test
    public void getAllApplicationsTest() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, NOTE+"user2", STATUS);
        } catch (IllegalArgumentException e) {
            fail();
        }
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user3.getEmail(), DATE_OF_SUBMISSION, NOTE +"user3", STATUS);
        } catch (IllegalArgumentException e) {
            fail();
        }
        List<Application> lst = service.getAllApplicationsForAdvertisement(advertisement.getAdvertisementId());
        TestUtils.assertApplication(lst.get(0), advertisement, user2, DATE_OF_SUBMISSION, NOTE + "user2", STATUS);
        TestUtils.assertApplication(lst.get(1), advertisement, user3, DATE_OF_SUBMISSION, NOTE + "user3", STATUS);
    }

}
