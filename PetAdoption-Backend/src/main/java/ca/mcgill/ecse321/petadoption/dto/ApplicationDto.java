package ca.mcgill.ecse321.petadoption.dto;

import ca.mcgill.ecse321.petadoption.model.Status;

import java.sql.Date;

public class ApplicationDto {
    private Date dateOfSubmission;
    private String note;
    private String advertisementId;
    private String applicantEmail;
    private String applicationId;
    private Status status;

    public ApplicationDto(){
    }

    public ApplicationDto(Date date, String note, String  advertisementId, String applicantEmail, String id, Status status){
        this.dateOfSubmission = date;
        this.note = note;
        this.advertisementId = advertisementId;
        this.applicantEmail = applicantEmail;
        this.applicationId = id;
        this.status = status;
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

    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getAdvertisementId(){
        return this.advertisementId;
    }

    public void setApplicantEmail(String applicantEmail) {
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

    public void setStatus(Status status){
        this.status = status;
    }

    public Status getStatus(){
        return this.status;
    }

}
