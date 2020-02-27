package ca.mcgill.ecse321.petadoption.TestSuits.UnitTests;

import ca.mcgill.ecse321.petadoption.service.PetAdoptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
public class AppUserUnitTest {


    @Mock
    private PetAdoptionService service;
    
    @Test
    public void createAppUser(){

    }


}
