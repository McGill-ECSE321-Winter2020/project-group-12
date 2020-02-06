package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class Advertisement{
   private Date datePosted;
   private Long id;
   private boolean isExpired;
   private Set<Application> applications;
   private AppUser postedBy;
   private String pet_name;
   private Integer pet_age;
   private String pet_description;
   private Set<Image> pet_images;

   @Enumerated(EnumType.STRING)
   private Species pet_species;

   @Enumerated(EnumType.STRING)
   private Sex pet_sex;

   public void setDatePosted(Date value) {
       this.datePosted = value;
   }
   public Date getDatePosted() {
       return this.datePosted;
   }

   public void setId(Long value) {
       this.id = value;
   }
   @Id
   @GeneratedValue
   public Long getId() {
       return this.id;
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



   public void setName(String value) {
      this.pet_name = value;
   }
   public String getName() {
      return this.pet_name;
   }

   public void setAge(Integer value) {
      this.pet_age = value;
   }
   public Integer getAge() {
      return this.pet_age;
   }

   public void setDescription(String value) {
      this.pet_description = value;
   }
   public String getDescription() {
      return this.pet_description;
   }


   public void setSex(Sex value) {
      this.pet_sex = value;
   }
   public Sex getSex() {
      return this.pet_sex;
   }


   public void setSpecies(Species value) {
      this.pet_species = value;
   }
   public Species getSpecies() {
      return this.pet_species;
   }

   @OneToMany(mappedBy="advertisement")
   public Set<Image> getImages() {
      return this.pet_images;
   }

   public void setImages(Set<Image> images) {
      this.pet_images = images;
   }

}
