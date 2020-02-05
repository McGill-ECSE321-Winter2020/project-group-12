package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class AppUser{
private Set<Donation> donations;
   
   @OneToMany(mappedBy="donor" )
   public Set<Donation> getDonations() {
      return this.donations;
   }
   
   public void setDonations(Set<Donation> donationss) {
      this.donations = donationss;
   }
   
   private String name;

public void setName(String value) {
    this.name = value;
}
public String getName() {
    return this.name;
}
private String email;

public void setEmail(String value) {
    this.email = value;
}
@Id
@GeneratedValue
public String getEmail() {
    return this.email;
}
private String password;

public void setPassword(String value) {
    this.password = value;
}
public String getPassword() {
    return this.password;
}
private String biography;

public void setBiography(String value) {
    this.biography = value;
}
public String getBiography() {
    return this.biography;
}
private String homeDescription;

public void setHomeDescription(String value) {
    this.homeDescription = value;
}
public String getHomeDescription() {
    return this.homeDescription;
}
private Integer age;

public void setAge(Integer value) {
    this.age = value;
}
public Integer getAge() {
    return this.age;
}
private Sex sex;

public void setSex(Sex value) {
    this.sex = value;
}
public Sex getSex() {
    return this.sex;
}
private boolean isAdmin;

public void setIsAdmin(boolean value) {
    this.isAdmin = value;
}
public boolean isIsAdmin() {
    return this.isAdmin;
}
   private Set<Advertisement> advertisements;
   
   @OneToMany(mappedBy="postedBy" )
   public Set<Advertisement> getAdvertisements() {
      return this.advertisements;
   }
   
   public void setAdvertisements(Set<Advertisement> advertisementss) {
      this.advertisements = advertisementss;
   }
   
   private Set<Application> applications;
   
   @OneToMany(mappedBy="applicant" )
   public Set<Application> getApplications() {
      return this.applications;
   }
   
   public void setApplications(Set<Application> applicationss) {
      this.applications = applicationss;
   }
   
   }
