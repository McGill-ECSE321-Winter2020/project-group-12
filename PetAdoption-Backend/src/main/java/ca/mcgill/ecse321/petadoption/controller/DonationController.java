package ca.mcgill.ecse321.petadoption.controller;

import ca.mcgill.ecse321.petadoption.dto.DonationDto;
import ca.mcgill.ecse321.petadoption.model.Donation;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
import ca.mcgill.ecse321.petadoption.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class DonationController {

    @Autowired
    private DonationService donationService;
    @Autowired
    private AppUserService appUserService;

    public static DonationDto convertToDto(Donation donation) {
        if(donation == null) {
            throw new IllegalArgumentException("There is no such Donation!");
        }
        return new DonationDto(donation.getDonor().getEmail(), donation.getDateOfPayment(), donation.getAmount(), donation.getTransactionID());
    }


    @PostMapping(value ={"/{userId}/donations","/{userId}/donations/"})
    public DonationDto createDonation(@RequestBody DonationDto don, @PathVariable("userId") String userEmail) throws IllegalArgumentException {
        Donation donation = donationService.createDonation(userEmail, don.getAmount(), don.getDateOfPayment());
        return convertToDto(donation);
    }

    @GetMapping(value = {"/donations/ids/{transactionID}", "/donations/ids/{transactionID}/"})
    public DonationDto getDonationByTransactionId(@PathVariable("transactionID") String transactionID) {
        Donation donation = donationService.getDonationByTransactionID(transactionID);
        return convertToDto(donation);
    }

    @GetMapping(value = {"/donations/dates/{date}", "/donations/dates/{date}/"})
    public List<DonationDto> getDonationsByDateOfPayment(@PathVariable("dateOfPayment") Date dateOfPayment) {
        List<Donation> donations = donationService.getDonationsByDateOfPayment(dateOfPayment);
        List<DonationDto> dtos = new ArrayList<>();
        for(Donation donation : donations) {
            dtos.add(convertToDto(donation));
        }
        return dtos;
    }

    @GetMapping(value = {"/donations/users/{userID}", "/donations/users/{userID}/"})
    public List<DonationDto> getDonationsByDonor(@PathVariable("userID") String userEmail) {
            List<DonationDto> userDonationDtos = new ArrayList<>();
            for(Donation don: donationService.getDonationsByUser(userEmail)) {
                userDonationDtos.add(convertToDto(don));
            }
            return userDonationDtos;
    }

    @GetMapping(value = {"/donations", "/donations/" })
    public List<DonationDto> getAllDonations() {
        List<DonationDto> donationDtos = new ArrayList<>();
        for(Donation don : donationService.getAllDonations()) {
            donationDtos.add(convertToDto(don));
        }
        return donationDtos;
    }



}
