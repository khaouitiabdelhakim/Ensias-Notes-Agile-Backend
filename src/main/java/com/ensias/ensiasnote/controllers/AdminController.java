package com.ensias.ensiasnote.controllers;

import com.ensias.ensiasnote.models.User;
import com.ensias.ensiasnote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

  @Autowired
  UserRepository userRepository;

  @GetMapping("/all-students")
  public ResponseEntity<?> getAllStudents() {
    List<User> students = userRepository.findByRole("STUDENT");
    return ResponseEntity.ok(students);
  }

  @GetMapping("/all-teachers")
  public ResponseEntity<?> getAllTeachers() {
    List<User> teachers = userRepository.findByRole("TEACHER");
    return ResponseEntity.ok(teachers);
  }
}
