package ca.mcgill.ecse321.petadoption.controller;

import ca.mcgill.ecse321.petadoption.dto.AppUserDto;
import ca.mcgill.ecse321.petadoption.model.AppUser;
import ca.mcgill.ecse321.petadoption.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AppUserController {
    @Autowired
    private AppUserService service;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping(value ={"/register","/register/" })
    public AppUserDto createAppUser(@RequestBody AppUserDto appUser)throws RestClientException {
        AppUser user = null;
        user = service.createAppUser(appUser.getName(), appUser.getEmail(), appUser.getPassword(), appUser.getBiography(), appUser.getHomeDescription(),
                    appUser.getAge(), appUser.isIsAdmin(), appUser.getSex());
        return convertToDto(user);
    }

    @GetMapping(value ={"/getAllUsers", "/getAllUsers/"})
    public List<AppUserDto> getAllAppUsers(@RequestHeader String jwt){
        AppUser requester = service.getAppUserByJwt(jwt);
        if(requester.isIsAdmin()) {
            List<AppUserDto> lst = new ArrayList<AppUserDto>();
            for(AppUser user: service.getAllAppUsers() ){
                lst.add(convertToDto(user));
            }
            return lst;
        }
        throw new IllegalArgumentException("You do not have the required authorization to perform this action!");
    }

    @PutMapping(value = {"/updateUser","/updateUser"})
    public AppUserDto updateAppUser(@RequestBody AppUserDto appUser, @RequestHeader String jwt){
        AppUser requester = service.getAppUserByJwt(jwt);
        if(requester.getEmail() == appUser.getEmail()) {
            AppUser updatedUser = service.updateAppUser(appUser.getName(), appUser.getEmail(),"password", appUser.getBiography(), appUser.getHomeDescription(),
                    appUser.getAge(), appUser.isIsAdmin(), appUser.getSex(), appUser.getJwt());
            return convertToDto(updatedUser);
        }
        throw new IllegalArgumentException("You do not have the required authorization to perform this action!");
    }

    @GetMapping(value = {"/getUser/{email}","/getUser/{email}/"})
    public AppUserDto getAppUser(@PathVariable("email") String email) {
        AppUser user = service.getAppUserByEmail(email);
        return convertToDto(user);
    }

    @PostMapping(value = {"/login","/login/"})
    public ResponseEntity<?> login(@RequestBody AppUserDto appUser) {
        service.login(appUser.getEmail(), appUser.getPassword()); // if able to find such credentials in database
        // create a JWT
        final String jwt = jwtProvider.createToken(appUser.getEmail());
        AppUser user = service.getAppUserByEmail(appUser.getEmail());
        service.updateAppUser(user.getName(), user.getEmail(), user.getPassword(), user.getBiography(),
                user.getHomeDescription(), user.getAge(), user.isIsAdmin(), user.getSex(), jwt);
        return ResponseEntity.ok(jwt); // return it in the response so the client can subsequently use it in every request made afterwards
    }

    @DeleteMapping(value = {"/logout", "/logout/"})
    public ResponseEntity<?> logout(@RequestHeader String token) {
        AppUser user = service.getAppUserByJwt(token);
        service.updateAppUser(user.getName(), user.getEmail(), user.getPassword(), user.getBiography(), user.getHomeDescription(), user.getAge(), user.isIsAdmin(), user.getSex(), null);
        // have to remove authentication from context holder
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/user/{email}", "/delete/user/{email}/"})
    public void deleteAppUser(@PathVariable("email") String email, @RequestHeader String jwt) {
        AppUser requester = service.getAppUserByJwt(jwt); // validating the jwt
        service.deleteAppUser(email);
    }

    private AppUserDto convertToDto(AppUser appUser){
        return new AppUserDto(appUser.getName(), appUser.getEmail(), "", appUser.getBiography(), appUser.getHomeDescription(),
                              appUser.getAge(), appUser.isIsAdmin(), appUser.getSex(), appUser.getJwt());
    }

}
