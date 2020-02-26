package ca.mcgill.ecse321.petadoption.controller;

import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.service.PetAdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class AdvertisementController {

    @Autowired
    private PetAdoptionService service;

    @RequestMapping(value = {"/advertisement/create", "/advertisement/create/"}, method = RequestMethod.POST)
    public ResponseEntity<Object> createAdvertisement(@RequestBody Advertisement advertisement) {
        return null;
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
