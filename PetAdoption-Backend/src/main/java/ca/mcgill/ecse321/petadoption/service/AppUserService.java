package ca.mcgill.ecse321.petadoption.service;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class AppUserService {
    @Autowired(required = true)
    AppUserRepository appUserRepository;
    @Autowired(required = true)
    ApplicationRepository applicationRepository;
    @Autowired(required = true)
    AdvertisementRepository advertisementRepository;
    @Autowired(required = true)
    DonationRepository donationRepository;
    @Autowired(required = true)
    ImageRepository imageRepository;

    @Autowired
    DonationService donationService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    AdvertisementService advertisementService;

    /**
     * Creates and adds a new AppUser object to the database.
     *
     * @param name(String)
     * @param email(String)
     * @param password(String)
     * @param biography(String)
     * @param homeDescription(String)
     * @param age(Integer)
     * @param isAdmin(boolean)
     * @param sex(Sex)
     * @return AppUser object
     */
    @Transactional
    public AppUser createAppUser(String name, String email, String password,
                                 String biography, String homeDescription, Integer age, boolean isAdmin, Sex sex) {

        userParamCheck(name, email, password, biography, homeDescription, age, sex);

        AppUser old_user = appUserRepository.findAppUserByEmail(email);
        if(old_user != null){
            throw new IllegalArgumentException("The email " + email+ " is already used.");
        }
        AppUser new_user = new AppUser();
        new_user.setSex(sex);
        new_user.setPassword(password);
        new_user.setIsAdmin(isAdmin);
        new_user.setHomeDescription(homeDescription);
        new_user.setBiography(biography);
        new_user.setAge(age);
        new_user.setEmail(email);
        new_user.setName(name);
        return appUserRepository.save(new_user);
    }

    /**
     * Returns the AppUser with specified email from the database.
     *
     * @param email
     * @return AppUser object
     */
    @Transactional
    public AppUser getAppUserByEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("The email entered is not valid.");
        }
        AppUser user = appUserRepository.findAppUserByEmail(email);
        if(user == null) {
            throw new IllegalArgumentException("The user with email "+ email +" does not exist.");
        }
        return user;
    }

    /**
     * Returns all AppUsers in the database.
     *
     * @return List of AppUser objects
     */
    @Transactional
    public List<AppUser> getAllAppUsers() {
        return new ArrayList<AppUser>((Collection<? extends AppUser>) appUserRepository.findAll());
    }

    /**
     * Deletes the AppUser with specified email from the database.
     *
     * @param email
     */
    @Transactional
    public void deleteAppUser(String email) {
        AppUser user1 = appUserRepository.findAppUserByEmail(email);
        for (Advertisement ad : user1.getAdvertisements()){
            advertisementRepository.deleteAdvertisementByAdvertisementId(ad.getAdvertisementId());
        }

        for(Donation donation: user1.getDonations()){
            donationRepository.deleteDonationByTransactionID(donation.getTransactionID());
        }

        for(Application app : user1.getApplications()){
            applicationRepository.deleteApplicationByApplicationId(app.getApplicationId());
        }
        appUserRepository.deleteAppUserByEmail(email);
    }

    /**
     * Updates the AppUser instance and save it to the database.
     * @param name(String)
     * @param email(String)
     * @param password(String)
     * @param biography(String)
     * @param homeDescription(String)
     * @param age(Integer)
     * @param isAdmin(boolean)
     * @param sex(Sex)
     *
     * @return the updated AppUser
     */
     @Transactional
    public AppUser updateAppUser(String name, String email, String password, String biography,
                                  String homeDescription, Integer age, boolean isAdmin, Sex sex){

         userParamCheck(name, email, password, biography, homeDescription, age, sex);
         AppUser user = appUserRepository.findAppUserByEmail(email);
         if(user == null){
             String error = "The User with email: " + email + " does not exist";
             throw new IllegalArgumentException(error);
         }
         user.setSex(sex);
         user.setPassword(password);
         user.setIsAdmin(isAdmin);
         user.setHomeDescription(homeDescription);
         user.setBiography(biography);
         user.setAge(age);
         user.setEmail(email);
         user.setName(name);
         return appUserRepository.save(user);
     }

    /**
     * Checks if the email,password pair matches a user.
     * @param email
     * @param password
     * @return
     */
     public boolean checkLoginParam(String email, String password){
         String error = "";
         if(email == null || email.trim().length() == 0){
             error = "name cannot be empty! ";
         }
         if (password == null || password.trim().length() == 0) {
             error = error + "password cannot be empty ";
         }
         if (error.length() != 0) {
             throw new IllegalArgumentException(error);
         }
         AppUser user = appUserRepository.findAppUserByEmail(email);
         if(user!= null && user.getPassword().equals(password)){
             return true;
         }
         return false;
     }

    private void userParamCheck(String name, String email, String password, String biography,
                                String homeDescription, Integer age, Sex sex){
        String error = "";
        if (name == null || name.trim().length() == 0) {
            error = "name cannot be empty! ";
        }
        if (email == null || email.trim().length() == 0) {
            error = error + "email cannot be empty ";
        }else if(!isValid(email)){
            error = error + "the email " + email + " doesn't have a valid format.";
        }

        if (password == null || password.trim().length() == 0) {
            error = error + "password cannot be empty ";
        }
        if (biography == null || biography.trim().length() == 0) {
            error = error + "biography cannot be empty ";
        }
        if (homeDescription == null || homeDescription.trim().length() == 0) {
            error = error + "homeDescription cannot be empty ";
        }
        if (age <= 0) {
            error = error + "age is not valid ";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }
    }

    private static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
