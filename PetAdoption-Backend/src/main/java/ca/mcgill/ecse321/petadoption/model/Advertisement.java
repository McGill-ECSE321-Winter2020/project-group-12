package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class Advertisement{
   private Date datePosted;
   private Long advertisementId;
   private boolean isExpired;
   private Set<Application> applications;
   private AppUser postedBy;
   private String petName;
   private Integer petAge;
   private String petDescription;
   private Set<Image> petImages;

   @Enumerated(EnumType.STRING)
   private Species petSpecies;

   @Enumerated(EnumType.STRING)
   private Sex petSex;

   public void setDatePosted(Date value) {
       this.datePosted = value;
   }
   public Date getDatePosted() {
       return this.datePosted;
   }

   public void setAdvertisementId(Long value) {
       this.advertisementId = value;
   }
   @Id
   @GeneratedValue
   public Long getAdvertisementId() {
       return this.advertisementId;
   }

   public void setIsExpired(boolean value) {
       this.isExpired = value;
   }
   public boolean isIsExpired() {
       return this.isExpired;
   }

   @OneToMany(mappedBy="advertisement" )
   public Set<Application> getApplications() {
      return this.applications;
   }

   public void setApplications(Set<Application> applications) {
      this.applications = applications;
   }


   @ManyToOne(optional=false)
   public AppUser getPostedBy() {
      return this.postedBy;
   }

   public void setPostedBy(AppUser postedBy) {
      this.postedBy = postedBy;
   }



   public void setPetName(String value) {
      this.petName = value;
   }
   public String getPetName() {
      return this.petName;
   }

   public void setPetAge(Integer value) {
      this.petAge = value;
   }
   public Integer getPetAge() {
      return this.petAge;
   }

   public void setPetDescription(String value) {
      this.petDescription = value;
   }
   public String getPetDescription() {
      return this.petDescription;
   }


   public void setPetSex(Sex value) {
      this.petSex = value;
   }
   public Sex getPetSex() {
      return this.petSex;
   }


   public void setPetSpecies(Species value) {
      this.petSpecies = value;
   }
   public Species getPetSpecies() {
      return this.petSpecies;
   }

   @OneToMany(mappedBy="advertisement")
   public Set<Image> getPetImages() {
      return this.petImages;
   }

   public void setPetImages(Set<Image> images) {
      this.petImages = images;
   }

}
