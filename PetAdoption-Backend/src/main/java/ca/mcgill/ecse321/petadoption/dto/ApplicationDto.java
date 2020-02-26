package ca.mcgill.ecse321.petadoption.dto;

import ca.mcgill.ecse321.petadoption.model.AppUser;
import java.sql.Date;

public class ApplicationDto {
    private Date dateOfSubmission;
    private String note;
    private AdvertisementDto advertisement;
    private AppUser applicant;
    private String applicationId;

    public ApplicationDto(){
    }

    public ApplicationDto(Date date, String note, AdvertisementDto ad, AppUser applicant, String id){
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

    public AdvertisementDto getAdvertisement(){
        return this.advertisement;
    }

    public AppUser getApplicant(){
        return this.applicant;
    }

}
