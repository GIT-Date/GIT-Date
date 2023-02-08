package com.GITDate.GITDate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.GITDate.GITDate.models.UserComment;
//import javax.xml.stream.events.Comment;

public interface CommentRepository extends JpaRepository <UserComment, Long> {
}
