package com.example.Assignment1.Repository;
import com.example.Assignment1.model.PostEntity;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface  BotRepository extends JpaRepository<PostEntity, UUID> {

}
