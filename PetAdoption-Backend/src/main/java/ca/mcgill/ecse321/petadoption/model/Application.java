package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import java.sql.Date;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Application{
   private Date dateOfSubmission;

public void setDateOfSubmission(Date value) {
    this.dateOfSubmission = value;
}
public Date getDateOfSubmission() {
    return this.dateOfSubmission;
}
private Status status;

public void setStatus(Status value) {
    this.status = value;
}
public Status getStatus() {
    return this.status;
}
private String note;

public void setNote(String value) {
    this.note = value;
}
public String getNote() {
    return this.note;
}
private Person applicant;

@ManyToOne(optional=false)
public Person getApplicant() {
   return this.applicant;
}

public void setApplicant(Person applicant) {
   this.applicant = applicant;
}

private Advertisement advertisement;

@ManyToOne(optional=false)
public Advertisement getAdvertisement() {
   return this.advertisement;
}

public void setAdvertisement(Advertisement advertisement) {
   this.advertisement = advertisement;
}

private Integer id;

public void setId(Integer value) {
    this.id = value;
}
@Id
public Integer getId() {
    return this.id;
}
}
