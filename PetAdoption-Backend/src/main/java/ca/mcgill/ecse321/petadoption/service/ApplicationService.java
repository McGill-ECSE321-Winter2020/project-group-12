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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
        Application app = new Application();
        Advertisement advertisement = advertisementRepository.findAdvertisementByAdvertisementId(advertisementId);
        AppUser aUser = appUserRepository.findAppUserByEmail(appUserEmail);
        String error = "";

        // Set<Application> apps = advertisement.getApplications();
        //  error = error + "You already applied for this";
        // apps.add(app);
        //if (apps == null) error = error + "You already applied for this";
        // for (Application a : apps) {
        //if (a.getApplicant().getEmail().equals(appUserEmail)) {
        //  error = error + "You already applied for this";
        //}
        //    int abdul = 3;
        //   }


////        String id = app.getApplicationId();
////        Application old_application = applicationRepository.findApplicationByApplicationId(id);
////
////        if(old_application != null){
////            throw new IllegalArgumentException("The application id: " + id + " already exists");
////        }

        if (advertisementId == null || appUserEmail == null) {
            error = error + "An Application must have an Advertisement and a AppUser ";
        } else if (advertisement.isIsExpired()) {
            error = error + "The Advertisement has expired";
        }

        if (dateOfSubmission == null) {
            error = "dateOfSubmission can not be empty! ";
        }
        if (note == null || note.trim().length() == 0) {
            error = error + "note cannot be empty ";
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

        applicationRepository.save(app);
        return app;
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
        Application a = applicationRepository.findApplicationByApplicationId(id);
        return a;
    }

    /**
     * Returns all Applications in the database.
     *
     * @return List of Application objects
     */
    @Transactional
    public List<Application> getAllApplications() {
        return new ArrayList<>((Collection<? extends Application>)applicationRepository.findAll());
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
    public Application updateApplicationStatus(Application app, Status status) {
        app.setStatus(status);
        applicationRepository.save(app);
        return app;
    }


}
