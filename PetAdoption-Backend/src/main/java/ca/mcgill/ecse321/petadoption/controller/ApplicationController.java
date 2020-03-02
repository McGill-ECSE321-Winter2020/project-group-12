package ca.mcgill.ecse321.petadoption.controller;


import ca.mcgill.ecse321.petadoption.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.petadoption.service.ApplicationService;
import ca.mcgill.ecse321.petadoption.model.Application;
import ca.mcgill.ecse321.petadoption.dto.ApplicationDto;


@CrossOrigin(origins = "*")
@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    @GetMapping(value = {"/applications/{advertisementId}", "/applications/{advertisementId}/"})
    public List<ApplicationDto> getAllApplications(@PathVariable("advertisementId") String advertisementId) {
        List<ApplicationDto> applicationDtoList = new ArrayList<>();
        List<Application> applicationList = service.getAllApplicationsForAdvertisement(advertisementId);
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

    @DeleteMapping(value = {"/application/delete/{applicationId}", "/application/delete/{applicationId}/"})
    public void deleteApplication(@PathVariable("applicationId") String applicationID) {
        service.deleteApplication(applicationID);
    }

    @GetMapping(value = {"/application/{applicationID}", "/application/{applicationID}/"})
    public ApplicationDto getApplicationById(@PathVariable String applicationID) {
        Application app = service.getApplicationByID(applicationID);
        return convertToDto(app);
    }

    @PutMapping(value = {"/application/update", "/application/update/"})
    public ApplicationDto updateApplication(@RequestBody ApplicationDto application) {
        return convertToDto(service.updateApplicationStatus(application.getApplicationId(), application.getStatus()));
    }

    private ApplicationDto convertToDto(Application app) {
        return new ApplicationDto(app.getDateOfSubmission(), app.getNote(), app.getAdvertisement().getAdvertisementId(), app.getApplicant().getEmail(), app.getApplicationId(), app.getStatus());
    }
}
