package com.example.Assignment1.Repository;
import com.example.Assignment1.model.UserEntity;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,UUID> {
  Optional<UserEntity> findByUsername(String username);
}
