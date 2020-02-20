package ca.mcgill.ecse321.petadoption.controller;

import ca.mcgill.ecse321.petadoption.service.PetAdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@Controller
public class DonationController {

    @Autowired
    private PetAdoptionService service;
//
//    @GetMapping(value = {"/donations", "/donations/" })
//    public ResponseEntity<List<Donation>> getAllDonations() {
//        List<Donation> donations = service.getAllDonations();
//        return new ResponseEntity(donations, HttpStatus.valueOf(200));
//    }
}
