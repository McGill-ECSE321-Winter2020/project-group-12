package ca.mcgill.ecse321.petadoption.dto;

import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import java.sql.Date;

public class ApplicationDto {
    private Date dateOfSubmission;
    private String note;
    private Advertisement advertisement;
    private AppUser applicant;
    private String applicationId;

    public ApplicationDto(){
    }

    public ApplicationDto(Date date, String note, Advertisement ad, AppUser applicant, String id){
        this.dateOfSubmission = date;
        this.note = note;
        this.advertisement = ad;
        this.applicant = applicant;
        this.applicationId = id;
    }

    public Date getDateOfSubmission() {
        return dateOfSubmission;
    }

    public String getNote() {
        return note;
    }

    public Advertisement getAdvertisement(){
        return this.advertisement;
    }

    public AppUser getApplicant(){
        return this.applicant;
    }

    public void setDateOfSubmission(Date dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

    public void setApplicant(AppUser applicant) {
        this.applicant = applicant;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
