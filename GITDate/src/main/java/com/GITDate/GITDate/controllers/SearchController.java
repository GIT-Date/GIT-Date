package com.GITDate.GITDate.controllers;

import org.springframework.stereotype.Controller;
import com.GITDate.GITDate.models.AppUser;
import com.GITDate.GITDate.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SearchController {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    HttpServletRequest request;

//make get mapping to search

    @GetMapping("/search/results")
    public String getFilteredAppUser(Model m, Principal p, String key) {
        AppUser authenticateUser = appUserRepository.findByUsername(p.getName());
        String authUserName = authenticateUser.getUsername();
        m.addAttribute("authUserName", authUserName);
        List<AppUser> viewAllUsers = appUserRepository.findAll();
        List<AppUser> filteredUsers = new ArrayList<>();
        if (key != null);
            for (AppUser user: viewAllUsers){
                if (user.getGender().equals(key)){
                    filteredUsers.add(user);
                }
                //search through users, if gender matches key save to final list
                //if user gender matches key push to list, else filter to next user
            }

        m.addAttribute("viewAllUsers", filteredUsers);


        return "search";

    }

    @GetMapping("/search")
    public String getAllUsers(Model m, Principal p){

        AppUser authenticateUser = appUserRepository.findByUsername(p.getName());

        List<AppUser> viewAllUsers = appUserRepository.findAll();
        m.addAttribute("viewAllUsers", viewAllUsers);
        m.addAttribute("browsingUser", authenticateUser);
       
        return "/search";
    }
}