package ca.mcgill.ecse321.petadoption.dto;

import ca.mcgill.ecse321.petadoption.model.Sex;
import java.util.List;

public class AppUserDto {
    private List<DonationDto> donations;
    private String name;
    private String email;
    private String biography;
    private String homeDescription;
    private Integer age;
    private boolean isAdmin;
    private List<AdvertisementDto> advertisements;
    private List<ApplicationDto> applications;

    private Sex sex;

    public AppUserDto(){

    }
    public AppUserDto(String name, String email, String biography, String homeDescription, int age, boolean isAdmin,
                      Sex sex, List<DonationDto> donations, List<AdvertisementDto> advertisements, List<ApplicationDto> applications)
    {
        this.name = name;
        this.email = email;
        this.biography = biography;
        this.homeDescription = homeDescription;
        this.age =age;
        this.isAdmin = isAdmin;
        this.sex = sex;
        this.donations = donations;
        this.advertisements = advertisements;
        this.applications = applications;
    }

    public List<DonationDto> getDonations() {
        return this.donations;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getEmail() {
        return this.email;
    }

    public void setBiography(String value) {
        this.biography = value;
    }

    public String getBiography() {
        return this.biography;
    }

    public String getHomeDescription() {
        return this.homeDescription;
    }

    public Integer getAge() {
        return this.age;
    }

    public Sex getSex() {
        return this.sex;
    }

    public boolean isIsAdmin() {
        return this.isAdmin;
    }

    public List<AdvertisementDto> getAdvertisements() {
        return this.advertisements;
    }

    public List<ApplicationDto> getApplications() {
        return this.applications;
    }

}
