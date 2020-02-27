package ca.mcgill.ecse321.petadoption.controller;

import ca.mcgill.ecse321.petadoption.dto.AdvertisementDto;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.service.PetAdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AdvertisementController {

    @Autowired
    private PetAdoptionService service;

    //TODO: Test POST mapping for advertisement after implementing POST method for AppUser
    @PostMapping(value = {"{userId}/advertisement/create", "{userId}/advertisement/create/"})
    public AdvertisementDto createAdvertisement(@RequestBody Advertisement ad, @RequestParam Date date,
                                                @PathVariable("userId") String userEmail) throws IllegalArgumentException {
        Advertisement advertisement = service.createAdvertisement(userEmail, date, false, ad.getPetName(),
                ad.getPetAge(), ad.getPetDescription(), ad.getPetSex(), ad.getPetSpecies());
        return convertToDto(advertisement);
    }

    @PutMapping(value = {"/advertisement/update", "/advertisement/update/"})
    public ResponseEntity<Object> updateAdvertisement(@RequestParam("adId") String advertisementID) {
        return null;
    }

    @DeleteMapping(value = {"/advertisement/delete", "/advertisement/delete/"})
    public boolean deleteAdvertisement(@RequestParam("adId") String advertisementID) {
        return service.deleteAdvertisement(advertisementID);
    }

    @GetMapping(value = {"/advertisements", "/advertisements/"})
    public List<AdvertisementDto> getAllAdvertisements() {
        List<AdvertisementDto> advertisementDtoList = new ArrayList<>();
        for (Advertisement advertisement : service.getAllAdvertisements()) {
            advertisementDtoList.add(convertToDto(advertisement));
        }
        return advertisementDtoList;
    }

    //TODO: Test GET Mapping after AppUser class has been implemented
    @GetMapping(value = {"/{userID}/advertisements", "/{userID}/advertisements/"})
    public List<AdvertisementDto> getAdvertisementsByAppUser(@PathVariable("userID") String userEmail) {
        List<Advertisement> advertisements;
        try {
            AppUser appUser = service.getAppUserByEmail(userEmail);
            // Converting HashSet of Advertisements to ArrayList of Advertisements
            advertisements = new ArrayList<>(appUser.getAdvertisements());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return null;
    }

    @GetMapping(value = {"/advertisement/{advertisementID}", "/advertisement/{advertisementID/"})
    public AdvertisementDto getAdvertisementById(@PathVariable("advertisementID") String advertisementID) {
        Advertisement advertisement;
        try {
            advertisement = service.getAdvertisementByID(advertisementID);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return convertToDto(advertisement);
    }

    // Changed method to public so that method can be called by other controller classes that need it
    // Method made static so method can be used without the need of an instance of the AdvertisementDTO Class - Check
    public static AdvertisementDto convertToDto(Advertisement ad) {
        if (ad == null) {
            throw new IllegalArgumentException("There is no such Advertisement!");
        }
        AdvertisementDto advertisementDto = new AdvertisementDto(ad.getPostedBy(), ad.getDatePosted(),
                ad.getAdvertisementId(), ad.isIsExpired(), ad.getPetName(), ad.getPetAge(), ad.getPetDescription(),
                ad.getPetSex(), ad.getPetSpecies(), ad.getApplications(), ad.getPetImages());
        return advertisementDto;
    }

//    Evaluate the necessities of the following convert to DTO methods for the Advertisement class
//    Method needs convertToDto from application class
//    public static List<ApplicationDto> createApplicationDtosForAdvertisement(Set<Application> applications){ Set or List?
//        return null;
//    }
//
//    Method needs convertToDto from image class
//    public static List<ImageDto> createImageDtosForAdvertisement(Set<Image> images){
//        return null;
//    }

}
