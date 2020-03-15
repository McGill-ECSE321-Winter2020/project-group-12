package ca.mcgill.ecse321.petadoption.service;

import ca.mcgill.ecse321.petadoption.controller.SecurityConfigurer;
import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.dao.ApplicationRepository;
import ca.mcgill.ecse321.petadoption.dao.DonationRepository;
import ca.mcgill.ecse321.petadoption.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @Autowired
    DonationService donationService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    AdvertisementService advertisementService;

    @Autowired
    SecurityConfigurer secConfigurer;

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * Creates and adds a new AppUser object to the database ie registers a user.
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
     * Checks if a user exists in the database and the inputs are valid
     * @param email
     * @param password
     * @return
     */
    public void login(String email, String password){
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

        // idk if this is needed
//         try {
//             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//         } catch (BadCredentialsException e) {
//             throw new IllegalArgumentException("Incorrect username or password");
//         }

        AppUser user = appUserRepository.findAppUserByEmail(email);
        if(user!= null && user.getPassword().equals(password)){
            return;
        } else { // so either user == null or (user != null and passwordGiven != userPassword in DB)
            throw new IllegalArgumentException("Incorrect username or password");
        }
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
     * Returns the AppUser with the specified jwt from the database.
     *
     * @param jwt
     * @return AppUser with specified jwt
     */
    @Transactional
    public AppUser getAppUserByJwt(String jwt) {
        if (jwt == null || jwt.trim().length() == 0) {
            throw new IllegalArgumentException("The jwt entered is not valid");
        }

        AppUser user = appUserRepository.findAppUserByJwt(jwt);
        if(user == null) {
            throw new IllegalArgumentException("The user with this token does not exist");
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
    public void deleteAppUser(String email, String jwt) {
        AppUser user1 = appUserRepository.findAppUserByEmail(email);
        AppUser requester = getAppUserByJwt(jwt);
        if(user1 != requester) {
            throw new IllegalArgumentException("You are not authorized to delete this account!");
        }
        for(Application app : user1.getApplications()){
            applicationRepository.deleteApplicationByApplicationId(app.getApplicationId());
        }
        for (Advertisement ad : user1.getAdvertisements()){
            advertisementRepository.deleteAdvertisementByAdvertisementId(ad.getAdvertisementId());
        }
        for(Donation donation: user1.getDonations()){
            donationRepository.deleteDonationByTransactionID(donation.getTransactionID());
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
                                  String homeDescription, Integer age, boolean isAdmin, Sex sex, String jwt){

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
         user.setJwt(jwt);
         return appUserRepository.save(user);
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
        return pat.matcher(email).matches();
    }
}
