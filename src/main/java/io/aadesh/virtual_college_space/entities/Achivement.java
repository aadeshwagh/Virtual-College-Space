package io.aadesh.virtual_college_space.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Achivements")
public class Achivement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "achivement_id")
    private int achivementId;

    @Column(name = "achivement_name")
    private String achivementName;

    @Column(name = "achivement_images")
    private String achivementImage;

    public String getAchivementImage() {
        return achivementImage;
    }

    public void setAchivementImage(String achivementImage) {
        this.achivementImage = achivementImage;
    }

    @Column(name = "description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAchivementId() {
        return achivementId;
    }

    public void setAchivementId(int achivementId) {
        this.achivementId = achivementId;
    }

    public String getAchivementName() {
        return achivementName;
    }

    public void setAchivementName(String achivementName) {
        this.achivementName = achivementName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;
}
