package ca.mcgill.ecse321.petadoption.TestSuits;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.ImageRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.petadoption.model.*;

    @ExtendWith(SpringExtension.class)
    @SpringBootTest
    public class ImageTest {

        private AdvertisementRepository advertisementRepository;
        @Autowired
        private ImageRepository imageRepository;

        @BeforeEach //before all tests clean the database
        public void cleanDatabase(){
            imageRepository.deleteAll();
            advertisementRepository.deleteAll();
        }
//        @AfterEach //after each tests clean the database
//        public void cleanDatabase(){
//            ImageRepository.deleteAll();
//            AdvertisementRepository.deleteAll();
//        }

        //Image tests//
        @Test
        public void testCreateImage(){
            //create an image and an advertisement for it and save both
            Date date_posted = new Date(2020, 1, 30); // date in depreciated form
            String name = "name";
            Integer age = 3;
            String description = "description";
            Sex sex = Sex.F;
            Species species = Species.cat;
            Advertisement ad = new Advertisement();
            ad.setDatePosted(date_posted); //requires java.sql.date
            ad.setIsExpired(false);
            ad.setPetName(name);
            ad.setPetAge(age);
            ad.setPetDescription(description);
            ad.setPetSex(sex);
            ad.setPetSpecies(species);
            advertisementRepository.save(ad);
            Image new_image = new Image();
            String image_name = "image_name";
            String link = "link";
            new_image.setName(image_name);
            new_image.setLink(link);
            new_image.setAdvertisement(ad);
            imageRepository.save(new_image);
            //Long id = new_image.getImageId();

            //set new_image to null and try retrieving it from database to test persistence
            new_image = null;
            Image retrieved = imageRepository.findImageByName(image_name);
            assertNotNull(retrieved);
            assertEquals(image_name, retrieved.getName());
        }



}
