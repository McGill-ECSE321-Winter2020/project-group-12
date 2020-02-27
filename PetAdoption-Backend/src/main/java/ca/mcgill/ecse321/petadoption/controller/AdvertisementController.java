package ca.mcgill.ecse321.petadoption.controller;

import ca.mcgill.ecse321.petadoption.dto.AdvertisementDto;
import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.service.PetAdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@CrossOrigin(origins = "*")
@RestController
public class AdvertisementController {

    @Autowired
    private PetAdoptionService service;

    @PostMapping(value = {"{userEmail}/advertisement/create", "{userEmail}/advertisement/create/"})
    public AdvertisementDto createAdvertisement(@RequestBody Advertisement ad, @RequestParam Date date,
                                                @PathVariable String userEmail) throws IllegalArgumentException {
        Advertisement advertisement = service.createAdvertisement(userEmail, date, false, ad.getPetName(),
                ad.getPetAge(), ad.getPetDescription(), ad.getPetSex(), ad.getPetSpecies());
        return convertToDto(advertisement);
    }

    private AdvertisementDto convertToDto(Advertisement ad) {
        if (ad == null) {
            throw new IllegalArgumentException("There is no such Advertisement!");
        }
        AppUserDto appUserDto = convertToDto(ad.getPostedBy());
        AdvertisementDto advertisementDto = new AdvertisementDto(appUserDto, ad.getDatePosted(), ad.getAdvertisementId()
                , ad.isIsExpired(), ad.getPetName(), ad.getPetAge(), ad.getPetDescription(), ad.getPetSex(),
                ad.getPetSpecies(), ad.getApplications(), ad.getPetImages());

        return advertisementDto;
    }

    @RequestMapping(value = {"/advertisement/update", "/advertisement/update/"}, method = RequestMethod.PUT)
    public ResponseEntity<Object> updateAdvertisement(@RequestParam String advertisementID) {
        return null;
    }

    @RequestMapping(value = {"/advertisement/delete", "/advertisement/delete/"}, method = RequestMethod.DELETE)
    public RequestEntity<Object> deleteAdvertisement(@RequestParam String advertisementID) {
        return null;
    }

    @RequestMapping(value = {"/advertisements", "/advertisements/"}, method = RequestMethod.GET)
    public ResponseEntity<Object> getAllAdvertisements() {
        return null;
    }

    @RequestMapping(value = {"/{userID}/advertisements", "/{userID}/advertisements/"}, method = RequestMethod.GET)
    public RequestEntity<Object> getAdvertisementsByAppUser(@PathVariable("userID") String userID) {
        return null;
    }

    @RequestMapping(value = {"/advertisement/{advertisementID}", "/advertisement/{advertisementID/"},
            method = RequestMethod.GET)
    public ResponseEntity<Object> getAdvertisementById(@PathVariable("advertisementID") String advertisementID) {
        return null;
    }


}
