package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import java.sql.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Donation{
    private AppUser donor;

    @ManyToOne(optional=false)
    public AppUser getDonor() {
      return this.donor;
    }

    public void setDonor(AppUser donor) {
      this.donor = donor;
    }

    private Integer amount;

    public void setAmount(Integer value) {
        this.amount = value;
    }
    public Integer getAmount() {
        return this.amount;
    }
    private Date dateOfPayment;

    public void setDateOfPayment(Date value) {
        this.dateOfPayment = value;
    }
    public Date getDateOfPayment() {
        return this.dateOfPayment;
    }
    private Integer transactionNumber;

    public void setTransactionNumber(Integer value) {
        this.transactionNumber = value;
    }
    @Id
    @GeneratedValue
    public Integer getTransactionNumber() {
        return this.transactionNumber;
    }

   }
