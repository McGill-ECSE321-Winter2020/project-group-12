package ca.mcgill.ecse321.petadoption.TestSuits.UnitTests;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.ApplicationRepository;
import ca.mcgill.ecse321.petadoption.model.Advertisement;
import ca.mcgill.ecse321.petadoption.model.Application;
import ca.mcgill.ecse321.petadoption.model.AppUser;

import ca.mcgill.ecse321.petadoption.service.ApplicationService;

@ExtendWith(MockitoExtension.class)
public class ApplicationUnitTest { //application test service
    @Mock
    private ApplicationRepository applicationDao;
    private AppUserRepository appUserDao;
    private AdvertisementRepository advertisementDao;
    @InjectMocks
    private ApplicationService service;

    private static final String USER_NAME = "User1";


//    @BeforeEach
//    public void setMockOutput() {
//        lenient().when(applicationDao.findApplicationByApplicationId(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
//            if(invocation.getArgument(0).equals(APPLICATION_ID)) {
//                Application app = new Application();
//                app.setApplicationId();
//                return app;
//            } else {
//                return null;
//            }
//        });
//    }
//    lenient().when(personDao.save(any(Person.class))).thenAnswer(returnParameterAsAnswer);
//    lenient().when(eventDao.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
//    lenient().when(registrationDao.save(any(Registration.class))).thenAnswer(returnParameterAsAnswer);
//}





}