package ca.mcgill.ecse321.petadoption.dao;

import ca.mcgill.ecse321.petadoption.model.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, String>{

    AppUser findAppUserByEmail(String email);

    void deleteAppUserByEmail(String email);
}
