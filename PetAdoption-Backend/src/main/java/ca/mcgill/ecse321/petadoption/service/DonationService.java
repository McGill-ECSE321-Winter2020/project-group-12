package ca.mcgill.ecse321.petadoption.service;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Donation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DonationService {
    @Autowired(required = true)
    AppUserRepository appUserRepository;
    @Autowired(required = true)
    ApplicationRepository applicationRepository;
    @Autowired(required = true)
    AdvertisementRepository advertisementRepository;
    @Autowired(required = true)
    DonationRepository donationRepository;
    @Autowired(required = true)
    ImageRepository imageRepository;
    @Autowired
    AppUserService appUserService;

    /**
     * Creates and adds a new Donation object to the database.
     *
     * @param amount(Integer)
     * @param dateOfPayment(Date)
     * @return Donation object
     */
    @Transactional
    public Donation createDonation(String userEmail, Integer amount, Date dateOfPayment, String jwt) {
        Donation donation = null;
        String error = "";
        if(userEmail == null || userEmail.trim().length() == 0) {
            throw new IllegalArgumentException("A valid email is needed to make a donation");
        }

        AppUser user = appUserRepository.findAppUserByEmail(userEmail);

        if (user == null) {
            throw new IllegalArgumentException("Donation cannot be created without a donor!");
        }

        if(!user.getJwt().equals(jwt)) {
            throw new IllegalArgumentException("Unauthorized request");
        }

        if(amount == null) {
            error = error + "Cannot make a donation with unspecified amount";
        } else if (amount <= 0) {
            error = error + "Cannot make a donation of $0 or less";
        }

        // need to double check how this will be filled in automatically
        if (dateOfPayment == null) {
            error = error + "Donation made cannot be missing a date of payment";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }

        donation = new Donation();
        donation.setAmount(amount);
        donation.setDateOfPayment(dateOfPayment);
        donation.setTransactionID();
        donation.setDonor(user);

        return donationRepository.save(donation);
    }
    /**
     * Returns the Donation with specified transactionNumber from the database.
     *
     * @param transactionID
     * @return Donation object
     */
    @Transactional
    public Donation getDonationByTransactionID(String transactionID, String jwt) {
        if (transactionID == null || transactionID.trim().length() == 0) {
            throw new IllegalArgumentException("Donation must have a valid transactionID!");
        }
        Donation donation = donationRepository.findDonationByTransactionID(transactionID);
        if(donation == null) {
            throw new IllegalArgumentException("A Donation with such an ID does not exist");
        }
        AppUser requester = appUserService.getAppUserByJwt(jwt);
        if(!(donation.getDonor().getEmail().equals(requester.getEmail())) && !(requester.isIsAdmin())) {
            throw new IllegalArgumentException("You are not authorized to view this donation");
        }
        return donation;
    }

    /**
     * Returns the donations made by the specific user.
     * @param userEmail
     * @return List of Donations made by user
     */
    @Transactional
    public List<Donation> getDonationsByUser(String userEmail) { // TODO: make sure its correct to pass in the app user like this!, i think we shld pass user email and then do findUserByEmail?
        if (userEmail == null || userEmail.trim().length() == 0) {
            throw new IllegalArgumentException("The email entered is not valid.");
        }

        List<Donation> donations = donationRepository.findDonationByDonorEmail(userEmail);
        if(donations.size() == 0) {
            throw new IllegalArgumentException("A donation with such an email does not exist.");
        }
        return donations;
    }

    @Transactional
    public List<Donation> getDonationsByDateOfPayment(Date dateOfPayment) {
        if(dateOfPayment == null) {
            throw new IllegalArgumentException("Date of payment cannot be empty");
        }
        List<Donation> donations = donationRepository.findDonationByDateOfPayment(dateOfPayment);
        if(donations.size() == 0) {
            throw new IllegalArgumentException("No donations were made on this date");
        }
        return donations;
    }

    @Transactional
    public List<Donation> getDonationsByDateAndDonor(Date dateOfPayment, String userEmail) {
        String error = "";
        if(userEmail == null || userEmail.trim().length() == 0) {
            error = "A user email must be provided! ";
        }

        if(dateOfPayment == null) {
            error += "Date of payment cannot be empty!";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }

        List<Donation> donations = donationRepository.findDonationByDateOfPaymentAndDonorEmail(dateOfPayment, userEmail);
        if(donations.size() == 0) {
            throw new IllegalArgumentException("No donations were found on this date by this donor.");
        }
        return donations;
    }

    /**
     *
     * Returns all Donations in the database.
     *
     * @return List of Donation objects
     */
    @Transactional
    public List<Donation> getAllDonations() {
        return new ArrayList<Donation>((Collection<? extends Donation>) donationRepository.findAll());
    }

}
