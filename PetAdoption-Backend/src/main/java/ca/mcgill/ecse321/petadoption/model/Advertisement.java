package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class Advertisement{
   private Date datePosted;

   public void setDatePosted(Date value) {
       this.datePosted = value;
   }
   public Date getDatePosted() {
       return this.datePosted;
   }
   private Integer id;

   public void setId(Integer value) {
       this.id = value;
   }
   @Id
   @GeneratedValue
   public Integer getId() {
       return this.id;
   }
   private boolean isExpired;

   public void setIsExpired(boolean value) {
       this.isExpired = value;
   }
   public boolean isIsExpired() {
       return this.isExpired;
   }
   private Set<Application> applications;

   @OneToMany(mappedBy="advertisement" )
   public Set<Application> getApplications() {
      return this.applications;
   }

   public void setApplications(Set<Application> applicationss) {
      this.applications = applicationss;
   }

   private AppUser postedBy;

   @ManyToOne(optional=false)
   public AppUser getPostedBy() {
      return this.postedBy;
   }

   public void setPostedBy(AppUser postedBy) {
      this.postedBy = postedBy;
   }


   private String pet_name;

   public void setName(String value) {
      this.pet_name = value;
   }
   public String getName() {
      return this.pet_name;
   }
   private Integer pet_age;

   public void setAge(Integer value) {
      this.pet_age = value;
   }
   public Integer getAge() {
      return this.pet_age;
   }
   private String pet_description;

   public void setDescription(String value) {
      this.pet_description = value;
   }
   public String getDescription() {
      return this.pet_description;
   }

   @Enumerated(EnumType.STRING)
   private Sex pet_sex;

   public void setSex(Sex value) {
      this.pet_sex = value;
   }
   public Sex getSex() {
      return this.pet_sex;
   }

   @Enumerated(EnumType.STRING)
   private Species pet_species;

   public void setSpecies(Species value) {
      this.pet_species = value;
   }
   public Species getSpecies() {
      return this.pet_species;
   }
   private Set<Image> pet_images;

   @OneToMany(mappedBy="advertisement" )
   public Set<Image> getImages() {
      return this.pet_images;
   }

   public void setImages(Set<Image> images) {
      this.pet_images = images;
   }

}
