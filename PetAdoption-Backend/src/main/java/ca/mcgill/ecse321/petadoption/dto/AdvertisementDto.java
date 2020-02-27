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
    private AppUserDto postedBy;
    private Set<ApplicationDto> applications;
    private Set<ImageDto> petImages;

    public AdvertisementDto() {
    }

    public AdvertisementDto(AppUserDto postedBy, Date datePosted, String advertisementId, boolean isExpired,
                            String petName, Integer petAge, String petDescription, Sex petSex, Species petSpecies,
                            Set<ApplicationDto> applications, Set<ImageDto> petImages) {
        this.datePosted = datePosted;
        this.advertisementId = advertisementId;
        this.isExpired = isExpired; //#SYNERGY
        this.petName = petName;
        this.petAge = petAge;
        this.petDescription = petDescription;
        this.petSex = petSex;
        this.petSpecies = petSpecies;
        this.postedBy = postedBy;
        this.applications = applications;
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

    public String getAdvertisementId() {
        return advertisementId;
    }

    public boolean isExpired() {
        return isExpired;
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

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetAge(Integer petAge) {
        this.petAge = petAge;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public void setPetSex(Sex petSex) {
        this.petSex = petSex;
    }

    public void setPetSpecies(Species petSpecies) {
        this.petSpecies = petSpecies;
    }

    public void setApplications(Set<ApplicationDto> applications) {
        this.applications = applications;
    }

    public void setPetImages(Set<ImageDto> petImages) {
        this.petImages = petImages;
    }
}
