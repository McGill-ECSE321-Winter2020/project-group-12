package ca.mcgill.ecse321.petadoption.dto;

import ca.mcgill.ecse321.petadoption.model.Sex;

public class AppUserDto {
    private String name;
    private String email;
    private String password;
    private String biography;
    private String homeDescription;
    private Integer age;
    private boolean isAdmin;
    private Sex sex;

    public AppUserDto(String name, String email,String password, String biography, String homeDescription, int age, boolean isAdmin,
                      Sex sex)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.biography = biography;
        this.homeDescription = homeDescription;
        this.age =age;
        this.isAdmin = isAdmin;
        this.sex = sex;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setBiography(String value) {
        this.biography = value;
    }

    public String getBiography() {
        return this.biography;
    }

    public String getHomeDescription() {
        return this.homeDescription;
    }

    public Integer getAge() {
        return this.age;
    }

    public Sex getSex() {
        return this.sex;
    }

    public boolean isIsAdmin() {
        return this.isAdmin;
    }

}
