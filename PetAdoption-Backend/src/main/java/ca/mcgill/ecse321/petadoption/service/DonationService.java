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


    /**
     * Creates and adds a new Donation object to the database.
     *
     * @param amount(Integer)
     * @param dateOfPayment(Date)
     * @return Donation object
     */
    @Transactional
    public Donation createDonation(String userEmail, Integer amount, Date dateOfPayment) {
        Donation donation = null;
        String error = "";
        if(userEmail == null || userEmail.trim().length() == 0) {
            throw new IllegalArgumentException("A valid email is needed to make a donation");
        }

        AppUser user = appUserRepository.findAppUserByEmail(userEmail);

        if (user == null) {
            throw new IllegalArgumentException("Donation cannot be created without a donor!");
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
    public Donation getDonationByTransactionID(String transactionID) {
        if (transactionID == null || transactionID.trim().length() == 0) {
            throw new IllegalArgumentException("Donation must have a valid transactionID!");
        }
        Donation a = donationRepository.findDonationByTransactionID(transactionID);
        return a;
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

        return donations;
    }

    @Transactional
    public List<Donation> getDonationsByDateOfPayment(Date dateOfPayment) {
        if(dateOfPayment == null) {
            throw new IllegalArgumentException("Date of payment cannot be empty");
        }
        List<Donation> donations = donationRepository.findDonationByDateOfPayment(dateOfPayment);
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

        return donationRepository.findDonationByDateOfPaymentAndDonorEmail(dateOfPayment, userEmail);
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

    /////////////////////////////Donation Delete Method////////////////////////////////////////

//    /**
//     * Deletes the Donation with specified transactionID from the database.
//     *
//     * @param transactionID
//     */
//    @Transactional
//    public void deleteDonation(String transactionID) {
//        donationRepository.deleteDonationByTransactionID(transactionID);
//    }

}
