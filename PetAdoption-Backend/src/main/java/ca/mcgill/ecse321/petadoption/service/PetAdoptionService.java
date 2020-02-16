package ca.mcgill.ecse321.petadoption.service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.petadoption.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.ApplicationRepository;
import ca.mcgill.ecse321.petadoption.dao.DonationRepository;
import ca.mcgill.ecse321.petadoption.dao.ImageRepository;

public class PetAdoptionService {
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

    @Transactional
    public Advertisement createAdvertisement(Date datePosted, boolean isExpired, String petName, Integer petAge, String petDescription, Sex petSex, Species petSpecies) {
        Advertisement ad = new Advertisement();
        String error = "";
        if (datePosted == null) {
            error = "datePosted can not be empty! ";
        }
        if (isExpired == true) {
            error = error + "isExpired cannot be true at this point! ";
        }
        if (petName == null || petName.trim().length() == 0) {
            error = error + "petName cannot be empty! ";
        }
        if (petAge <= 0) {
            error = error + "petAge cannot be less than or equal to 0! ";
        }
        if (petDescription == null || petDescription.trim().length() == 0) {
            error = error + "petDescription cannot be empty! ";
        }
        if (petSex != Sex.F && petSex != Sex.M) {
            error = error + "petSex must be either male or female!";
        }
        if (petSpecies != Species.bird && petSpecies != Species.cat && petSpecies != Species.dog && petSpecies != Species.rabbit && petSpecies != Species.other) {
            error = error + "petSpecies is invalid! ";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }

        ad.setDatePosted(datePosted);
        ad.setAdvertisementId();
        ad.setPetDescription(petDescription);
        ad.setPetName(petName);
        ad.setPetAge(petAge);
        ad.setPetSex(petSex);
        ad.setPetSpecies(petSpecies);

        advertisementRepository.save(ad);
        return ad;
    }

}
