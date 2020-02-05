package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import ca.mcgill.ecse321.petadoption.model.java.sql.Date;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Donation{
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
public Integer getTransactionNumber() {
    return this.transactionNumber;
}
   private Person donor;
   
   @ManyToOne(optional=false)
   public Person getDonor() {
      return this.donor;
   }
   
   public void setDonor(Person donor) {
      this.donor = donor;
   }
   
   }
