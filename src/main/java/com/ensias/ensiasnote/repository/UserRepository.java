package com.ensias.ensiasnote.repository;

import com.ensias.ensiasnote.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String username);

  Boolean existsByEmail(String email);

  List<User> findByRole(String role);

}
