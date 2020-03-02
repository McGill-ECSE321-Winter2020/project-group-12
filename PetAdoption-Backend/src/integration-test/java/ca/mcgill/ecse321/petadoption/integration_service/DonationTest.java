package ca.mcgill.ecse321.petadoption.integration_service;

import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;
import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.Donation;
import ca.mcgill.ecse321.petadoption.model.Sex;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
import ca.mcgill.ecse321.petadoption.service.DonationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class DonationTest {

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

    private static final String NON_EXISTING_USER_EMAIL = "nonexisting@gmail.com";
    private static final Date NON_EXISTING_DONATION_DATE = Date.valueOf("2025-01-14");
    private static final String NON_EXISTING_ID = "1234";

    @Autowired
    private DonationService donationService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private DonationRepository donationRepository;

    @BeforeEach
    public void cleanDB() {
        imageRepository.deleteAll();
        applicationRepository.deleteAll();
        advertisementRepository.deleteAll();
        donationRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    public void testCreateDonation(){
        Donation donation = createDonorThenDonation();
        TestUtils.assertDonation(donation, USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
    }

    @Test
    public void testCreateDonationDonorNullEmail() {
        String error = null;
        Donation donation = null;
        try {
            donation = donationService.createDonation(null, DONATION_AMT_1, DONATION_DATE_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donation);
        assertEquals("A valid email is needed to make a donation", error);
    }

    @Test
    public void testCreateDonationDonorEmailEmpty() {
        String error = null;
        Donation donation = null;
        try {
            donation = donationService.createDonation("", DONATION_AMT_1, DONATION_DATE_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donation);
        assertEquals("A valid email is needed to make a donation", error);
    }

    @Test
    public void testCreateDonationNonExistingDonor() {
        String error = "";
        Donation donation = null;
        try {
            donation = donationService.createDonation(NON_EXISTING_USER_EMAIL, DONATION_AMT_2, DONATION_DATE_2);
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donation);
        assertEquals("Donation cannot be created without a donor!", error);

    }

    @Test
    public void testCreateDonationAmountNull() {
        String error = null;
        Donation donation = null;

        try {
            appUserService.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1, USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            donation = donationService.createDonation(USER_EMAIL_1, null, DONATION_DATE_1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donation);
        assertEquals("Cannot make a donation with unspecified amount", error);
    }

    @Test
    public void testCreateDonationInvalidAmount0() {
        String error = null;
        Donation donation = null;

        try {
            appUserService.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1, USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            donation = donationService.createDonation(USER_EMAIL_1, 0, DONATION_DATE_1);
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donation);
        assertEquals("Cannot make a donation of $0 or less", error);
    }

    @Test
    public void testCreateDonationInvalidAmountNegative() {
        String error = null;
        Donation donation = null;
        try {
            appUserService.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1, USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            donation = donationService.createDonation(USER_EMAIL_1, -1, DONATION_DATE_1);
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donation);
        assertEquals("Cannot make a donation of $0 or less", error);
    }

    @Test
    public void testCreateDonationDateNull() {
        String error = null;
        Donation donation = null;

        try {
            appUserService.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1, USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            donation = donationService.createDonation(USER_EMAIL_1, DONATION_AMT_1, null);
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donation);
        assertEquals("Donation made cannot be missing a date of payment", error);
    }

    @Test
    public void testGetAllDonations() {
        createDonorWithTwoDonations();

        List<Donation> donations = donationService.getAllDonations();
        TestUtils.assertDonation(donations.get(0), USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
        TestUtils.assertDonation(donations.get(1), USER_EMAIL_1, DONATION_AMT_2, DONATION_DATE_2);
    }

    @Test
    public void testGetDonationByID() {
        Donation donation = createDonorThenDonation();
        //Donation donation;

        String id = donation.getTransactionID();
        assertEquals(id, donationService.getDonationByTransactionID(id).getTransactionID());
    }

    private Donation createDonorThenDonation() {
        Donation donation = null;

        try {
            appUserService.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1, USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            donation = donationService.createDonation(USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
        } catch (IllegalArgumentException e) {
            fail();
        }
        return donation;
    }

    @Test
    public void testGetDonationByNullID() {
        String error = "";

        Donation donation = null;
        try {
            donation = donationService.getDonationByTransactionID(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donation);
        assertEquals("Donation must have a valid transactionID!", error);
    }

    @Test
    public void testGetDonationByEmptyID() {
        String error = "";
        Donation donation = null;
        try {
            donation = donationService.getDonationByTransactionID("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donation);
        assertEquals("Donation must have a valid transactionID!", error);
    }

    @Test
    public void testGetDonationByNonExistingID() {
        String error = "";
        Donation donation = null;
        try {
            donation = donationService.getDonationByTransactionID(NON_EXISTING_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("A Donation with such an ID does not exist", error);
        assertNull(donation);
    }

    @Test
    public void testGetDonationByUserEmail() {
        createDonorWithTwoDonations();

        for(Donation don: donationService.getDonationsByUser(USER_EMAIL_1)) {
            assertEquals(USER_EMAIL_1, don.getDonor().getEmail());
        }
    }

    private void createDonorWithTwoDonations() {
        try {
            appUserService.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1, USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            donationService.createDonation(USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            donationService.createDonation(USER_EMAIL_1, DONATION_AMT_2, DONATION_DATE_2);
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testGetDonationByNonExistingUserEmail() {
        String error = "";
        List<Donation> donations = null;
        try {
            donations = donationService.getDonationsByUser(NON_EXISTING_USER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("A donation with such an email does not exist.", error);
        assertNull(donations);
    }

    @Test
    public void testGetDonationByNullUserEmail() {
        String error = "";
        List<Donation> donations = null;
        try {
            donations = donationService.getDonationsByUser(null);
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donations);
        assertEquals("The email entered is not valid.", error);
    }

    @Test
    public void testGetDonationByEmptyUserEmail() {
        String error = "";
        List<Donation> donations = null;
        try {
            donations = donationService.getDonationsByUser("");
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donations);
        assertEquals("The email entered is not valid.", error);
    }

    @Test
    public void testGetDonationByDateOfPayment() {
        try {
            appUserService.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1, USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            donationService.createDonation(USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
        } catch (IllegalArgumentException e) {
            fail();
        }

        for(Donation don: donationService.getDonationsByDateOfPayment(DONATION_DATE_1)) {
            assertEquals(DONATION_DATE_1, don.getDateOfPayment());
        }
    }

    @Test
    public void testGetDonationByEmptyDateOfPayment() {
        String error = "";
        List<Donation> donations = null;
        try {
            donations = donationService.getDonationsByDateOfPayment(null);
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donations);
        assertEquals("Date of payment cannot be empty", error);
    }

    @Test
    public void testGetDonationByNonExistingDateOfPayment() {
        String error = "";
        List<Donation> donations = null;
        try {
            donations = donationService.getDonationsByDateOfPayment(NON_EXISTING_DONATION_DATE);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donations);
        assertEquals("No donations were made on this date", error);
    }

    @Test
    public void testGetDonationByDateOfPaymentAndUser() { //null pointer exception for some reason
        createDonorWithTwoDonations();

        for(Donation don: donationService.getDonationsByDateAndDonor(DONATION_DATE_1, USER_EMAIL_1)) {
            assertEquals(DONATION_DATE_1, don.getDateOfPayment());
            assertEquals(USER_EMAIL_1, don.getDonor().getEmail());
        }
    }

    @Test
    public void testGetDonationByNonExistingDateAndNonExistingUser() {
        String error = "";
        List<Donation> donations = null;
        try {
            donations = donationService.getDonationsByDateAndDonor(NON_EXISTING_DONATION_DATE, NON_EXISTING_USER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donations);
        assertEquals("No donations were found on this date by this donor.", error);
    }

    @Test
    public void testGetDonationByNullUserEmailAndNullDateOfPayment() {
        String error = "";
        List<Donation> donations = new ArrayList<>();
        try {
            donations = donationService.getDonationsByDateAndDonor(null, null);
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(0, donations.size());
        assertEquals("A user email must be provided! Date of payment cannot be empty!", error);
    }

    @Test
    public void testGetDonationByEmptyUserEmail2() {
        String error = "";
        List<Donation> donations = new ArrayList<>();
        try {
            donations = donationService.getDonationsByDateAndDonor(DONATION_DATE_1, "");
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(0, donations.size());
        assertEquals("A user email must be provided! ", error);
    }

}
