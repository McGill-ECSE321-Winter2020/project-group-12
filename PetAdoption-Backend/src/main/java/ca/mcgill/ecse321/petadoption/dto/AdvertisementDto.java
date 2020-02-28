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
    private String userEmail;
    private Set<Application> applications;
    private Set<Image> petImages;

    public AdvertisementDto() {
    }

    public AdvertisementDto(String userEmail, Date datePosted, String advertisementId, boolean isExpired,
                            String petName, Integer petAge, String petDescription, Sex petSex, Species petSpecies,
                            Set<Application> applications, Set<Image> petImages) {
        this.datePosted = datePosted;
        this.advertisementId = advertisementId;
        this.isExpired = isExpired; //#SYNERGY
        this.petName = petName;
        this.petAge = petAge;
        this.petDescription = petDescription;
        this.petSex = petSex;
        this.petSpecies = petSpecies;
        this.userEmail = userEmail;
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

    public Set<Application> getApplications() {
        return applications;
    }

    public String getUserEmail() {
        return userEmail;
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

    public Set<Image> getPetImages() {
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

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public void setPetImages(Set<Image> petImages) {
        this.petImages = petImages;
    }
}
