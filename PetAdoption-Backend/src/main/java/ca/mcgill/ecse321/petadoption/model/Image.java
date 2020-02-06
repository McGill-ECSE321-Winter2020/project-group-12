package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue
    public Integer getId() {
        return this.id;
    }

    private Advertisement advertisement;

    @ManyToOne(optional=false)
    public Advertisement getAdvertisement() {
      return this.advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
      this.advertisement = advertisement;
    }

}
