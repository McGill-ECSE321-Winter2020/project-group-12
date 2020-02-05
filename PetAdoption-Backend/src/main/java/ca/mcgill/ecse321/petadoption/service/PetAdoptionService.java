package ca.mcgill.ecse321.petadoption.service;


import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PetAdoptionService {

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    DonationRepository donationRepository;

    @Autowired
    ImageRepository imageRepository;

    @Transactional
    public AppUser createAppUser(String name, String email, String password, String biography, String homeDescription,
                                 int age, Sex sex, boolean isAdmin) {
        AppUser new_user = new AppUser();
        new_user.setName(name);
        new_user.setEmail(email);
        new_user.setPassword(password);
        new_user.setBiography(biography);
        new_user.setHomeDescription(homeDescription);
        new_user.setAge(age);
        new_user.setSex(sex);
        new_user.setIsAdmin(isAdmin);
        appUserRepository.save(new_user);
        return new_user;
    }

    @Transactional
    public AppUser getAppUser(String email) {
        return appUserRepository.findAppUserByEmail(email);
    }

}
