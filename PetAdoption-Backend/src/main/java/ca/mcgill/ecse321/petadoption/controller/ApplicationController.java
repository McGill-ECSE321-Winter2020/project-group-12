package ca.mcgill.ecse321.petadoption.controller;


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
import java.sql.Date;


@CrossOrigin(origins = "*")
@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    @GetMapping(value = {"/applications", "/applications/"})
    public List<ApplicationDto> getAllApplications() {
        List<ApplicationDto> applicationDtoList = new ArrayList<>();
        for (Application app : service.getAllApplications()) {
            applicationDtoList.add(convertToDto(app));
        }
        return applicationDtoList;
    }

    @PostMapping(value = {"/applictions/", "/applications/"})
    public ApplicationDto createApplication(@RequestBody ApplicationDto ap) throws IllegalArgumentException {
        Application appl = service.createApplication(ap.getAdvertisement(), ap.getAppUser(), ap.getApplicationId(), ap.getDateOfSubmission(), ap.getNote(), ap.getStatus());
        return convertToDto(appl);
    }



    }
