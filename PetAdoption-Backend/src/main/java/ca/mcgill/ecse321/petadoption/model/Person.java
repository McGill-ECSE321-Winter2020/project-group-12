package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Person extends UserRole{
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
   private Set<Application> applications;
   
   @OneToMany(mappedBy="applicant" )
   public Set<Application> getApplications() {
      return this.applications;
   }
   
   public void setApplications(Set<Application> applicationss) {
      this.applications = applicationss;
   }
   
   private Set<Donation> donations;
   
   @OneToMany(mappedBy="donor" )
   public Set<Donation> getDonations() {
      return this.donations;
   }
   
   public void setDonations(Set<Donation> donationss) {
      this.donations = donationss;
   }
   
   }
