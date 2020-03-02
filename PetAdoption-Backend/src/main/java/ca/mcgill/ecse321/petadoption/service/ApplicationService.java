package ca.mcgill.ecse321.petadoption.service;

import ca.mcgill.ecse321.petadoption.dao.*;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.model.Application;
import ca.mcgill.ecse321.petadoption.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

@Service
public class ApplicationService {

    @Autowired(required = true)
    AppUserRepository appUserRepository;
    @Autowired(required = true)
    ApplicationRepository applicationRepository;
    @Autowired(required = true)
    AdvertisementRepository advertisementRepository;
    @Autowired(required = true)
    DonationRepository donationRepository;
    @Autowired(required = true)
    ImageRepository imageRepository;

    /**
     * Creates and adds a new Application object to the database.
     *
     * @param dateOfSubmission
     * @param note
     * @param status
     * @return Application object
     */
    @Transactional
    public Application createApplication(String advertisementId, String appUserEmail, Date dateOfSubmission, String note, Status status) {
        boolean bool = false;
        Application app = new Application();
        Advertisement advertisement = advertisementRepository.findAdvertisementByAdvertisementId(advertisementId);
        AppUser aUser = appUserRepository.findAppUserByEmail(appUserEmail);
        String error = "";
        System.out.println("ad : "+ advertisementId + "   user  " + appUserEmail);
        if (advertisementId == null || appUserEmail == null || advertisementId.trim().length() == 0 || appUserEmail.trim().length() == 0) {
            error = error + "An Application must have an Advertisement and a AppUser ";
        } else if (advertisement.isIsExpired()) {
            error = error + "The Advertisement has expired";
        } else if (advertisement != null) {
            AppUser owner = advertisement.getPostedBy();
            String ownerEmail = owner.getEmail();
            app.setApplicant(aUser);
            if (appUserEmail.equals(ownerEmail)) {
                error = error + "You cannot adopt your own pet!";
            }
            Application findDuplicate = applicationRepository.findApplicationByAdvertisement_AdvertisementIdAndApplicant_Email(advertisementId, appUserEmail);
            if (findDuplicate != null){
                throw new IllegalArgumentException("You already applied for this");
            }
            if (dateOfSubmission == null) {
                error = "dateOfSubmission can not be empty! ";
            } else if ((dateOfSubmission.compareTo(advertisement.getDatePosted())) < 0) {
                error = error + "Advertisement Date Must Be Prior or Equal To Application Date";
            }
            if (note == null || note.trim().length() == 0) {
                error = error + "note cannot be empty ";
            }
        }
        if (error.length() != 0) {
            throw new IllegalArgumentException(error);
        }
        app.setApplicationId();
        app.setApplicant(aUser);
        app.setAdvertisement(advertisement);
        app.setDateOfSubmission(dateOfSubmission);
        app.setNote(note);
        app.setStatus(status);
        app.setApplicant(aUser);
        advertisement.addApplication(app);
        return applicationRepository.save(app);
    }

    /**
     * Returns the Application with specified id from the database.
     *
     * @param id
     * @return Application object
     */
    @Transactional
    public Application getApplicationByID(String id) {
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("Application must have an ID");
        }
        Application application = applicationRepository.findApplicationByApplicationId(id);
        if(application == null){
            throw new IllegalArgumentException("This application does not exist.");
        }
        return application;
    }

    /**
     * Returns all Applications in the database.
     *
     * @return List of Application objects
     */
    @Transactional
    public List<Application> getAllApplications() {
        return new ArrayList<>((Collection<? extends Application>) applicationRepository.findAll());
    }

    /**
     * Returns all the Applications corresponding
     * @param id: The id of the advertisement.
     * @return A list of application for that advertisement
     */
    @Transactional
    public List<Application> getAllApplicationsForAdvertisement(String id){
        return new ArrayList<>((Collection<? extends Application>) applicationRepository.findApplicationByAdvertisement_AdvertisementId(id));
    }
    /////////////////////////////Application Delete Method////////////////////////////////////////

    /**
     * Deletes the Application with specified id from the database.
     *
     * @param id
     */
    @Transactional
    public void deleteApplication(String id) {
        applicationRepository.deleteApplicationByApplicationId(id);
    }


    //////////////////////////////Application update method////////////////////////////////////

    /**
     * Updates the status attribute of Application class in the database.
     *
     * @param status
     * @return Application
     */
    @Transactional
    public Application updateApplicationStatus(String applicationId, Status status) {
        Application app = getApplicationByID(applicationId);
        app.setStatus(status);
        return applicationRepository.save(app);
    }


}
