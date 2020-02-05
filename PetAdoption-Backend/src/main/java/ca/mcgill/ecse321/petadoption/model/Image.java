package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Image{
   private String name;

public void setName(String value) {
    this.name = value;
}
public String getName() {
    return this.name;
}
private String link;

public void setLink(String value) {
    this.link = value;
}
public String getLink() {
    return this.link;
}
private Integer id;

public void setId(Integer value) {
    this.id = value;
}
@Id
@GenerateValue
public Integer getId() {
    return this.id;
}
   private Pet pet;
   
   @ManyToOne(optional=false)
   public Pet getPet() {
      return this.pet;
   }
   
   public void setPet(Pet pet) {
      this.pet = pet;
   }
   
   }
