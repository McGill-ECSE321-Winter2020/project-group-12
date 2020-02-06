package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import java.sql.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Donation{
    private AppUser donor;
    private Integer amount;
    private Date dateOfPayment;
    private Long transactionNumber;

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

    public void setTransactionNumber(Long value) {
        this.transactionNumber = value;
    }
    @Id
    @GeneratedValue
    public Long getTransactionNumber() {
        return this.transactionNumber;
    }

   }
