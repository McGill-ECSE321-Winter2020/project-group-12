package ca.mcgill.ecse321.petadoption.TestSuits.UnitTests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.time.LocalDate;

import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;

import ca.mcgill.ecse321.petadoption.service.ImageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.ImageRepository;
import ca.mcgill.ecse321.petadoption.model.*;

import static org.junit.jupiter.api.Assertions.assertNull;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ImageUnitTest {

    private static final String USER_NAME_1 = "user 1";
    private static final String USER_EMAIL_1 = "user1@mcgill.ca";
    private static final String USER_PASSWORD_1 = "password 1";
    private static final String USER_BIO_1 = "empty";
    private static final String USER_HOME_1 = "its nice";
    private static final Integer USER_AGE_1 = 34;
    private static final Sex USER_SEX_1 = Sex.M;
    private static final boolean USER_ADMIN_1 = true;

    private static final String IMAGE_NAME_1 = "doggy 1";
    private static final String IMAGE_LINK_1 = "doggy_1.com";
    private static final String IMAGE_ID_1 = "id1";

    private static final String IMAGE_NAME_2 = "doggy 2";
    private static final String IMAGE_LINK_2 = "doggy_2.com";
    private static final String IMAGE_ID_2 = "id2";

    private static final Date ADVERTISEMENT_POSTDATE_1 = Date.valueOf(LocalDate.of(2020, Month.FEBRUARY, 7));
    private static final String ADVERTISEMENT_ID_1 = "Ad_id1";
    private static final boolean ADVERTISEMENT_ISEXPIRED_1 = false;
    private static final String PET_NAME = "";
    private static final int  PET_AGE = 3;
    private static final String PET_DESCRIPTION = "cute lovely";
    private static final Sex PET_SEX = Sex.M;
    private static final Species PET_SPECIE = Species.bird;

    private static final AppUser user = TestUtils.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1, USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
    private static Advertisement ad = TestUtils.createAdvertisement(user, ADVERTISEMENT_POSTDATE_1, ADVERTISEMENT_ID_1, ADVERTISEMENT_ISEXPIRED_1, PET_NAME, PET_AGE, PET_DESCRIPTION, PET_SEX, PET_SPECIE );
    private static Image image1 = TestUtils.createImage(ad, IMAGE_NAME_1, IMAGE_LINK_1, IMAGE_ID_1);
    private static Image image2 = TestUtils.createImage(ad, IMAGE_NAME_2, IMAGE_LINK_2, IMAGE_ID_2);


    @InjectMocks
    private ImageService imageservice;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private AdvertisementRepository advertisementRepository;

    /**
     * create the needed mock objects before each test
     */
    @BeforeEach
    public void mockSetUp() {
        lenient().when(imageRepository.save(any(Image.class))).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (((Image) invocation.getArgument(0)).getName().equals(IMAGE_NAME_1)) {
                        return image1;
                    } else if (((Image) invocation.getArgument(0)).getName().equals(IMAGE_NAME_2)) {
                        return image2;
                    } else {
                        return null;
                    }
                }
        );



        lenient().when(imageRepository.findImageByImageId(anyString())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(IMAGE_ID_1)) {
                        return image1;
                    } else {
                        return null;
                    }
                }
        );
        lenient().when(imageRepository.findAll()).thenAnswer(
                (InvocationOnMock invocation) -> {
                    ArrayList<Image> list = new ArrayList<Image>();
                    list.add(image1);
                    list.add(image2);
                    return list;
                }
        );
        lenient().when(imageRepository.findImagesByAdvertisement_AdvertisementId(anyString())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(ADVERTISEMENT_ID_1)) {
                        ArrayList<Image> list = new ArrayList<Image>();
                        list.add(image1);
                        list.add(image2);
                        return list;
                    } else {
                        return null;
                    }
                }
        );
        lenient().when(advertisementRepository.findAdvertisementByAdvertisementId(anyString())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(ADVERTISEMENT_ID_1)) {
                        return ad;
                    } else {
                        return null;
                    }
                }
        );
    }

    @Test
    public void testCreateImage(){
        Image image = null;

        try{
            image = imageservice.createImage(ADVERTISEMENT_ID_1, IMAGE_NAME_1, IMAGE_LINK_1);
        }catch (IllegalArgumentException e){
            fail();
        }
        assertEquals(ADVERTISEMENT_ID_1, image.getAdvertisement().getAdvertisementId());
        assertEquals(IMAGE_NAME_1, image.getName());
        assertEquals(IMAGE_ID_1, image.getImageId());
        assertEquals(IMAGE_LINK_1, image.getLink());
    }


    @Test
    public void testCreateImageEmptyName(){
        Image image = null;
        String error ="";
        try{
            image = imageservice.createImage(ADVERTISEMENT_ID_1, null, IMAGE_LINK_1);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(image);
        assertEquals("name can not be empty ", error);
    }

    @Test
    public void testCreateImageEmptyLink(){
        Image image = null;
        String error ="";
        try{
            image = imageservice.createImage(ADVERTISEMENT_ID_1, IMAGE_NAME_1, null);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(image);
        assertEquals("link can not be empty ", error);
    }

    @Test
    public void testCreateImageEmptyAdvertisementID(){
        Image image = null;
        String error ="";
        try{
            image = imageservice.createImage(null, IMAGE_NAME_1, IMAGE_LINK_1);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(image);
        assertEquals("A Image must have an Advertisement", error);
    }

    @Test
    public void testGetImageByID(){
        Image image = null;
        try{
            image = imageservice.getImageByID(IMAGE_ID_1);
        }catch (IllegalArgumentException e){
            fail();
        }
        assertEquals(ADVERTISEMENT_ID_1, image.getAdvertisement().getAdvertisementId());
        assertEquals(IMAGE_NAME_1, image.getName());
        assertEquals(IMAGE_ID_1, image.getImageId());
        assertEquals(IMAGE_LINK_1, image.getLink());
    }

    @Test
    public void testGetImageNonExistent(){
        Image image = null;
        String error = "";
        try{
            image = imageservice.getImageByID(IMAGE_ID_2);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(image);
        assertEquals("There is no such Image!",error);
    }

    @Test
    public void testGetImageNullID(){
        Image image = null;
        String error = "";
        try{
            image = imageservice.getImageByID(null);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(image);
        assertEquals("Image must have an ID",error);
    }
    @Test
    public void testGetImageByEmptyID(){
        Image image = null;
        String error = "";
        try{
            image = imageservice.getImageByID("");
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(image);
        assertEquals("Image must have an ID",error);
    }

    @Test
    public void testGetImagesByAdvertisementID(){
        List<Image> list = null;
        ad.addPetImage(image1);
        ad.addPetImage(image2);
        try{
            list = imageservice.getAllImagesOfAdvertisement(ad);
        }catch (IllegalArgumentException e){
            fail();
        }

        assertEquals(list.get(0).getAdvertisement().getAdvertisementId(), ADVERTISEMENT_ID_1);
        assertEquals(list.get(0).getImageId(), IMAGE_ID_1);
        assertEquals(list.get(0).getLink(), IMAGE_LINK_1);
        assertEquals(list.get(0).getName(), IMAGE_NAME_1);

        assertEquals(list.get(1).getAdvertisement().getAdvertisementId(), ADVERTISEMENT_ID_1);
        assertEquals(list.get(1).getImageId(), IMAGE_ID_2);
        assertEquals(list.get(1).getLink(), IMAGE_LINK_2);
        assertEquals(list.get(1).getName(), IMAGE_NAME_2);

    }

}