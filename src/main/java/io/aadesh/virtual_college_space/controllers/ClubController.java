package io.aadesh.virtual_college_space.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.aadesh.virtual_college_space.entities.Club;
import io.aadesh.virtual_college_space.entities.Student;
import io.aadesh.virtual_college_space.repos.ClubRepo;
import io.aadesh.virtual_college_space.repos.StudentRepo;

@Controller
public class ClubController {

    @Autowired
    ClubRepo clubRepo;

    @Autowired
    StudentRepo studentRepo;

    @GetMapping("/clubregistrationpage")
    public String clubRegistrationPage(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        return "club-registration";
    }

    @PostMapping("/createclub")
    public String createClub(@RequestBody MultiValueMap<String, String> formdata,
            @AuthenticationPrincipal OAuth2User principal, Model model) {

        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        Set<Student> students = new HashSet<>();
        Set<Club> clubs;
        Club club = new Club();
        String studentId = principal.getAttribute("login");
        club.setAdmin(studentId);
        club.setClubName(formdata.getFirst("clubName"));
        club.setClubDescription(formdata.getFirst("clubDescription"));
        club.setClubImage(formdata.getFirst("clubImage"));
        club.setEvents(new HashSet<>());
        Student student = studentRepo.findById(studentId).get();
        clubs = student.getClubs();
        clubs.add(club);
        students.add(student);

        club.setStudents(students);
        student.setClubs(clubs);

        clubRepo.save(club);
        studentRepo.save(student);

        return getAllClubs(principal, model);

    }

    @GetMapping("/getclubbyname/{clubName}")
    public String getClubByName(@PathVariable String clubName, @AuthenticationPrincipal OAuth2User principal,
            Model model) {

        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        Optional<Club> optionalClub = clubRepo.findById(clubName);
        Student student = studentRepo.findById(principal.getAttribute("login")).get();
        if (optionalClub.isPresent()) {
            Club club = optionalClub.get();
            String imgUrl = "/images/" + club.getClubImage();
            model.addAttribute("club", club);
            model.addAttribute("clubImage", imgUrl);
            model.addAttribute("events", club.getEvents());

            if (club.getStudents().contains(student)) {
                model.addAttribute("member", student);
            }
            if (principal.getAttribute("login").equals(club.getAdmin())) {
                model.addAttribute("clubAdmin", club.getAdmin() + ": Club admin");
            }
            return "club-details";

        }

        return "page-not-found";

    }

    @GetMapping("/getallclubs")
    public String getAllClubs(@AuthenticationPrincipal OAuth2User principal,
            Model model) {

        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        List<Club> clubs = clubRepo.findAll();
        if (!clubs.isEmpty()) {
            model.addAttribute("clubs", clubs);

        }
        return "clubs";

    }

    @GetMapping("/eventregistrationpage/{clubName}")
    public String eventRegistrationPage(@AuthenticationPrincipal OAuth2User principal, @PathVariable String clubName,
            Model model) {
        model.addAttribute("clubName", clubName);
        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        return "event-registration";
    }

    @GetMapping("/addclubmember/{clubName}")
    public String addClubMember(@AuthenticationPrincipal OAuth2User principal, @PathVariable String clubName,
            Model model) {
        Set<Student> students;
        Set<Club> clubs;
        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        Club club = clubRepo.findById(clubName).get();
        Student student = studentRepo.findById(principal.getAttribute("login")).get();
        students = club.getStudents();
        students.add(student);

        club.setStudents(students);

        clubs = student.getClubs();
        clubs.add(club);
        student.setClubs(clubs);

        clubRepo.save(club);
        studentRepo.save(student);

        return getClubByName(clubName, principal, model);
    }

    @GetMapping("/myclubs")
    public String myClubs(@AuthenticationPrincipal OAuth2User principal,
            Model model) {
        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        Student student = studentRepo.findById(principal.getAttribute("login")).get();
        model.addAttribute("clubs", student.getClubs());

        return "my-clubs";
    }

}
