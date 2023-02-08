package com.GITDate.GITDate.repositories;

import com.GITDate.GITDate.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Post, Long> {

}
