package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser {
    private Set<Donation> donations;
    private String name;
    private String email;
    private String password;
    private String biography;
    private String homeDescription;
    private Integer age;
    private boolean isAdmin;
    private Set<Advertisement> advertisements;
    private Set<Application> applications;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToMany(mappedBy = "donor", fetch = FetchType.EAGER)
    public Set<Donation> getDonations() {
        return this.donations;
    }

    public void setDonations(Set<Donation> donations) {
        this.donations = donations;
    }

    public void addDonation(Donation donation){
        if(this.donations == null){
            this.donations = new HashSet<Donation>();
        }
        this.donations.add(donation);
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

    @Id
    public String getEmail() {
        return this.email;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String getPassword() {
        return this.password;
    }

    public void setBiography(String value) {
        this.biography = value;
    }

    public String getBiography() {
        return this.biography;
    }

    public void setHomeDescription(String value) {
        this.homeDescription = value;
    }

    public String getHomeDescription() {
        return this.homeDescription;
    }

    public void setAge(Integer value) {
        this.age = value;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setSex(Sex value) {
        this.sex = value;
    }

    public Sex getSex() {
        return this.sex;
    }

    public void setIsAdmin(boolean value) {
        this.isAdmin = value;
    }

    public boolean isIsAdmin() {
        return this.isAdmin;
    }

    @OneToMany(mappedBy = "postedBy", fetch = FetchType.EAGER)
    public Set<Advertisement> getAdvertisements() {
        return this.advertisements;
    }

    public void setAdvertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public void addAdvertisement(Advertisement advertisement){
        if(this.advertisements == null){
            this.advertisements = new HashSet<Advertisement>();
        }
        this.advertisements.add(advertisement);
    }

    @OneToMany(mappedBy = "applicant", fetch = FetchType.EAGER)
    public Set<Application> getApplications() {
        return this.applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public void addApplication(Application application){
        if(this.applications == null){
            this.applications = new HashSet<Application>();
        }
        this.applications.add(application);
    }
}
