package ca.mcgill.ecse321.petadoption.controller;


import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.petadoption.service.ApplicationService;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.Application;
import ca.mcgill.ecse321.petadoption.dto.ApplicationDto;
import ca.mcgill.ecse321.petadoption.dto.AdvertisementDto;

import java.sql.Date;


@CrossOrigin(origins = "*")
@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    @GetMapping(value = {"/applications", "/applications/"})
    public List<ApplicationDto> getAllApplications() {
        List<ApplicationDto> applicationDtoList = new ArrayList<>();
        List<Application> applicationList = service.getAllApplications();
        for (Application app : applicationList) {
            applicationDtoList.add(convertToDto(app));
        }
        return applicationDtoList;
    }

    @PostMapping(value = {"/applications/create/", "/applications/create"})
    public ApplicationDto createApplication(@RequestBody ApplicationDto ap) throws IllegalArgumentException {
        Application appl = service.createApplication(ap.getAdvertisementId(), ap.getApplicantEmail(), ap.getDateOfSubmission(), ap.getNote(), Status.pending);
        return convertToDto(appl);
    }

    @DeleteMapping(value = {"/application/delete", "/application/delete/"})
    public void deleteApplication(@RequestParam("applicationId") String applicationID) {
        service.deleteApplication(applicationID);
    }

    @GetMapping(value = {"/application/{applicationID}", "/application/{applicationID}/"})
    public ApplicationDto getAdvertisementById(@RequestBody ApplicationDto apdto) throws IllegalArgumentException {
        Application app;
        try {
            app = service.getApplicationByID(apdto.getApplicationId());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return convertToDto(app);
    }

    private ApplicationDto convertToDto(Application app) {
        return new ApplicationDto(app.getDateOfSubmission(), app.getNote(), app.getAdvertisement().getAdvertisementId(), app.getApplicant().getEmail(), app.getApplicationId(), app.getStatus());
    }

//    private AdvertisementDto convertAdToDto(Advertisement ad) {
//        AdvertisementDto advertisementDto = new AdvertisementDto(ad.getPostedBy(), ad.getDatePosted(),
//                ad.getAdvertisementId(), ad.isIsExpired(), ad.getPetName(), ad.getPetAge(), ad.getPetDescription(),
//                ad.getPetSex(), ad.getPetSpecies(), ad.getApplications(), ad.getPetImages());
//        return advertisementDto;
//    }

    //not sure about this one
    @PutMapping(value = {"/application/update", "/application/update/"})
    public ResponseEntity<Object> updateApplication(@RequestParam("applicationId") String applicationID) {
        return null;
    }

}
