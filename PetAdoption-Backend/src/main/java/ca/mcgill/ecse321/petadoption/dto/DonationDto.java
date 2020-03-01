package ca.mcgill.ecse321.petadoption.dto;

import java.sql.Date;

public class DonationDto {
    private String donorEmail;
    private Integer amount;
    private Date dateOfPayment;
    private String transactionID;

    // constructor: come back to this to see how many constructors u need and what u shld pass into them (???)

    public DonationDto(String userEmail, Date dateOfPayment, Integer amount, String transactionID) {
        this.donorEmail = userEmail;
        this.dateOfPayment = dateOfPayment;
        this.amount = amount;
        this.transactionID = transactionID;
    }

    // getters
    public String getDonorEmail() {
        return this.donorEmail;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public Date getDateOfPayment() {
        return this.dateOfPayment;
    }

    public String getTransactionID() {
        return this.transactionID;
    }
}
