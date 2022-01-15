package io.aadesh.virtual_college_space.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.aadesh.virtual_college_space.entities.Achivement;
import io.aadesh.virtual_college_space.entities.Student;
import io.aadesh.virtual_college_space.repos.StudentRepo;
import io.aadesh.virtual_college_space.repos.AchivementRepo;

@Controller
public class AchivementsController {

    @Autowired
    AchivementRepo achivementRepo;

    @Autowired
    StudentRepo studentRepo;

    @GetMapping("/allachivements")
    public String getAllAchivements(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }

        model.addAttribute("achivements", achivementRepo.findAll());

        return "achivements";
    }

    @GetMapping("/achivementcreationpage")
    public String AchivementCreationPage(@AuthenticationPrincipal OAuth2User principal) {

        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        return "achivement-creation";
    }

    @PostMapping("/createachivement")
    public String createAchivement(@RequestBody MultiValueMap<String, String> formdata,
            @AuthenticationPrincipal OAuth2User principal, Model model) {

        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        Achivement achivement = new Achivement();
        Set<Achivement> achivements;
        String studentId = principal.getAttribute("login");
        achivement.setAchivementName(formdata.getFirst("achivementName"));
        achivement.setDescription(formdata.getFirst("description"));
        achivement.setAchivementImage(formdata.getFirst("achivementImage"));
        Student student = studentRepo.findById(studentId).get();
        achivement.setStudent(student);
        achivements = student.getAchivements();
        achivements.add(achivement);

        student.setAchivements(achivements);

        achivementRepo.save(achivement);
        studentRepo.save(student);

        return getAllAchivements(model, principal);

    }
}
