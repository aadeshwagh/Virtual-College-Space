package io.aadesh.virtual_college_space.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import io.aadesh.virtual_college_space.entities.Student;
import io.aadesh.virtual_college_space.repos.StudentRepo;

@Controller
public class HomeController {

    @Autowired
    StudentRepo studentRepo;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        String studentId = principal.getAttribute("login");
        Optional<Student> student = studentRepo.findById(studentId);
        if (student.isPresent()) {
            return "home";
        }
        return "register";

    }

    @GetMapping("/notes")
    public String notes() {
        return "notes";
    }

    @GetMapping("/placements")
    public String placements() {
        return "placements";
    }

}
