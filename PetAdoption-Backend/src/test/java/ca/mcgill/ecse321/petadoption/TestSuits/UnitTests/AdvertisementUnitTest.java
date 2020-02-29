package ca.mcgill.ecse321.petadoption.TestSuits.UnitTests;

import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Sex;
import ca.mcgill.ecse321.petadoption.model.Species;
import ca.mcgill.ecse321.petadoption.service.AdvertisementService;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
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
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AdvertisementUnitTest {

    @Mock
    private AdvertisementRepository advertisementDao;
    @Mock
    private AppUserRepository appUserDao;

    @InjectMocks
    private AdvertisementService advertisementService;
    @InjectMocks
    private AppUserService appUserService;

    // Constants to create test appUser and advertisement objects
    private static final String USER_EMAIL_1 = "user1@mcgill.ca";
    private static final String USER_EMAIL_2 = "user2@mcgill.ca";

    //TODO: Check/ Put constraint that advertisement cannot be updated if it is expired
    private static final boolean IS_EXPIRED_1 = false;
    private static final String PET_NAME_1 = "Rusty";
    private static final Integer PET_AGE_1 = 10;
    private static final String PET_DESCRIPTION_1 = "Not Rusty at all";
    private static final Sex PET_SEX_1 = Sex.M;
    private static final Species PET_SPECIES_1 = Species.dog;

    private static final boolean IS_EXPIRED_2 = false;
    private static final String PET_NAME_2 = "Snow";
    private static final Integer PET_AGE_2 = 15;
    private static final String PET_DESCRIPTION_2 = "Not Snowy at all";
    private static final Sex PET_SEX_2 = Sex.F;
    private static final Species PET_SPECIES_2 = Species.cat;

    private static final boolean IS_EXPIRED_3 = false;
    private static final String PET_NAME_3 = "Chirpy";
    private static final Integer PET_AGE_3 = 20;
    private static final String PET_DESCRIPTION_3 = "Not Chirpy at all";
    private static final Sex PET_SEX_3 = Sex.F;
    private static final Species PET_SPECIES_3 = Species.bird;

    private static final Date DATE_1 = Date.valueOf("2020-07-02");
    private static final Date DATE_2 = Date.valueOf("2020-06-07");

    //Mock unique ID keys for advertisements
    private static final String ADVERTISEMENT_1_ID = "zwVC9mb";
    private static final String ADVERTISEMENT_2_ID = "3klFrbp";
    private static final String ADVERTISEMENT_3_ID = "MEM6jFl";

    private static final AppUser APP_USER_1 = new AppUser();
    private static final AppUser APP_USER_2 = new AppUser();

    //Create Mock Advertisement Objects
    private static final Advertisement ADVERTISEMENT_1 = createAdvertisement(APP_USER_1, DATE_1, ADVERTISEMENT_1_ID,
            IS_EXPIRED_1, PET_NAME_1, PET_AGE_1, PET_DESCRIPTION_1, PET_SEX_1, PET_SPECIES_1);
    private static final Advertisement ADVERTISEMENT_2 = createAdvertisement(APP_USER_2, DATE_1, ADVERTISEMENT_2_ID,
            IS_EXPIRED_2, PET_NAME_2, PET_AGE_2, PET_DESCRIPTION_2, PET_SEX_2, PET_SPECIES_2);
    private static final Advertisement ADVERTISEMENT_3 = createAdvertisement(APP_USER_2, DATE_2, ADVERTISEMENT_3_ID,
            IS_EXPIRED_3, PET_NAME_3, PET_AGE_3, PET_DESCRIPTION_3, PET_SEX_3, PET_SPECIES_3);

    //TODO: Find out how to mock a void from the CRUD repository
    //IF YOU ARE NOT MOCKING IT THEN YOU ARE CALLING THE ACTUAL DATABASE AND YOU DO NOT WANT THAT.
    @BeforeEach
    public void setMockOutput() {
        //Set up Mock data base with App User account and Advertisements
        configureAppUser(APP_USER_1, USER_EMAIL_1, ADVERTISEMENT_1);
        configureAppUser(APP_USER_2, USER_EMAIL_2, ADVERTISEMENT_2, ADVERTISEMENT_3);

        // Mock Actions of data base
        lenient().when(appUserDao.findAppUserByEmail(anyString())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(USER_EMAIL_1)) {
                        return APP_USER_1;
                    } else if (invocation.getArgument(0).equals((USER_EMAIL_2))) {
                        return APP_USER_2;
                    }
                    return null;
                });

        lenient().when(advertisementDao.findAdvertisementsByPostedBy(any(AppUser.class))).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (((AppUser) invocation.getArgument(0)).getEmail().equals(USER_EMAIL_1)) {
                        //Return HashSet data as ArrayList because method in DAO class returns List<Advertisement>
                        return new ArrayList<>(APP_USER_1.getAdvertisements());
                    } else if (((AppUser) invocation.getArgument(0)).getEmail().equals(USER_EMAIL_2)) {
                        return new ArrayList<>(APP_USER_2.getAdvertisements());
                    }
                    return null;
                });

        lenient().when(advertisementDao.findAdvertisementByAdvertisementId(anyString())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(ADVERTISEMENT_1_ID)) {
                        return ADVERTISEMENT_1;
                    } else if (invocation.getArgument(0).equals(ADVERTISEMENT_2_ID)) {
                        return ADVERTISEMENT_2;
                    } else if (invocation.getArgument(0).equals(ADVERTISEMENT_3_ID)) {
                        return ADVERTISEMENT_3;
                    }
                    return null;
                });

        lenient().when(advertisementDao.findAll()).thenAnswer(
                (InvocationOnMock invocation) -> {
                    List<Advertisement> advertisementList = new ArrayList<>();
                    advertisementList.add(ADVERTISEMENT_1);
                    advertisementList.add(ADVERTISEMENT_2);
                    advertisementList.add(ADVERTISEMENT_3);

                    return advertisementList;
                });

        //Returning Parameter object upon save
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };

        lenient().when(advertisementDao.save(any(Advertisement.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void createAdvertisement() {

    }


    private static Advertisement createAdvertisement(AppUser appUser, Date datePosted, String id, boolean isExpired, String petName,
                                                     Integer petAge, String petDescription, Sex petSex, Species petSpecies) {
        Advertisement advertisement = new Advertisement();
        advertisement.setDatePosted(datePosted);
        advertisement.setAdvertisementId(id);
        advertisement.setIsExpired(isExpired);
        advertisement.setPetName(petName);
        advertisement.setPetAge(petAge);
        advertisement.setPetDescription(petDescription);
        advertisement.setPetSex(petSex);
        advertisement.setPetSpecies(petSpecies);

        advertisement.setPostedBy(appUser);
        advertisement.setPetImages(new HashSet<>());
        advertisement.setApplications(new HashSet<>());

        return advertisement;
    }

    private static void configureAppUser(AppUser appUser, String userEmail, Advertisement... advertisement) {
        appUser.setEmail(userEmail);
        for (Advertisement ad : advertisement) {
            appUser.addAdvertisement(ad);
        }
    }
}
