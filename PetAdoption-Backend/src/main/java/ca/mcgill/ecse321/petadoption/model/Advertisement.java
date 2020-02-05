package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import ca.mcgill.ecse321.petadoption.model.java.sql.Date;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;

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
   private UserRole postedBy;
   
   @ManyToOne(optional=false)
   public UserRole getPostedBy() {
      return this.postedBy;
   }
   
   public void setPostedBy(UserRole postedBy) {
      this.postedBy = postedBy;
   }
   
   private Set<Application> applications;
   
   @OneToMany(mappedBy="advertisement" )
   public Set<Application> getApplications() {
      return this.applications;
   }
   
   public void setApplications(Set<Application> applicationss) {
      this.applications = applicationss;
   }
   
   }
