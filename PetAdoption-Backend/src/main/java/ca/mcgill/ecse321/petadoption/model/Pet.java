package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Pet{
   private String name;

public void setName(String value) {
    this.name = value;
}
public String getName() {
    return this.name;
}
private Integer age;

public void setAge(Integer value) {
    this.age = value;
}
public Integer getAge() {
    return this.age;
}
private String description;

public void setDescription(String value) {
    this.description = value;
}
public String getDescription() {
    return this.description;
}
private Sex sex;

public void setSex(Sex value) {
    this.sex = value;
}
public Sex getSex() {
    return this.sex;
}
private Species species;

public void setSpecies(Species value) {
    this.species = value;
}
public Species getSpecies() {
    return this.species;
}
private Set<Image> images;

@OneToMany(mappedBy="pet" )
public Set<Image> getImages() {
   return this.images;
}

public void setImages(Set<Image> imagess) {
   this.images = imagess;
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
   private Advertisement advertisement;
   
   @OneToOne(optional=false)
   public Advertisement getAdvertisement() {
      return this.advertisement;
   }
   
   public void setAdvertisement(Advertisement advertisement) {
      this.advertisement = advertisement;
   }
   
   }
