package ca.mcgill.ecse321.petadoption.TestSuits;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.time.LocalDate;

import ca.mcgill.ecse321.petadoption.dao.*;

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

        //Image tests//
        @Test
        public void testCreateImage(){
            //create an image and an advertisement for it and save both
            String user_name = "user_name";
            String email = "email";
            String password = "password";
            String biography = "biography";
            String home_description = "home_description";
            Integer user_age = 20;
            AppUser Abdul = new AppUser();
            Abdul.setName(user_name);
            Abdul.setEmail(email);
            Abdul.setPassword(password);
            Abdul.setBiography(biography);
            Abdul.setHomeDescription(home_description);
            Abdul.setAge(user_age);
            Abdul.setIsAdmin(false);
            Abdul.setSex(Sex.M);

            appUserRepository.save(Abdul);

            Date date_posted = Date.valueOf(LocalDate.of(2020, 1, 23)); // date in depreciated form
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
            ad.setPostedBy(Abdul);
            ad.setAdvertisementId();
            String ad_id = ad.getAdvertisementId();
            ad = advertisementRepository.save(ad);

            Image new_image = new Image();
            String image_name = "image_name";
            String link = "link";
            new_image.setName(image_name);
            new_image.setImageId();
            new_image.setLink(link);
            new_image.setAdvertisement(ad);
            new_image.setImageId();
            String img_id = new_image.getImageId() ;
            imageRepository.save(new_image);



            //set new_image to null and try retrieving it from database to test persistence through attribute comparison
            new_image = null;

            Image retrieved = imageRepository.findImageByImageId(img_id);


            assertNotNull(retrieved);
            assertEquals(link, retrieved.getLink());
            assertEquals(image_name, retrieved.getName());
            assertEquals(ad.getPetName(), retrieved.getAdvertisement().getPetName());
            assertEquals(ad.getPetSex(), retrieved.getAdvertisement().getPetSex());
            assertEquals(ad.getPetAge(), retrieved.getAdvertisement().getPetAge());
            assertEquals(ad.getPostedBy().getEmail(), retrieved.getAdvertisement().getPostedBy().getEmail());
            assertEquals(ad.getPostedBy().getBiography(), retrieved.getAdvertisement().getPostedBy().getBiography());
            assertEquals(ad.getAdvertisementId(), retrieved.getAdvertisement().getAdvertisementId());
            assertEquals(img_id, retrieved.getImageId());
        }



}
