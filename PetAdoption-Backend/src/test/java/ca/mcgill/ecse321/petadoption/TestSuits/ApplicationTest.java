package ca.mcgill.ecse321.petadoption.TestSuits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.service.PetAdoptionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.Application;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Donation;
import ca.mcgill.ecse321.petadoption.model.Image;
import ca.mcgill.ecse321.petadoption.model.Sex;
import ca.mcgill.ecse321.petadoption.model.Species;
import ca.mcgill.ecse321.petadoption.model.Status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ApplicationTest {
    @Autowired
    private PetAdoptionService service;
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

    @Test
    public void ApplicationTest() {

        Date datePosted = Date.valueOf(LocalDate.of(2020, Month.FEBRUARY, 7));

        AppUser newUser1 = new AppUser();
        newUser1.setEmail("dr.nafario@evil.com");
        newUser1.setName("dr.Nafario");
        newUser1.setAge(99);
        newUser1.setBiography("Male");
        newUser1.setHomeDescription("homeless");
        newUser1.setIsAdmin(true);
        newUser1.setPassword("abcdefg");
        newUser1.setSex(Sex.M);
        appUserRepository.save(newUser1);

        Advertisement newAd = new Advertisement();
        newAd.setAdvertisementId("cutedog");
        newAd.setDatePosted(datePosted);
        newAd.setPetDescription("lovely,cute,adorable");
        newAd.setPetAge(2);
        newAd.setPetName("Mojo");
        newAd.setIsExpired(false);
        newAd.setPostedBy(newUser1);
        advertisementRepository.save(newAd);

        AppUser newUser2 = new AppUser();
        newUser2.setEmail("dr.lizard@mcgill.ca");
        newUser2.setName("dr.Lizard");
        newUser2.setAge(100);
        newUser2.setBiography("Male");
        newUser2.setHomeDescription("homeless");
        newUser2.setIsAdmin(true);
        newUser2.setPassword("hijklmn");
        newUser2.setSex(Sex.M);
        appUserRepository.save(newUser2);

        Application newapp1 = new Application();
        newapp1.setApplicationId("petadoption1");
        newapp1.setDateOfSubmission(datePosted);
        newapp1.setNote("careful,friendly,nice");
        newapp1.setStatus(Status.accepted);
        newapp1.setApplicant(newUser2);
        newapp1.setAdvertisement(newAd);
        applicationRepository.save(newapp1);

        newapp1 = null ;

        newapp1 = applicationRepository.findApplicationByApplicationId("petadoption1");
        assertEquals(datePosted, newapp1.getDateOfSubmission());
        assertEquals("careful,friendly,nice", newapp1.getNote());
        assertEquals(Status.accepted, newapp1.getStatus());
        assertEquals(newUser2.getEmail(), newapp1.getApplicant().getEmail());
        assertEquals(newAd.getAdvertisementId(), newapp1.getAdvertisement().getAdvertisementId());

    }


}
