package io.aadesh.virtual_college_space.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aadesh.virtual_college_space.entities.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, String> {

}
