package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Image{
    private String name;
    private String link;
    private String imageId;
    private Advertisement advertisement;
//    private static long newId = 1;
//
//    public void setNewId(Long val){
//        newId++;
//        this. specificId= newId;
//    }

    public void setName(String value) {
        this.name = value;
    }
    public String getName() {
        return this.name;
    }

    public void setLink(String value) {
        this.link = value;
    }
    public String getLink() {
        return this.link;
    }

    public void setImageId(String value) {
        this.imageId = value;
    }

    public void setImageId() {
        this.imageId = UUID.randomUUID().toString();
    }
    @Id
    public String getImageId() {
        return this.imageId;
    }


    @ManyToOne(optional=false)
    public Advertisement getAdvertisement() {
      return this.advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
      this.advertisement = advertisement;
    }

}
