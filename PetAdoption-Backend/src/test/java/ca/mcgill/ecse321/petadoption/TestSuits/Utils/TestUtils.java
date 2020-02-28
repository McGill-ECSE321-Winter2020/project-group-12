package ca.mcgill.ecse321.petadoption.TestSuits.Utils;

import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Sex;

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
        return new_user;
    }
}
