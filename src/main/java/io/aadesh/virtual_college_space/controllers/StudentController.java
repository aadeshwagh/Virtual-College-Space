package io.aadesh.virtual_college_space.controllers;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.aadesh.virtual_college_space.entities.Student;
import io.aadesh.virtual_college_space.repos.StudentRepo;

@Controller
public class StudentController {

    @Autowired
    StudentRepo studentRepo;

    @PostMapping("/registerstudent")
    public String registerStudent(@RequestBody MultiValueMap<String, String> formdata,
            @AuthenticationPrincipal OAuth2User principal) {

        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        Student student = new Student();
        String studentId = principal.getAttribute("login");
        student.setStudentId(studentId);

        student.setStudentName(formdata.getFirst("studentName"));
        student.setStudentMajor(formdata.getFirst("studentMajor"));
        student.setStudentEmail(formdata.getFirst("studentEmail"));
        student.setClubs(new HashSet<>());
        student.setEvents(new HashSet<>());
        student.setPosts(new HashSet<>());
        student.setAchivements(new HashSet<>());

        studentRepo.save(student);

        return "home";
    }

}
