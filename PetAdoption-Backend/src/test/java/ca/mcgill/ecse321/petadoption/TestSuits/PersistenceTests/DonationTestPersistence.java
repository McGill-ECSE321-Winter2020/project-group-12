package ca.mcgill.ecse321.petadoption.TestSuits.PersistenceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DonationTestPersistence {

    //AppUser #1
    public static String NAME = "Test User";
    public static String EMAIL = "test.user@testmail.com";
    public static String PASSWORD = "password";
    public static String BIOGRAPHY = "I love dogs";
    public static String HOMEDESCRPTION = "Disneyland";
    public static int AGE = 27;
    public static boolean ADMIN = false;
    public static Sex SEX = Sex.M;

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
    public void clearDatabase() {
        applicationRepository.deleteAll();
        imageRepository.deleteAll();
        // First, we clear advertisement to avoid exceptions due to inconsistencies
        advertisementRepository.deleteAll();
        // Then we can clear the other tables
        donationRepository.deleteAll();
        appUserRepository.deleteAll();
    }


    //Test Cases:
    //READ Case - Retrieve info about a donation - Available info: Donor email, donation amount, date of payment, ID
    //WRITE Case - Make donation - Send info: donation amount, date of payment, ID

    @Test
    public void testPersistAndLoadDonation() {
        //Creating user object for donation verification
        AppUser appUser1 = new AppUser();
        appUser1.setName(NAME);
        appUser1.setEmail(EMAIL);
        appUser1.setPassword(PASSWORD);
        appUser1.setBiography(BIOGRAPHY);
        appUser1.setHomeDescription(HOMEDESCRPTION);
        appUser1.setAge(AGE);
        appUser1.setIsAdmin(ADMIN);
        appUser1.setSex(SEX);
        //Save user
        appUserRepository.save(appUser1);

        //Test donation details - Donation Write case
        Donation donation1 = new Donation();
        Date date = java.sql.Date.valueOf(LocalDate.of(2020,7,2));
        int testAmount = 1000;
        donation1.setTransactionID();
        String donation1_ID = donation1.getTransactionID();  // Storing donation ID for READ case verification below
        donation1.setAmount(testAmount);
        donation1.setDateOfPayment(date);
        donation1.setDonor(appUser1);
        //Save user donation
        donationRepository.save(donation1);

        //Testing Read case
        donation1 = null;

        //Getting donation by unique ID field value
        donation1 = donationRepository.findDonationByTransactionID(donation1_ID);
        assertNotNull(donation1);
        //Verify attributes
        assertEquals(date, donation1.getDateOfPayment());
        assertEquals(testAmount, donation1.getAmount());
        assertEquals(appUser1.getEmail(), donation1.getDonor().getEmail());
        assertEquals(donation1_ID, donation1.getTransactionID());

    }
}
