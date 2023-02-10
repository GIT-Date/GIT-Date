package com.GITDate.GITDate.controllers;

import com.GITDate.GITDate.models.AppUser;
import com.GITDate.GITDate.repositories.AppUserRepository;
import com.GITDate.GITDate.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class LikesController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;


    @GetMapping("/likes/{id}")
    public String getOneAppUser(@PathVariable Long id, Model m, Principal p) {
        AppUser authenticateUser = appUserRepository.findByUsername(p.getName());
        String authUserName = authenticateUser.getUsername();
        m.addAttribute("authUserName", authUserName);
        AppUser viewUser = appUserRepository.findById(id).orElseThrow();
        Long viewUserId = viewUser.getId();
        String viewUserName = viewUser.getUsername();
        String viewUserFirstName = viewUser.getFirstName();
        Object[] userILike = viewUser.getUsersILike().toArray();
        m.addAttribute("viewUserFirstName", viewUserFirstName);
        m.addAttribute("viewUserName", viewUserName);
        m.addAttribute("viewUserId", viewUserId);
        m.addAttribute("usersILike", userILike);
        m.addAttribute("usersWhoLikeMe", viewUser.getUsersWhoLikeMe());

        return "likes.html";
    }

    @GetMapping("/likes")
    public String getOneAppUser(Model m, Principal p) {
        AppUser authenticateUser = appUserRepository.findByUsername(p.getName());
        String authUserName = authenticateUser.getUsername();
        m.addAttribute("authUserName", authUserName);
        Long viewUserId = authenticateUser.getId();
        String viewUserName = authenticateUser.getUsername();
        String viewUserFirstName = authenticateUser.getFirstName();
        Object[] userILike = authenticateUser.getUsersILike().toArray();
        m.addAttribute("viewUserFirstName", viewUserFirstName);
        m.addAttribute("viewUserName", viewUserName);
        m.addAttribute("viewUserId", viewUserId);
        m.addAttribute("usersILike", userILike);
        m.addAttribute("usersWhoLikeMe", authenticateUser.getUsersWhoLikeMe());

        return "likes.html";
    }




    @PutMapping("/likes/{id}")
    public RedirectView likedUser(Principal p, Model m, @PathVariable Long id) throws IllegalAccessException {
        AppUser usersILike = appUserRepository.findById(id).orElseThrow(() -> new RuntimeException("Error Reading User From The Database with ID of: " + id));
        AppUser browsingUser = appUserRepository.findByUsername(p.getName());
        if (browsingUser.getUsername().equals(usersILike.getUsername())) {
            throw new IllegalAccessException("Don't Like Yourself!");
        }

        browsingUser.getUsersILike().add(usersILike);


        appUserRepository.save(browsingUser);

        return new RedirectView("/likes/" + browsingUser.getId());
    }
    public void autoAuthWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException se) {
            se.printStackTrace();
        }
    }
}
