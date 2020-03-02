package ca.mcgill.ecse321.petadoption.controller;

import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AppUserController {
    @Autowired
    private AppUserService service;

    @PostMapping(value ={"/register","/register/" })
    public AppUserDto createAppUser(@RequestBody AppUserDto appUser)throws RestClientException {
        AppUser user = null;
        try{
            user = service.createAppUser(appUser.getName(), appUser.getEmail(), appUser.getPassword(), appUser.getBiography(), appUser.getHomeDescription(),
                    appUser.getAge(), appUser.isIsAdmin(), appUser.getSex());
        }catch (IllegalArgumentException e){
            throw new RestClientException(e.getMessage());
        }
        return convertToDto(user);
    }

    @GetMapping(value ={"/getAllUsers", "/getAllUsers/"})
    public List<AppUserDto> getAllAppUsers(){
        List<AppUserDto> lst = new ArrayList<AppUserDto>();
        for( AppUser user: service.getAllAppUsers() ){
                lst.add(convertToDto(user));
        }
        return lst;
    }

    @PutMapping(value = {"/updateUser","/updateUser"})
    public AppUserDto updateAppUser(@RequestBody AppUserDto appUser){
        AppUser updatedUser = service.updateAppUser(appUser.getName(), appUser.getEmail(),"password", appUser.getBiography(), appUser.getHomeDescription(),
                appUser.getAge(), appUser.isIsAdmin(), appUser.getSex());
        return convertToDto(updatedUser);
    }

    @GetMapping(value = {"/getUser/{email}","/getUser/{email}/"})
    public AppUserDto getAppUser(@PathVariable("email") String email) {
        AppUser user = service.getAppUserByEmail(email);
        return convertToDto(user);
    }

    @GetMapping(value = {"/login/{email}/{password}","/getUser/{email}/{password}"})
    public boolean getAppUser(@PathVariable("email") String email, @PathVariable("password") String password) {
        return service.checkLoginParam(email, password);
    }

    @DeleteMapping(value = {"/delete/user/{email}", "/delete/user/{email}/"})
    public void deleteAppUser(@PathVariable("email") String email) {
        service.deleteAppUser(email);
    }

    private AppUserDto convertToDto(AppUser appUser){
        return new AppUserDto(appUser.getName(), appUser.getEmail(), "", appUser.getBiography(), appUser.getHomeDescription(),
                              appUser.getAge(), appUser.isIsAdmin(), appUser.getSex());
    }

}
