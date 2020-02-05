package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public abstract class UserRole{
   private User user;
   
   @OneToOne(optional=false)
   public User getUser() {
      return this.user;
   }
   
   public void setUser(User user) {
      this.user = user;
   }
   
   private String password;

public void setPassword(String value) {
    this.password = value;
}
public String getPassword() {
    return this.password;
}
private Integer id;

public void setId(Integer value) {
    this.id = value;
}
@Id
public Integer getId() {
    return this.id;
}
   private Set<Advertisement> advertisements;
   
   @OneToMany(mappedBy="postedBy" )
   public Set<Advertisement> getAdvertisements() {
      return this.advertisements;
   }
   
   public void setAdvertisements(Set<Advertisement> advertisementss) {
      this.advertisements = advertisementss;
   }
   
   }
