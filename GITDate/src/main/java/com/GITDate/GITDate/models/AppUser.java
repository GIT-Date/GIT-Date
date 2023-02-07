package com.GITDate.GITDate.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Integer age;

    private String bio;

    private String interests;

    private String gender;

    private String image;

    protected AppUser() {
    }
    @ManyToMany
    @JoinTable(
            name = "likes_to_liked",
            joinColumns = {@JoinColumn(name = "userWhoIsLiking")},
            inverseJoinColumns = {@JoinColumn(name = "LikedUser")}
    )
    Set<AppUser> usersILike = new HashSet<>();

    public Set<AppUser> getUsersILike() {
        return usersILike;
    }

    public Set<AppUser> getUsersWhoLikeMe() {
        return usersWhoLikeMe;
    }

    @ManyToMany(mappedBy = "usersILike")
    Set<AppUser> usersWhoLikeMe = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    Set<Post> listOfPost = new HashSet<>();

    public Set<Post> getListOfPost() {
        return listOfPost;
    }
    public void setListOfPost(Set<Post> listOfPost) {
        this.listOfPost = listOfPost;
    }

    public AppUser(String username, String password, String firstName, String lastName, Integer age, String bio, String interests, String gender, String image) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.bio = bio;
        this.interests = interests;
        this.gender = gender;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void getUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
