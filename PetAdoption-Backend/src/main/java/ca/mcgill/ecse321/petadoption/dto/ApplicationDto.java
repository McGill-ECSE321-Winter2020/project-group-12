package ca.mcgill.ecse321.petadoption.dto;

import ca.mcgill.ecse321.petadoption.model.Application;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import java.sql.Date;

public class ApplicationDto {
    private Date dateOfSubmission;
    private String note;
    private String advertisementId;
    private String applicantEmail;
    private String applicationId;

    public ApplicationDto(){
    }

    public ApplicationDto(Date date, String note, String  advertisementId, String applicantEmail, String id){
        this.dateOfSubmission = date;
        this.note = note;
        this.advertisementId = advertisementId;
        this.applicantEmail = applicantEmail;
        this.applicationId = id;
    }

    public void setDateOfSubmission(Date dat){
        this.dateOfSubmission = dat;
    }

    public Date getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void setNote(String note){
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setAdvertisement(String advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getAdvertisementId(){
        return this.advertisementId;
    }

    public void setApplicant(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getApplicantEmail(){
        return this.applicantEmail;
    }

    public void setApplicationId(String id){
        this.applicationId = id;
    }

    public String getApplicationId(){
        return this.applicationId;
    }

}
