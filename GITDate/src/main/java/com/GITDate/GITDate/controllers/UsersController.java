package com.GITDate.GITDate.controllers;

import com.GITDate.GITDate.models.AppUser;
import com.GITDate.GITDate.repositories.AppUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UsersController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/user/{id}")
    public String getOneAppUser(@PathVariable Long id, Model m, Principal p){
        AppUser authenticatedUser = appUserRepository.findByUsername(p.getName());
        m.addAttribute("authUser", authenticatedUser);
        // Find user by ID from DB
        AppUser viewUser = appUserRepository.findById(id).orElseThrow();
        // Attach the user to the model
        m.addAttribute("viewUser", viewUser);
        // Add usersIFollow and usersWhoFollowMe to the HTML page
        m.addAttribute("usersILike", viewUser.getUsersILike());
        m.addAttribute("usersWhoLikeMe", viewUser.getUsersWhoLikeMe());

        return "user-info.html";
    }
    @PutMapping("/user/{id}")
    public RedirectView editAppUserInfo(@PathVariable Long id, String username, Principal p, RedirectAttributes redir) throws ServletException {
        // Find user to edit
        AppUser userToBeEdited = appUserRepository.findById(id).orElseThrow();
        if (p.getName().equals(userToBeEdited.getUsername())) {
            // Update user
            userToBeEdited.setUsername(username);
            // Save edited user back to db
            appUserRepository.save(userToBeEdited);
            request.logout();
            autoAuthWithHttpServletRequest(username, userToBeEdited.getPassword());
        } else {
            redir.addFlashAttribute("errorMessage", "Can't edit another user's info!");
        }
        return new RedirectView("/user/" + id);

    }

    public void autoAuthWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException se) {
            se.printStackTrace();
        }
    }
}
