package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Id;

@Entity
public class User{
   private UserRole userRole;
   
   @OneToOne(mappedBy="user" , optional=false)
   public UserRole getUserRole() {
      return this.userRole;
   }
   
   public void setUserRole(UserRole userRole) {
      this.userRole = userRole;
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
public String getEmail() {
    return this.email;
}
}
