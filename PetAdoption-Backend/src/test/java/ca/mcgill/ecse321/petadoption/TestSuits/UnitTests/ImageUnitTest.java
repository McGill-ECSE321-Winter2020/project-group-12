package ca.mcgill.ecse321.petadoption.TestSuits.UnitTests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.time.LocalDate;

import ca.mcgill.ecse321.petadoption.dao.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.petadoption.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ImageUnitTest {

    @Test
    public void createImage(){
        
    }
}
