package ca.mcgill.ecse321.petadoption.controller;


import ca.mcgill.ecse321.petadoption.dto.ApplicationDto;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Application;
import ca.mcgill.ecse321.petadoption.model.Status;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
import ca.mcgill.ecse321.petadoption.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    @Autowired
    private AppUserService appUserService;

    @GetMapping(value = {"/applications/{advertisementId}", "/applications/{advertisementId}/"})
    public List<ApplicationDto> getAllApplications(@PathVariable("advertisementId") String advertisementId, @RequestHeader String jwt) {
        AppUser requester = appUserService.getAppUserByJwt(jwt);
        List<ApplicationDto> applicationDtoList = new ArrayList<>();
        List<Application> applicationList = service.getAllApplicationsForAdvertisement(advertisementId, requester.getEmail());
        for (Application app : applicationList) {
            applicationDtoList.add(convertToDto(app));
        }
        return applicationDtoList;
    }

    @PostMapping(value = {"/applications/create/", "/applications/create"})
    public ApplicationDto createApplication(@RequestBody ApplicationDto ap, @RequestHeader String jwt) throws IllegalArgumentException {
        AppUser requester = appUserService.getAppUserByJwt(jwt);
        Application appl = service.createApplication(ap.getAdvertisementId(), ap.getApplicantEmail(), ap.getDateOfSubmission(), ap.getNote(), Status.pending, requester.getEmail());
        return convertToDto(appl);
    }

    @DeleteMapping(value = {"/application/delete/{applicationId}", "/application/delete/{applicationId}/"})
    public void deleteApplication(@PathVariable("applicationId") String applicationID, @RequestHeader String jwt) {
        AppUser requester = appUserService.getAppUserByJwt(jwt);
        service.deleteApplication(applicationID, requester.getEmail());
    }

    // gotta test these
    @GetMapping(value = {"/application/{applicationID}", "/application/{applicationID}/"})
    public ApplicationDto getApplicationById(@PathVariable String applicationID, @RequestHeader String jwt) {
        AppUser requester = appUserService.getAppUserByJwt(jwt);
        Application app = service.getApplicationByID(applicationID, requester.getEmail());
        return convertToDto(app);
    }

    // gave bugs that jwt column was non-existent for user
    @PutMapping(value = {"/application/update", "/application/update/"})
    public ApplicationDto updateApplication(@RequestBody ApplicationDto application, @RequestHeader String jwt) {
        AppUser requester = appUserService.getAppUserByJwt(jwt); // checking user is logged in; thats all
        return convertToDto(service.updateApplicationStatus(application.getApplicationId(), application.getStatus(), requester.getEmail()));
    }

    private ApplicationDto convertToDto(Application app) {
        return new ApplicationDto(app.getDateOfSubmission(), app.getNote(), app.getAdvertisement().getAdvertisementId(), app.getApplicant().getEmail(), app.getApplicationId(), app.getStatus());
    }
}
