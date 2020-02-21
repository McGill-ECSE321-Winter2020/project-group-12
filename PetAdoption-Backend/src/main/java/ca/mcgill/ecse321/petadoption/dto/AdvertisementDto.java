package ca.mcgill.ecse321.petadoption.dto;

import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Application;
import ca.mcgill.ecse321.petadoption.model.Image;

import java.sql.Date;
import java.util.Set;

public class AdvertisementDto {
    private Date datePosted;
    private String advertisementId;
    private boolean isExpired;
    private Set<Application> applications;
    private AppUser postedBy;
    private String petName;
    private Integer petAge;
    private String petDescription;
    private Set<Image> petImages;

    public AdvertisementDto() {
    }

    public AdvertisementDto(Date datePosted, String advertisementId, boolean isExpired,
                            Set<Application> applications, AppUser postedBy, String petName, Integer petAge,
                            String petDescription, Set<Image> petImages) {
        this.datePosted = datePosted;
        this.advertisementId = advertisementId;
        this.isExpired = isExpired;
        this.applications = applications;
        this.postedBy = postedBy;
        this.petName = petName;
        this.petAge = petAge;
        this.petDescription = petDescription;
        this.petImages = petImages;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public String getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public AppUser getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(AppUser postedBy) {
        this.postedBy = postedBy;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Integer getPetAge() {
        return petAge;
    }

    public void setPetAge(Integer petAge) {
        this.petAge = petAge;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public Set<Image> getPetImages() {
        return petImages;
    }

    public void setPetImages(Set<Image> petImages) {
        this.petImages = petImages;
    }
}
