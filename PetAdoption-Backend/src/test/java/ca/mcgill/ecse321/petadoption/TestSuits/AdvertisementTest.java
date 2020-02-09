package ca.mcgill.ecse321.petadoption.TestSuits;
import ca.mcgill.ecse321.petadoption.dao.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

import ca.mcgill.ecse321.petadoption.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AdvertisementTest {

    @Autowired
    private AdvertisementRepository adRepository;

    @Autowired
    private ApplicationRepository appRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ImageRepository imgRepository;
    @Autowired
    private DonationRepository donationRepository;

    @BeforeEach
    public void clearDatabase() {
        appRepository.deleteAll();
        imgRepository.deleteAll();
        adRepository.deleteAll();
        donationRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @AfterEach
    public void afterClearDatabase() {
        appRepository.deleteAll();
        imgRepository.deleteAll();
        adRepository.deleteAll();
        donationRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadAdvertisement() {
        // Creating owner instance of AppUser
        String ownerEmail = "testowner@test.com";
        String ownerName = "TestOwner";
        String ownerPassword = "ownerpassword";
        String ownerBiography = "I am 22 years old. I like to swim and bike in my spare time";
        String ownerHomeDescription = "Apartment, 3rd floor, 3 bedroom";
        int ownerAge = 22;
        boolean isAdmin = false;
        Sex ownerSex = Sex.M;
        AppUser petOwner = new AppUser();
        petOwner.setEmail(ownerEmail);
        petOwner.setName(ownerName);
        petOwner.setPassword(ownerPassword);
        petOwner.setBiography(ownerBiography);
        petOwner.setHomeDescription(ownerHomeDescription);
        petOwner.setAge(ownerAge);
        petOwner.setIsAdmin(isAdmin);
        petOwner.setSex(ownerSex);
        appUserRepository.save(petOwner);

        //Creating applicant instance of AppUser
        String applicantEmail = "testapplicant@test.com";
        String applicantName = "TestApplicant";
        String applicantPassword = "applicantpassword";
        String applicantBiography = "I have had 2 cats and 1 dog before.";
        String applicantHomeDescription = "2-bedroom apartment near the park and lake";
        int applicantAge = 25;
        Sex applicantSex = Sex.F;
        AppUser applicant = new AppUser();
        applicant.setEmail(applicantEmail);
        applicant.setName(applicantName);
        applicant.setPassword(applicantPassword);
        applicant.setBiography(applicantBiography);
        applicant.setHomeDescription(applicantHomeDescription);
        applicant.setAge(applicantAge);
        applicant.setIsAdmin(isAdmin);  //Both pet shelter owner and applicant Admin fields set to False.
        applicant.setSex(applicantSex);
        appUserRepository.save(applicant);

        //Creating Advertisement instance
        Date datePosted = Date.valueOf(LocalDate.of(2020, Month.FEBRUARY, 7));
        boolean isExpired = false;
        String petName = "Abbie";
        int petAge = 2;
        String petDescription = "Fun and playful golden retriever";
        Sex petSex = Sex.F;
        Species petSpecies = Species.dog;
        Advertisement ad = new Advertisement();
        ad.setDatePosted(datePosted);
        ad.setIsExpired(isExpired);
        ad.setPetName(petName);
        ad.setPetAge(petAge);
        ad.setPetDescription(petDescription);
        ad.setPetSex(petSex);
        ad.setPetSpecies(petSpecies);
        ad.setAdvertisementId();
        String adID = ad.getAdvertisementId();
        ad.setPostedBy(petOwner);
        petOwner.addAdvertisement(ad);
        adRepository.save(ad);

        //Creating image instance
        String imageName = "abbieDog";
        String link = "http://test-link.just/for/testing";
        Image image = new Image();
        image.setImageId();
        String imageID = image.getImageId();
        image.setName(imageName);
        image.setLink(link);
        image.setAdvertisement(ad);
        ad.addPetImage(image);
        imgRepository.save(image);

        // Creating application instance
        Date dateOfSubmission = Date.valueOf(LocalDate.of(2020, Month.FEBRUARY, 10));
        String note = "I really love golden retrievers and have interacted with them a lot!";
        Status status = Status.pending;
        Application app = new Application();
        app.setApplicant(applicant);
        app.setAdvertisement(ad);
        app.setDateOfSubmission(dateOfSubmission);
        app.setNote(note);
        app.setStatus(status);
        app.setApplicationId();
        String applicationID = app.getApplicationId();

        //Setting Links
        //2 way association between applicant and the application
        applicant.addApplication(app);
        app.setApplicant(applicant);
        //2 way association between application and the advertisement
        app.setAdvertisement(ad);
        ad.addApplication(app);
        appRepository.save(app);

        //Unit Testing Advertisement with associations
        ad = adRepository.findAdvertisementByAdvertisementId(adID);
        assertNotNull(ad);
        assertEquals(adID, ad.getAdvertisementId());
        assertEquals(petDescription, ad.getPetDescription());
        assertEquals(ownerEmail, ad.getPostedBy().getEmail());
        assertEquals(applicationID, ((Application) ad.getApplications().toArray()[0]).getApplicationId());
        assertEquals(imageID, ((Image) ad.getPetImages().toArray()[0]).getImageId());
    }

}                                                                                  