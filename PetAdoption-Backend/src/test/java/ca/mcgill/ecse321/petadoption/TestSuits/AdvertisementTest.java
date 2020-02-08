package ca.mcgill.ecse321.petadoption.TestSuits;
import ca.mcgill.ecse321.petadoption.dao.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.petadoption.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
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
        // First, we clear advertisement to avoid exceptions due to inconsistencies
        adRepository.deleteAll();
        // Then we can clear the other tables
        donationRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadAdvertisement() {
        // creating the instance of the poster of the advertisement to test the advertisement-user association
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

        Date datePosted = Date.valueOf(LocalDate.of(2020, Month.FEBRUARY, 7));
        //long adID = 1;
        boolean isExpired = false;
        String petName = "Abbie";
        int petAge = 2;
        String petDescription = "Fun and playful golden retriever";
        Sex petSex = Sex.F;
        Species petSpecies = Species.dog;
        Advertisement ad = new Advertisement();
        ad.setDatePosted(datePosted);
        //ad.setAdvertisementId(adID);
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

        String imageName = "abbieDog";
        String link = "http://test-link.just/for/testing";
        //long imgID = 1;
        Image image = new Image();
        image.setImageId();
        String imgID = image.getImageId();
        image.setName(imageName);
        image.setLink(link);


//        image.setAdvertisement(ad);
//        ad.addPetImage(image);
//        imgRepository.save(image);
//

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
        applicant.setIsAdmin(isAdmin);
        applicant.setSex(applicantSex);
        
      appUserRepository.save(applicant);


        // an application
        Date dateOfSubmission = Date.valueOf(LocalDate.of(2020, Month.FEBRUARY, 10));
        String note = "I really love golden retrievers and have interacted with them a lot!";
        Status status = Status.pending;
        //long appID = 1;
        Application app = new Application();
        app.setApplicant(applicant);
        app.setAdvertisement(ad);
        app.setDateOfSubmission(dateOfSubmission);
        app.setNote(note);
        app.setStatus(status);
        //app.setApplicationId(appID);
        app.setApplicationId();
        String applicationID = app.getApplicationId();
        applicant.addApplication(app);
        app.setApplicant(applicant);
        app.setAdvertisement(ad);
        ad.addApplication(app);
        appRepository.save(app);


//        applicant.addApplication(app);
//        app.setAdvertisement(ad);
//        app.setApplicant(applicant);
//        ad.addPetImage(image);
//
//        ad.addApplication(app);
//        image.setAdvertisement(ad);

//        //appUserRepository.save(petOwner);
////       // adRepository.save(ad);
////        imgRepository.save(image);
////        appUserRepository.save(applicant);
////        appRepository.save(app);
//
//        ad = null;
//        //ad = adRepository.findAdvertisementByAdvertisementId(adID);

//        List<Advertisement> adList = adRepository.findAdvertisementsByPostedBy(petOwner);
//        ad = adList.get(0);
        //System.out.println("HELLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
//        System.out.println("This is the id of the retrieved advertisement!!!!" + ad.getAdvertisementId());
//        assertNotNull(ad);
//        //assertEquals(adID, ad.getAdvertisementId());
//        assertEquals(petOwner.getEmail(), ad.getPostedBy().getEmail());
//        assertEquals(app.getApplicationId(), ((Application) ad.getApplications().toArray()[0]).getApplicationId());
//        assertEquals(image.getImageId(), ((Image) ad.getPetImages().toArray()[0]).getImageId());

        ad = null;
        ad = adRepository.findAdvertisementByAdvertisementId(adID);
        assertNotNull(ad);
        assertEquals(adID, ad.getAdvertisementId());
        assertEquals(petOwner.getEmail(), ad.getPostedBy().getEmail());
        //assertEquals(app.getApplicationId(), ((Application) ad.getApplications().toArray()[0]).getApplicationId());
    }


}                                                                                  