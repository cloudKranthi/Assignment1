package com.example.Assignment1.Repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Assignment1.model.PostEntity;

@Repository
public interface PostRepository  extends JpaRepository<PostEntity,UUID>{
    
    PostEntity findByPostTitle(String postTitle);
    // Change 'getAuthor_id' to 'findByAuthor_id'
PostEntity findByAuthorId(UUID authorId);
}
