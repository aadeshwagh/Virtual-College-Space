package io.aadesh.virtual_college_space.entities;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
    @Id
    private String studentId;

    @Column(name = "student_name")
    private String studentName;
    @Column(name = "student_email")
    private String studentEmail;

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Column(name = "student_major")
    private String studentMajor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "club_members", joinColumns = { @JoinColumn(name = "student_id") }, inverseJoinColumns = {
            @JoinColumn(name = "club_id") })
    private Set<Club> clubs;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_members", joinColumns = { @JoinColumn(name = "student_id") }, inverseJoinColumns = {
            @JoinColumn(name = "event_id") })
    private Set<Event> events;

    @OneToMany
    @JoinTable(name = "student_posts", joinColumns = { @JoinColumn(name = "student_id") }, inverseJoinColumns = {
            @JoinColumn(name = "post_id") })
    private Set<Post> posts;

    @OneToMany
    @JoinTable(name = "student_achievements", joinColumns = { @JoinColumn(name = "student_id") }, inverseJoinColumns = {
            @JoinColumn(name = "achivement_id") })
    private Set<Achivement> achivements;

    public Set<Achivement> getAchivements() {
        return achivements;
    }

    public void setAchivements(Set<Achivement> achivements) {
        this.achivements = achivements;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentMajor() {
        return studentMajor;
    }

    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }

    public Set<Club> getClubs() {
        return clubs;
    }

    public void setClubs(Set<Club> clubs) {
        this.clubs = clubs;
    }

}
