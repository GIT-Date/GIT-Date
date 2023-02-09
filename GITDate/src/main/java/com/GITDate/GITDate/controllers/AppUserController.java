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
import java.text.ParseException;


@Controller
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup.html";
    }



    @GetMapping("/myProfile")
    public String getMyProfile(Model m, Principal p) {
        AppUser authenticatedUser = appUserRepository.findByUsername(p.getName());
        m.addAttribute("authUser", authenticatedUser);
        return "myProfile.html";
    }

    @GetMapping("/")
    public String getHome(Model m, Principal p) {
        if (p != null) {
            String username = p.getName();
            AppUser dbUser = appUserRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("FirstName", dbUser.getFirstName());
        }
        try {

        } catch (RuntimeException runtimeException) {
            throw new RuntimeException("OOOOOOOOPPPPPSSSS!");
        }
        return "index.html";
    }

    @PostMapping("/signup")
    public RedirectView createAppUser(String username, String password, String firstName, String lastName,
                                      Integer age, String bio, String interests, String gender, String image) throws ParseException {
        String hashedPW = passwordEncoder.encode(password);
        AppUser newUser = new AppUser(username, hashedPW, firstName, lastName, age, bio,
                interests.toLowerCase(), gender.toLowerCase(), image);
        appUserRepository.save(newUser);
        autoAuthWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    public void autoAuthWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException se) {
            se.printStackTrace();
        }
    }
}
