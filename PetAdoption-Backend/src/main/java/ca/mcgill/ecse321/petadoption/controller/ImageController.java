package ca.mcgill.ecse321.petadoption.controller;

import ca.mcgill.ecse321.petadoption.dto.ImageDto;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Image;
import ca.mcgill.ecse321.petadoption.service.AdvertisementService;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
import ca.mcgill.ecse321.petadoption.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ImageController {
    @Autowired
    AdvertisementService advertisementService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    ImageService imageService;

    // Changed method to public so that method can be called by other controller classes that need it
    // Method made static so method can be used without the need of an instance of the ImageDTO Class - Check
    public static ImageDto convertToDto(Image image) {
        return new ImageDto(image.getName(), image.getLink(), image.getImageId(), image.getAdvertisement().getAdvertisementId());
    }

    //TODO: Test POST mapping for Image after implementing POST method for Advertisement
    @PostMapping(value = {"/image/create", "/image/create/"})
    public ImageDto createImage(@RequestBody ImageDto imageDto, @RequestHeader String jwt) throws IllegalArgumentException {
        AppUser requester = appUserService.getAppUserByJwt(jwt); // making sure user is logged in
        Image image = imageService.createImage(imageDto.getAdvertisementId(), imageDto.getName(), imageDto.getLink(), requester.getEmail());
        return convertToDto(image);
    }

    @DeleteMapping(value = {"/image/delete", "/image/delete/"})
    public boolean deleteImage(@RequestParam("imageID") String imageID, @RequestHeader String jwt) {
        AppUser requester = appUserService.getAppUserByJwt(jwt); // making sure user is logged in
        boolean returnStatement = imageService.deleteImage(imageID, requester.getEmail());
        return returnStatement;
    }

    @GetMapping(value = {"/image/{imageId}", "/image/{imageId}/"})
    public ImageDto getImageById(@PathVariable("imageId") String imageId, @RequestHeader String jwt) {
        appUserService.getAppUserByJwt(jwt);
        return convertToDto(imageService.getImageByID(imageId));
    }

    @GetMapping(value = {"/{advertisementID}/images", "/{advertisementID}/images/"})
    public List<ImageDto> getImagesByAdvertisement(@PathVariable("advertisementID") String advertisementID, @RequestHeader String jwt) {
        appUserService.getAppUserByJwt(jwt);
        Advertisement ad = advertisementService.getAdvertisementByID(advertisementID);
        return createImageDtosForAdvertisement(ad);
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


