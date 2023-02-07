package com.GITDate.GITDate.controllers;

import com.GITDate.GITDate.models.AppUser;
import com.GITDate.GITDate.models.Post;
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
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    HttpServletRequest request;



    @PostMapping("/myProfile")
    public RedirectView createPost(Principal p, String body, Model m) {
        if(p!=null){
            String username = p.getName();
            AppUser appUser = appUserRepository.findByUsername(username);
            m.addAttribute("addPost", appUser.getListOfPost());
            Date date = new Date();
            Post newPost = new Post(body, date, appUser);
            newPost.setCreatedBy(appUser);
            postRepository.save(newPost);

        }
        return new RedirectView("/myProfile");
    }

    public void autoAuthWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException se) {
            se.printStackTrace();
        }
    }
}
