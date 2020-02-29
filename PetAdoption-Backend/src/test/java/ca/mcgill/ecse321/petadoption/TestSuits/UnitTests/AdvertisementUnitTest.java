package ca.mcgill.ecse321.petadoption.TestSuits.UnitTests;

import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
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
import org.springframework.boot.test.context.SpringBootTest;

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
    private static final String USER_NAME_1 = "Test User 1";
    private static final String USER_EMAIL_1 = "user1@mcgill.ca";
    private static final String USER_PASSWORD_1 = "password 1";
    private static final String USER_BIO_1 = "cool";
    private static final String USER_HOME_1 = "pleasant";
    private static final Integer USER_AGE_1 = 34;
    private static final Sex USER_SEX_1 = Sex.M;
    private static final boolean USER_ADMIN_1 = true;

    private static final String USER_NAME_2 = "user 2";
    private static final String USER_EMAIL_2 = "user2@mcgill.ca";
    private static final String USER_PASSWORD_2 = "password 2";
    private static final String USER_BIO_2 = "cooler";
    private static final String USER_HOME_2 = "awful";
    private static final Integer USER_AGE_2 = 23;
    private static final Sex USER_SEX_2 = Sex.F;
    private static final boolean USER_ADMIN_2 = false;

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



    //TODO: Set up lenient blocks for all methods that are called from the CRUD repository for advertisement
    //Methods include: (AU)findAppUserByEmail,(Ad)findAdvertisementByPostedBy,(Ad)findAdvertisementByAdvertisementID,
    //(Ad) deleteAdvertisementByAdvertisementID, (Ad)findAll,(Ad)save,(Ad)delete
    //IF YOU ARE NOT MOCKING IT THEN YOU ARE CALLING THE ACTUAL DATABASE AND YOU DO NOT WANT THAT.
    @BeforeEach
    public void setMockOutput() {
        lenient().when(appUserDao.findAppUserByEmail(anyString())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    return null;
                });
    }

    @Test
    public void createAdvertisement() {

    }
}
