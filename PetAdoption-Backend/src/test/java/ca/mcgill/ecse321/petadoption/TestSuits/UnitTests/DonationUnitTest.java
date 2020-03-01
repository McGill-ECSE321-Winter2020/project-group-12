package ca.mcgill.ecse321.petadoption.TestSuits.UnitTests;

import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;
import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.dao.DonationRepository;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Donation;
import ca.mcgill.ecse321.petadoption.model.Sex;
import ca.mcgill.ecse321.petadoption.service.DonationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DonationUnitTest {
    private static final String USER_NAME_1 = "user 1";
    private static final String USER_EMAIL_1 = "user1@mcgill.ca";
    private static final String USER_PASSWORD_1 = "password 1";
    private static final String USER_BIO_1 = "empty";
    private static final String USER_HOME_1 = "its nice";
    private static final Integer USER_AGE_1 = 34;
    private static final Sex USER_SEX_1 = Sex.M;
    private static final boolean USER_ADMIN_1 = false;

    private static final AppUser DONOR = TestUtils.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
            USER_HOME_1,USER_AGE_1,USER_ADMIN_1, USER_SEX_1);
    private static final Integer DONATION_AMT_1 = 50;
    private static final Date DONATION_DATE_1 = Date.valueOf("2020-01-14");
    private static final Integer DONATION_AMT_2 = 30;
    private static final Date DONATION_DATE_2 = Date.valueOf("2020-02-14");
    private static final Donation DONATION_2 = TestUtils.createDonation(DONOR, DONATION_AMT_2, DONATION_DATE_2);
    private static final Donation DONATION_1 = TestUtils.createDonation(DONOR, DONATION_AMT_1, DONATION_DATE_1);

    private static final String NON_EXISTING_USER_EMAIL = "nonexisting@gmail.com";
    private static final Date NON_EXISTING_DONATION_DATE = Date.valueOf("2025-01-14");
    private static final String NON_EXISTING_ID = "1234";

    @Mock
    private DonationRepository donationDao;

    @Mock
    private AppUserRepository appUserDao;

    @InjectMocks
    private DonationService donationService;

    private static final String TRANSACTION_ID = "12hjkv";

    @BeforeEach
    public void setMockOutput() { //I ALSO CALL FIND APP USER BY EMAIL IN MY SERVICE CLASS SO.....
        // most probably wont need this cuz ID is randomly pseudo auto-generated
        lenient().when(donationDao.findDonationByTransactionID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if((invocation.getArgument(0)).equals(TRANSACTION_ID)) {
                Donation donation = new Donation();
                donation.setTransactionID(TRANSACTION_ID);
                return donation;
            } else {
                return null;
            }
        });

        lenient().when(donationDao.findDonationByDonorEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if((invocation.getArgument(0)).equals(USER_EMAIL_1)) {
                List<Donation> userDonations = new ArrayList<>();
                userDonations.add(DONATION_1);
                userDonations.add(DONATION_2);
                return userDonations;
            } else {
                return null;
            }
        });

        lenient().when(donationDao.findDonationByDateOfPayment(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
            if((invocation.getArgument(0)).equals(DONATION_DATE_1)) {
                List<Donation> donations = new ArrayList<>();
                donations.add(DONATION_1);
                return donations;
            } else {
                return null;
            }
        });

        lenient().when(donationDao.findDonationByDateOfPaymentAndDonorEmail(any(Date.class), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(((invocation.getArgument(0)).equals(DONATION_DATE_1)) && (invocation.getArgument(1)).equals(USER_EMAIL_1)) {
                List<Donation> donations = new ArrayList<>();
                donations.add(DONATION_1);
                return donations;
            } else {
                return null;
            }
        });

        lenient().when(donationDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            ArrayList<Donation> donationList = new ArrayList<>();
            donationList.add(DONATION_1);
            donationList.add(DONATION_2);
            return donationList;
        });

        lenient().when(appUserDao.findAppUserByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if((invocation.getArgument(0)).equals(USER_EMAIL_1)) {
                return DONOR;
            } else {
                return null;
            }
        });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(donationDao.save(any(Donation.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateDonation(){
        Donation donation = null;

        try {
            donation = donationService.createDonation(USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
        } catch (IllegalArgumentException e) {
            fail();
        }
        TestUtils.assertDonation(donation, USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
    }

    @Test
    public void testCreateDonationDonorNonExistingEmail() {
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
    public void testCreateDonationNonExistingDonor() { // TODO: NOT SURE HOW TO IMPLEMENT IT CUZ ABOVE IN THE MOCK SETUP WE SAID THAT IF WE QUERY W/ ANY STRING IT WILL RETURN STH
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
            donation = donationService.createDonation(USER_EMAIL_1, DONATION_AMT_1, null);
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donation);
        assertEquals("Donation made cannot be missing a date of payment", error);
    }

    @Test
    public void testGetAllDonations() {
        List<Donation> donations = donationService.getAllDonations();
        TestUtils.assertDonation(donations.get(0), USER_EMAIL_1, DONATION_AMT_1, DONATION_DATE_1);
        TestUtils.assertDonation(donations.get(1), USER_EMAIL_1, DONATION_AMT_2, DONATION_DATE_2);
    }

    @Test
    public void testGetDonationByID() {
        assertEquals(TRANSACTION_ID, donationService.getDonationByTransactionID(TRANSACTION_ID).getTransactionID());
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
        Donation donation = donationService.getDonationByTransactionID(NON_EXISTING_ID);
        assertNull(donation);
    }
    @Test
    public void testGetDonationByUserEmail() {
        for(Donation don: donationService.getDonationsByUser(USER_EMAIL_1)) {
            assertEquals(USER_EMAIL_1, don.getDonor().getEmail());
        }
    }

    @Test
    public void testGetDonationByNonExistingUserEmail() {
        assertNull(donationService.getDonationsByUser(NON_EXISTING_USER_EMAIL));
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
        assertNull(donationService.getDonationsByDateOfPayment(NON_EXISTING_DONATION_DATE));
    }

    @Test
    public void testGetDonationByDateOfPaymentAndUser() { //null pointer exception for some reason
        //System.out.println(donationService.getDonationsByDateAndDonor(DONATION_DATE_1, USER_EMAIL_1));
 //       assertNull(donationService.getDonationsByDateAndDonor(DONATION_DATE_1, USER_EMAIL_1));
        for(Donation don: donationService.getDonationsByDateAndDonor(DONATION_DATE_1, USER_EMAIL_1)) {
            assertEquals(DONATION_DATE_1, don.getDateOfPayment());
            assertEquals(USER_EMAIL_1, don.getDonor().getEmail());
        }
    }

    @Test
    public void testGetDonationByNonExistingDateAndNonExistingUser() {
        assertNull(donationService.getDonationsByDateAndDonor(NON_EXISTING_DONATION_DATE, NON_EXISTING_USER_EMAIL));
    }

    @Test
    public void testGetDonationByNullUserEmailAndNullDateOfPayment() {
        String error = "";
        List<Donation> donations = null;
        try {
            donations = donationService.getDonationsByDateAndDonor(null, null);
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donations);
        assertEquals("A user email must be provided! Date of payment cannot be empty!", error);
    }

    @Test
    public void testGetDonationByEmptyUserEmail2() {
        String error = "";
        List<Donation> donations = null;
        try {
            donations = donationService.getDonationsByDateAndDonor(DONATION_DATE_1, "");
        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(donations);
        assertEquals("A user email must be provided! ", error);
    }

}
