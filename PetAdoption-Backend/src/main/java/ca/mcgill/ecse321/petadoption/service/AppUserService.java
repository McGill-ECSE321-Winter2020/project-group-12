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
        appUserRepository.save(new_user);
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
            throw new IllegalArgumentException("AppUser must have an email");
        }
        AppUser a = appUserRepository.findAppUserByEmail(email);
        return a;
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

        while (user1.getAdvertisements().size() != 0) {
            Set<Advertisement> advertisements = user1.getAdvertisements();
            Advertisement ad = advertisements.iterator().next();
            advertisementService.deleteAdvertisement(ad.getAdvertisementId());
        }

        while (user1.getDonations().size() != 0) {
            Set<Donation> donations = user1.getDonations();
            Donation donation = donations.iterator().next();
            donationService.deleteDonation(donation.getTransactionID());
        }

        while (user1.getApplications().size() != 0) {
            Set<Application> apps = user1.getApplications();
            Application app = apps.iterator().next();
            applicationService.deleteApplication(app.getApplicationId());
        }

        appUserRepository.delete(user1);
    }

    /**
     * Updates the isAdmin attribute of AppUser class in the database.
     *
     * @param isAdmin
     * @return AppUser
     */
    @Transactional
    public AppUser updateAppUserIsAdmin(AppUser user, boolean isAdmin) {
        user.setIsAdmin(isAdmin);
        appUserRepository.save(user);
        return user;
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
    public AppUser updaterAppUser(String name, String email, String password, String biography,
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
         appUserRepository.save(user);
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
        if (sex != Sex.F && sex != Sex.M) {
            error = error + "sex is invalid ";
        }
        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }
    }
}
