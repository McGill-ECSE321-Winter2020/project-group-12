package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import ca.mcgill.ecse321.petadoption.model.java.sql.Date;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

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
   
   private Pet pet;
   
   @OneToOne(mappedBy="advertisement" , cascade={CascadeType.ALL}, optional=false)
   public Pet getPet() {
      return this.pet;
   }
   
   public void setPet(Pet pet) {
      this.pet = pet;
   }
   
   }
