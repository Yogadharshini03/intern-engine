package com.example.internengine.repository;

import com.example.internengine.model.Internship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findByActiveTrue();
    long countByActiveTrue();
}