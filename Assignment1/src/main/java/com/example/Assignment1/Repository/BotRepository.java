package com.example.Assignment1.Repository;
import com.example.Assignment1.model.BotEntity;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


@Repository
public interface  BotRepository extends JpaRepository<BotEntity, UUID> {
  Optional<BotEntity> findByName(String name);
}
