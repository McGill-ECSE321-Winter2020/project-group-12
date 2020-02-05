package ca.mcgill.ecse321.petadoption.util;


import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.AppUser;

import java.sql.Date;
import java.util.Calendar;

public class TestUtils {

    public static String USER_NAME_1 = "USER 1";
    public static String USER_EMAIL_1 = "user1@mcgill.ca";


    public static AppUser createUser(String name, String email)
    {
        AppUser new_user = new AppUser();
        new_user.setName(name);
        new_user.setEmail(email);
        return new_user;
    }


    public static Advertisement createAd()
    {
        Advertisement new_ad = new Advertisement();
        Date some_date = new Date(Calendar.getInstance().getTimeInMillis());
        return new_ad;
    }

}
