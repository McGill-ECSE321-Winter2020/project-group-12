package ca.mcgill.ecse321.petadoption.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import ca.mcgill.ecse321.petadoption.dao.*;
import org.junit.jupiter.api.AfterEach;
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
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private ImageRepository imageRepository;

    @AfterEach
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
        advertisementRepository.save(newad);
        Long id = newad.getAdvertisementId();
        newad = null;
        newad = advertisementRepository.findAdvertisementByAdvertisementId(id);
        assertNotNull(newad);
        assertEquals(id , newad.getAdvertisementId());
//        // First example for object save/load
//        Person person = new Person();
//        // First example for attribute save/load
//        person.setName(name);
//        personRepository.save(person);
//
//        person = null;
//
//        person = personRepository.findPersonByName(name);
//        assertNotNull(person);
//        assertEquals(name, person.getName());
    }


}
