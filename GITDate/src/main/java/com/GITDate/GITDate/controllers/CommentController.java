package com.GITDate.GITDate.controllers;

import com.GITDate.GITDate.models.AppUser;
import com.GITDate.GITDate.models.Post;
import com.GITDate.GITDate.models.UserComment;
import com.GITDate.GITDate.repositories.AppUserRepository;

import com.GITDate.GITDate.repositories.CommentRepository;
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
import javax.xml.stream.events.Comment;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    HttpServletRequest request;


    @PostMapping("/user/{id}")
    public RedirectView createComment(Principal p, String body, Model m) {
        if(p!=null){
            String username = p.getName();
            AppUser appUser = appUserRepository.findByUsername(username);
            m.addAttribute("addComment", appUser.getListOfComment());
            Date date = new Date();
            UserComment newComment = new UserComment(body, date, appUser);
            newComment.setCreatedBy(appUser);
            commentRepository.save(newComment);

        }
        return new RedirectView("/user/{id}");
    }

    public void autoAuthWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException se) {
//            System.out.print("hellohello");
            se.printStackTrace();
        }
    }
}
