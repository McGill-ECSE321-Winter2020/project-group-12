package ca.mcgill.ecse321.petadoption.TestSuits.Utils;

import ca.mcgill.ecse321.petadoption.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestUtils {

    public static AppUser createAppUser(String name, String email, String password,
                                String biography, String homeDescription, Integer age, boolean isAdmin, Sex sex) {
        AppUser new_user = new AppUser();
        new_user.setSex(sex);
        new_user.setPassword(password);
        new_user.setIsAdmin(isAdmin);
        new_user.setHomeDescription(homeDescription);
        new_user.setBiography(biography);
        new_user.setAge(age);
        new_user.setEmail(email);
        new_user.setName(name);

        Advertisement ad = new Advertisement();
        ad.setAdvertisementId();;
        new_user.addAdvertisement(ad);

        Application app = new Application();
        app.setApplicationId();
        new_user.addApplication(app);

        Donation don = new Donation();
        don.setTransactionID();
        new_user.addDonation(don);
        return new_user;
    }

    public static void assertAppUser(AppUser user,String name, String email, String password,
                                     String biography, String homeDescription, Integer age, boolean isAdmin, Sex sex ){
        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(biography, user.getBiography());
        assertEquals(homeDescription, user.getHomeDescription());
        assertEquals(age, user.getAge());
        assertEquals(sex, user.getSex());
        assertEquals(isAdmin, user.isIsAdmin());
    }
}
