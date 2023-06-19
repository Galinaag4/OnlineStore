package com.example.onlinestore.service;

import com.example.onlinestore.dto.Comment;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public interface CommentService {

    Comment addComments(Integer adsId, Comment adsComment, Authentication authentication);
    Comment getComments(Integer adsId,Integer commentId);
    void deleteComments(Integer adsId, Integer commentId);
    Comment updateComments(Integer adsId, Integer commentId, Comment comment);
}
