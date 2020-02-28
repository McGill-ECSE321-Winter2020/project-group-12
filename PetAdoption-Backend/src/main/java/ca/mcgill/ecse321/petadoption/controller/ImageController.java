package ca.mcgill.ecse321.petadoption.controller;

import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dto.AdvertisementDto;
import ca.mcgill.ecse321.petadoption.dto.ImageDto;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.Image;
import ca.mcgill.ecse321.petadoption.service.ImageService;
import ca.mcgill.ecse321.petadoption.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ImageController {


    @Autowired
    AdvertisementService advertisementService;

    @Autowired
    ImageService imageService;

    // Changed method to public so that method can be called by other controller classes that need it
    // Method made static so method can be used without the need of an instance of the ImageDTO Class - Check
    public static ImageDto convertToDto(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("There is no such Image!");
        }
        return new ImageDto(image.getName(), image.getLink(), image.getImageId(), image.getAdvertisement().getAdvertisementId());
    }

    //TODO: Test POST mapping for Image after implementing POST method for Advertisement
    @PostMapping(value = {"/image/create", "/image/create/"})
    public ImageDto createImage(@RequestBody ImageDto imageDto) throws IllegalArgumentException {
        Image image = imageService.createImage(imageDto.getAdvertisementId(), imageDto.getLink(), imageDto.getImageId(), imageDto.getImageId());
        return convertToDto(image);
    }

    @PutMapping(value = {"/image/updateName", "/image/updateName/"})
    public ImageDto updateImageName(@RequestParam("imageID") String imageID, @RequestParam("imageName") String imageName) {
        Image image = imageService.updateImageName(imageID, imageName);
        return convertToDto(image);
    }

    @DeleteMapping(value = {"/image/delete", "/image/delete/"})
    public boolean deleteImage(@RequestParam("imageID") String imageID) {
        boolean returnStatement = imageService.deleteImage(imageID);
        return returnStatement;
    }

    @GetMapping(value = {"/images", "/images/"})
    public List<ImageDto> getAllImages() {
        List<ImageDto> imageDtoList = new ArrayList<>();
        for (Image image : imageService.getAllImages()) {
            imageDtoList.add(convertToDto(image));
        }
        return imageDtoList;
    }

    @GetMapping(value = {"/{advertisementID}/images", "/{advertisementID}/images/"})
    public List<ImageDto> getImagesByAdvertisement(@PathVariable("advertisementID") String advertisementID) {
        try {
            Advertisement ad = advertisementService.getAdvertisementByID(advertisementID);
            return createImageDtosForAdvertisement(ad);
            // Converting HashSet of Advertisements to ArrayList of Advertisements
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private List<ImageDto> createImageDtosForAdvertisement(Advertisement ad) {
        List<Image> imagesForAdvertisement = imageService.getAllImagesOfAdvertisement(ad);
        List<ImageDto> imageDtos = new ArrayList<>();
        for (Image image : imagesForAdvertisement){
            imageDtos.add(convertToDto(image));
        }
        return imageDtos;
    }

}


