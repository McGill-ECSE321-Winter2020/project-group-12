package ca.mcgill.ecse321.petadoption.util;

import static java.lang.Boolean.FALSE;
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
public class TestPetAdoptionPersistence {

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
        // First, we clear advertisement to avoid exceptions due to inconsistencies
        advertisementRepository.deleteAll();
        // Then we can clear the other tables
        applicationRepository.deleteAll();
        appUserRepository.deleteAll();
        donationRepository.deleteAll();
        imageRepository.deleteAll();
    }

//    @Test
//    public void testPersistAndLoadAdvertisement() {
//
//        AppUser newUser = new AppUser();
//        newUser.setEmail("dr.nafario@evil.com");
//        newUser.setName("dr.Nafario");
//        newUser.setAge(99);
//        newUser.setBiography("Male");
//        newUser.setHomeDescription("null");
//        newUser.setIsAdmin(true);
//        newUser.setPassword("abcdefg");
//        newUser.setSex(Sex.M);
//        appUserRepository.save(newUser);
//
//
//        Image newImage = new Image();
//        newImage.setImageId((long) 23);
//        newImage.setLink("http");
//        newImage.setName("cuteanimal");
//
//
//        Application newApp = new Application();
//        Set<Application> newApps = new HashSet<Application>();
//        Set<Image> newImages = new HashSet<Image>();
//        newApps.add(newApp);
//        newImages.add(newImage);
//
//
//        Advertisement newAd = new Advertisement();
//        newAd.setDatePosted(new Date(12345));
//        newAd.setPetDescription("lovely,cute,adorable");
//        newAd.setPetAge(88);
//        newAd.setPetName("Mojo");
//        newAd.setIsExpired(false);
//        newAd.setApplications(newApps);
//        newAd.setPetImages(newImages);
//        newAd.setPostedBy(newUser);
//
//        Set<Advertisement> ads = new HashSet<Advertisement>();
//        ads.add(newAd);
//
//
//        advertisementRepository.save(newAd);
//        Long id = newAd.getAdvertisementId();
//        newAd = null;
//        newAd = advertisementRepository.findAdvertisementByAdvertisementId(id);
//        assertNotNull(newAd);
//        assertEquals(new Date(12345),newAd.getDatePosted());
//        assertEquals("lovely,cute,adorable",newAd.getPetDescription());
//        assertEquals(88,newAd.getPetAge());
//        assertEquals("Mojo",newAd.getPetName());
//        assertEquals(false,newAd.isIsExpired());
//    }

//    @Test
//    public void testPersistAndLoadEvent() {
//        String name = "ECSE321 Tutorial";
//        Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
//        Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
//        Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
//        Event event = new Event();
//        event.setName(name);
//        event.setDate(date);
//        event.setStartTime(startTime);
//        event.setEndTime(endTime);
//        eventRepository.save(event);
//
//        event = null;
//
//        event = eventRepository.findEventByName(name);
//
//        assertNotNull(event);
//        assertEquals(name, event.getName());
//        assertEquals(date, event.getDate());
//        assertEquals(startTime, event.getStartTime());
//        assertEquals(endTime, event.getEndTime());
//    }


}
