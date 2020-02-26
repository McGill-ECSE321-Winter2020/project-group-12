package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.util.UUID;

@Entity
public class Donation{
    private AppUser donor;
    private Integer amount;
    private Date dateOfPayment;
    private String transactionID;

    @ManyToOne(optional=false)
    public AppUser getDonor() {
      return this.donor;
    }

    public void setDonor(AppUser donor) {
      this.donor = donor;
    }
    
    public void setAmount(Integer value) {
        this.amount = value;
    }
    public Integer getAmount() {
        return this.amount;
    }

    public void setDateOfPayment(Date value) {
        this.dateOfPayment = value;
    }
    public Date getDateOfPayment() {
        return this.dateOfPayment;
    }

    public void setTransactionID(String value) {
        this.transactionID = value;
    }
    public void setTransactionID() {
        this.transactionID = UUID.randomUUID().toString();
    }
    @Id
    public String getTransactionID() {
        return this.transactionID;
    }

}
