package io.aadesh.virtual_college_space.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aadesh.virtual_college_space.entities.Event;

@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {

}
