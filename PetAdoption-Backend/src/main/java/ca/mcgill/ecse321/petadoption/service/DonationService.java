package ca.mcgill.ecse321.petadoption.service;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Donation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

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
     * @param transactionNumber(String)
     * @return Donation object
     */
    @Transactional
    public Donation createDonation(AppUser user2, Integer amount, Date dateOfPayment, String transactionNumber) {
        Donation donation = new Donation();
        String error = "";

        if (user2 == null) {
            error = "A Donation must have a AppUser ";
        }
        if (amount <= 0) {
            error = "Go take care of yourself! ";
        }
        if (dateOfPayment == null) {
            error = error + "dateOfPayment cannot be empty ";
        }
        if (transactionNumber == null || transactionNumber.trim().length() == 0) {
            error = error + "transactionNumber is invalid ";
        }

        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }

        donation.setAmount(amount);
        donation.setDateOfPayment(dateOfPayment);
        donation.setTransactionID(transactionNumber);
        donation.setDonor(user2);

        donationRepository.save(donation);
        return donation;
    }
    /**
     * Returns the Donation with specified transactionNumber from the database.
     *
     * @param transactionNumber
     * @return Donation object
     */
    @Transactional
    public Donation getDonationByTransactionID(String transactionNumber) {
        if (transactionNumber == null || transactionNumber.trim().length() == 0) {
            throw new IllegalArgumentException("Donation must have a transactionNumber!");
        }
        Donation a = donationRepository.findDonationByTransactionID(transactionNumber);
        return a;
    }

    /**
     * Returns all Donations in the database.
     *
     * @return List of Donation objects
     */
    @Transactional
    public List<Donation> getAllDonations() {
        return toList(donationRepository.findAll());
    }

    /////////////////////////////Donation Delete Method////////////////////////////////////////

    /**
     * Deletes the Donation with specified transactionNumber from the database.
     *
     * @param transactionNumber
     */
    @Transactional
    public void deleteDonation(String transactionNumber) {
        donationRepository.deleteDonationByTransactionID(transactionNumber);
    }

}
