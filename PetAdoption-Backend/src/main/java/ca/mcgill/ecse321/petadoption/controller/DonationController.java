package ca.mcgill.ecse321.petadoption.controller;

import ca.mcgill.ecse321.petadoption.model.Donation;
import ca.mcgill.ecse321.petadoption.service.PetAdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class DonationController {

    @Autowired
    private PetAdoptionService service;

    @GetMapping(value = {"/donations", "/donations/" })
    public ResponseEntity<List<Donation>> getAllDonations() {
        List<Donation> donations = service.getAllDonations();
        return new ResponseEntity(donations, HttpStatus.valueOf(200));
    }


}
