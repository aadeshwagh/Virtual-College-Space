package io.aadesh.virtual_college_space.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aadesh.virtual_college_space.entities.Achivement;

@Repository
public interface AchivementRepo extends JpaRepository<Achivement, String> {

}