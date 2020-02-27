package ca.mcgill.ecse321.petadoption.dto;

import ca.mcgill.ecse321.petadoption.model.*;

import java.sql.Date;
import java.util.Set;

public class AdvertisementDto {
    private Date datePosted;
    private String advertisementId;
    private boolean isExpired;
    private String petName;
    private Integer petAge;
    private String petDescription;
    private Sex petSex;
    private Species petSpecies;
    private Set<ApplicationDto> applications;
    private AppUserDto postedBy;


    private Set<ImageDto> petImages;

    public AdvertisementDto() {
    }

    public AdvertisementDto(AppUserDto postedBy, String advertisementId, Date datePosted,  boolean isExpired,
                            String petName, Integer petAge, String petDescription, Set<ApplicationDto> applications,
                             Set<ImageDto> petImages) {
        this.datePosted = datePosted;
        this.advertisementId = advertisementId;
        this.isExpired = isExpired; //#SYNERGY
        this.applications = applications;
        this.postedBy = postedBy;
        this.petName = petName;
        this.petAge = petAge;
        this.petDescription = petDescription;
        this.petImages = petImages;
    }

    public Sex getPetSex() {
        return petSex;
    }

    public Species getPetSpecies() {
        return petSpecies;
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

    public Set<ApplicationDto> getApplications() {
        return applications;
    }

    public AppUserDto getPostedBy() {
        return postedBy;
    }

    public String getPetName() {
        return petName;
    }

    public Integer getPetAge() {
        return petAge;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public Set<ImageDto> getPetImages() {
        return petImages;
    }
}
