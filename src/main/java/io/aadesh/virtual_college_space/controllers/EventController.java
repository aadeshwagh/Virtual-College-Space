package io.aadesh.virtual_college_space.controllers;

import java.time.LocalDate;
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

import java.util.HashSet;
import java.util.List;
import io.aadesh.virtual_college_space.entities.Club;
import io.aadesh.virtual_college_space.entities.Event;
import io.aadesh.virtual_college_space.entities.Student;
import io.aadesh.virtual_college_space.repos.ClubRepo;
import io.aadesh.virtual_college_space.repos.EventRepo;
import io.aadesh.virtual_college_space.repos.StudentRepo;

@Controller
public class EventController {
    @Autowired
    EventRepo eventRepo;

    @Autowired
    ClubRepo clubRepo;

    @Autowired
    ClubController clubController;

    @Autowired
    StudentRepo studentRepo;

    @PostMapping("/createevent")
    public String createEvent(@RequestBody MultiValueMap<String, String> formdata,
            @AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        Event event = new Event();
        Set<Event> events;
        String clubName = formdata.getFirst("clubName");
        Optional<Club> optionalClub = clubRepo.findById(clubName);
        Club club = null;
        if (optionalClub.isPresent()) {
            club = optionalClub.get();
        }

        events = club.getEvents();

        event.setClub(club);
        event.setEndDate(LocalDate.parse(formdata.getFirst("endDate")));
        event.setEventDescription(formdata.getFirst("eventDescription"));
        event.setEventImage(formdata.getFirst("eventImage"));
        event.setEventOrganizer(formdata.getFirst("eventOrganizer"));
        event.setEventTitle(formdata.getFirst("eventTitle"));
        event.setStartDate(LocalDate.parse(formdata.getFirst("startDate")));
        event.setStudents(new HashSet<>());

        events.add(event);
        club.setEvents(events);

        eventRepo.save(event);

        clubRepo.save(club);

        return clubController.getClubByName(clubName, principal, model);
    }

    @GetMapping("/getallevents")
    public String getAllevents(@AuthenticationPrincipal OAuth2User principal,
            Model model) {

        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        List<Event> events = eventRepo.findAll();
        if (!events.isEmpty()) {
            model.addAttribute("events", events);

        }
        return "events";

    }

    @GetMapping("/geteventbyid/{eventId}")
    public String getEventById(@PathVariable String eventId, @AuthenticationPrincipal OAuth2User principal,
            Model model) {

        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        Optional<Event> optionalEvent = eventRepo.findById(Integer.parseInt(eventId));
        Student student = studentRepo.findById(principal.getAttribute("login")).get();
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            String imgUrl = "/images/" + event.getEventImage();
            model.addAttribute("event", event);
            model.addAttribute("eventImage", imgUrl);
            if (event.getStudents().contains(student)) {
                model.addAttribute("registered", student);
            }
            return "event-details";

        }

        return "page-not-found";

    }

    @GetMapping("/addeventmember/{eventId}")
    public String addeventMember(@AuthenticationPrincipal OAuth2User principal, @PathVariable String eventId,
            Model model) {
        Set<Student> students;
        Set<Event> events;
        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        Event event = eventRepo.findById(Integer.parseInt(eventId)).get();
        Student student = studentRepo.findById(principal.getAttribute("login")).get();
        students = event.getStudents();
        students.add(student);

        event.setStudents(students);

        events = student.getEvents();
        events.add(event);
        student.setEvents(events);

        eventRepo.save(event);
        studentRepo.save(student);

        return getEventById(eventId, principal, model);
    }

    @GetMapping("/myevents")
    public String myEvents(@AuthenticationPrincipal OAuth2User principal,
            Model model) {
        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        Student student = studentRepo.findById(principal.getAttribute("login")).get();
        model.addAttribute("events", student.getEvents());

        return "my-events";
    }
}
