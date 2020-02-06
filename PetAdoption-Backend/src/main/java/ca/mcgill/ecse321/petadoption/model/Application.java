package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Application{
    private Date dateOfSubmission;
    private String note;
    private Advertisement advertisement;
    private AppUser applicant;
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;


    public void setDateOfSubmission(Date value) {
        this.dateOfSubmission = value;
    }
    public Date getDateOfSubmission() {
        return this.dateOfSubmission;
    }



    public void setStatus(Status value) {
        this.status = value;
    }
    public Status getStatus() {
        return this.status;
    }

    public void setNote(String value) {
        this.note = value;
    }
    public String getNote() {
        return this.note;
    }

    @ManyToOne(optional=false)
    public Advertisement getAdvertisement() {
       return this.advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
       this.advertisement = advertisement;
    }


    public void setId(Long value) {
        this.id = value;
    }
    @Id
    @GeneratedValue
    public Long getId() {
        return this.id;
    }

    @ManyToOne(optional=false)
    public AppUser getApplicant() {
      return this.applicant;
    }

    public void setApplicant(AppUser applicant) {
      this.applicant = applicant;
    }



}
