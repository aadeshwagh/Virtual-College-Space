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

import io.aadesh.virtual_college_space.entities.Post;
import io.aadesh.virtual_college_space.entities.Student;
import io.aadesh.virtual_college_space.repos.PostRepo;
import io.aadesh.virtual_college_space.repos.StudentRepo;

@Controller
public class PostController {

    @Autowired
    PostRepo postRepo;

    @Autowired
    StudentRepo studentRepo;

    @GetMapping("/allposts")
    public String getAllPosts(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }

        model.addAttribute("posts", postRepo.findAll());

        return "posts";
    }

    @GetMapping("/postcreationpage")
    public String postCreationPage(@AuthenticationPrincipal OAuth2User principal) {

        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        return "post-creation";
    }

    @PostMapping("/createpost")
    public String createPost(@RequestBody MultiValueMap<String, String> formdata,
            @AuthenticationPrincipal OAuth2User principal, Model model) {

        if (principal == null || principal.getAttribute("login") == null) {
            return "index";
        }
        Post post = new Post();
        Set<Post> posts;
        String studentId = principal.getAttribute("login");
        post.setPostName(formdata.getFirst("postName"));
        post.setDescription(formdata.getFirst("description"));
        post.setType(formdata.getFirst("type"));
        post.setUrl(formdata.getFirst("url"));
        Student student = studentRepo.findById(studentId).get();
        post.setStudent(student);
        posts = student.getPosts();
        posts.add(post);

        student.setPosts(posts);

        postRepo.save(post);
        studentRepo.save(student);

        return getAllPosts(model, principal);

    }
}
