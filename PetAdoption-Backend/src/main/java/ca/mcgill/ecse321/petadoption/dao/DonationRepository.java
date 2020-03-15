package ca.mcgill.ecse321.petadoption.dao;

import ca.mcgill.ecse321.petadoption.model.Donation;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface DonationRepository extends CrudRepository<Donation, String>{

    Donation findDonationByTransactionID(String transactionID);
    List<Donation> findDonationByDateOfPayment(Date DateOfPayment);
    List<Donation> findDonationByDonorEmail(String email);
    List<Donation> findDonationByDateOfPaymentAndDonorEmail(Date dateOfPayment, String email);
    void deleteDonationByTransactionID(String transactionID);

}
