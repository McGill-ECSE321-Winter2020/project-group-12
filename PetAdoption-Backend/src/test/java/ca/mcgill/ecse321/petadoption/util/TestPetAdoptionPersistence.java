package ca.mcgill.ecse321.petadoption.util;

import static java.lang.Boolean.FALSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

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

    @Test
    public void testPersistAndLoadAdvertisement() {
        Advertisement newad = new Advertisement();
        newad.setDatePosted(new Date(12345));
        newad.setPetDescription("lovely,cute,adorable");
        newad.setPetAge(88);
        newad.setPetName("Mojo");
        newad.setIsExpired(false);

        advertisementRepository.save(newad);
        Long id = newad.getAdvertisementId();
        newad = null;
        newad = advertisementRepository.findAdvertisementByAdvertisementId(id);
        assertNotNull(newad);
        assertEquals(new Date(12345),newad.getDatePosted());
        assertEquals("lovely,cute,adorable",newad.getPetDescription());
        assertEquals(88,newad.getPetAge());
        assertEquals("Mojo",newad.getPetName());
        assertEquals(false,newad.isIsExpired());

    }

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
