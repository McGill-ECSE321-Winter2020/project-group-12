package ca.mcgill.ecse321.petadoption.controller;

import ca.mcgill.ecse321.petadoption.dto.AdvertisementDto;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.service.AdvertisementService;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private AppUserService appUserService;

    //TODO: Test POST mapping for advertisement after implementing POST method for AppUser
    @PostMapping(value = {"/{userId}/advertisement/create", "/{userId}/advertisement/create/"})
    public AdvertisementDto createAdvertisement(@RequestBody AdvertisementDto ad, @RequestParam Date date,
                                                @PathVariable("userId") String userEmail, @RequestHeader String jwt) throws IllegalArgumentException {
        appUserService.getAppUserByJwt(jwt);
        Advertisement advertisement = advertisementService.createAdvertisement(userEmail, date, ad.getPetName(),
                ad.getPetAge(), ad.getPetDescription(), ad.getPetSex(), ad.getPetSpecies());
        return convertToDto(advertisement);
    }

    @PutMapping(value = {"/advertisement/update", "/advertisement/update/"})
    public AdvertisementDto updateAdvertisementDetails(@RequestParam("adId") String advertisementID,
                                                       @RequestBody AdvertisementDto advertisementDto, @RequestHeader String jwt) {
        appUserService.getAppUserByJwt(jwt);
        Advertisement advertisement = advertisementService.updateAdvertisement(advertisementID,
                advertisementDto.getPetName(), advertisementDto.getPetAge(), advertisementDto.getPetDescription(),
                advertisementDto.getPetSex(), advertisementDto.getPetSpecies(), advertisementDto.getUserEmail());
        return convertToDto(advertisement);
    }

    @PutMapping(value = {"/advertisement/updateExpiry", "/advertisement/updateExpiry/"})
    public AdvertisementDto updateAdvertisementExpiration(@RequestParam("adId") String advertisementID,
                                                          @RequestParam("expired") boolean expired, @RequestHeader String jwt) {
        AppUser requester = appUserService.getAppUserByJwt(jwt);
        Advertisement advertisement = advertisementService.updateAdvertisementIsExpired(advertisementID, expired, requester.getEmail());
        return convertToDto(advertisement);
    }

    @DeleteMapping(value = {"/advertisement/delete", "/advertisement/delete/"})
    public void deleteAdvertisement(@RequestParam("adId") String advertisementID, @RequestHeader String jwt) {
        AppUser requester = appUserService.getAppUserByJwt(jwt);
        advertisementService.deleteAdvertisement(advertisementID, requester.getEmail());
    }

    @GetMapping(value = {"/advertisements", "/advertisements/"})
    public List<AdvertisementDto> getAllAdvertisements(@RequestHeader String jwt) {
        throw new IllegalArgumentException("in get all ads");
//        appUserService.getAppUserByJwt(jwt); // making sure user is logged in; thats all
//        List<AdvertisementDto> advertisementDtoList = new ArrayList<>();
//        for (Advertisement advertisement : advertisementService.getAllAdvertisements()) {
//            advertisementDtoList.add(convertToDto(advertisement));
//        }
//        return advertisementDtoList;
    }

    @GetMapping(value = {"/{userID}/advertisements", "/{userID}/advertisements/"})
    public List<AdvertisementDto> getAdvertisementsByAppUser(@PathVariable("userID") String userEmail, @RequestHeader String jwt) {
        appUserService.getAppUserByJwt(jwt); // making sure user is logged in; thats all
        return createAdvertisementDtosForAppUser(userEmail);
    }

    @GetMapping(value = {"/advertisement/{advertisementID}", "/advertisement/{advertisementID}/"})
    public AdvertisementDto getAdvertisementById(@PathVariable("advertisementID") String advertisementID, @RequestHeader String jwt) {
        appUserService.getAppUserByJwt(jwt); // making sure user is logged in; thats all
        Advertisement advertisement = advertisementService.getAdvertisementByID(advertisementID);
        return convertToDto(advertisement);
    }

    // Changed method to public so that method can be called by other controller classes that need it
    // Method made static so method can be used without the need of an instance of the AdvertisementDTO Class - Check
    public static AdvertisementDto convertToDto(Advertisement ad) {
        if (ad == null) {
            throw new IllegalArgumentException("There is no such Advertisement!");
        }
        return new AdvertisementDto(ad.getPostedBy().getEmail(), ad.getDatePosted(),
                ad.getAdvertisementId(), ad.isIsExpired(), ad.getPetName(), ad.getPetAge(), ad.getPetDescription(),
                ad.getPetSex(), ad.getPetSpecies());
    }

    private List<AdvertisementDto> createAdvertisementDtosForAppUser(String userEmail) {
        List<Advertisement> advertisementsForAppUser = advertisementService.getAdvertisementsOfAppUser(userEmail);
        List<AdvertisementDto> advertisementDtos = new ArrayList<>();
        for (Advertisement advertisement : advertisementsForAppUser) {
            advertisementDtos.add(convertToDto(advertisement));
        }
        return advertisementDtos;
    }
}
