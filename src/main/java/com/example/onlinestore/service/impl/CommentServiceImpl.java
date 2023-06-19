package com.example.onlinestore.service.impl;

import com.example.onlinestore.dto.Comment;
import com.example.onlinestore.exception.CommentNotFoundException;
import com.example.onlinestore.mapper.CommentMapper;
import com.example.onlinestore.model.CommentModel;
import com.example.onlinestore.repository.AdsRepository;
import com.example.onlinestore.repository.CommentRepository;
import com.example.onlinestore.repository.UserRepository;
import com.example.onlinestore.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final ImageServiceImpl imageService;

    @Override
    public Comment addComments(Integer adsId, Comment adsComment, @NotNull Authentication authentication) {

        Integer userId = userRepository.getUserProfileId(authentication.getName());

        CommentModel commentModel = CommentMapper.INSTANCE.commentToCommentModel(adsComment);
        commentModel.setAdsId(adsId);
        commentModel.setAuthor(userId);
        commentModel.setCreatedAt(LocalDateTime.now().toString());

        commentRepository.save(commentModel);
        return CommentMapper.INSTANCE.commentModelToComment(commentModel);
    }

    @Override
    public Comment getComments(Integer adsId, Integer commentId) {
        CommentModel commentModel = commentRepository.getByAdsIdAndId(adsId, commentId).orElseThrow(CommentNotFoundException::new);
        return CommentMapper.INSTANCE.commentModelToComment(commentModel);
    }

    @Override
    public void deleteComments(Integer adsId, Integer commentId) {
        commentRepository.deleteByAdsIdAndId(adsId, commentId);
    }

    @Override
    public Comment updateComments(Integer adsId, Integer commentId, @NotNull Comment comment) {
        CommentModel commentModel = commentRepository.getByAdsIdAndId(adsId, commentId).orElseThrow(CommentNotFoundException::new);
        commentModel.setText(comment.getText());
        return CommentMapper.INSTANCE.commentModelToComment(commentRepository.save(commentModel));
    }

}
