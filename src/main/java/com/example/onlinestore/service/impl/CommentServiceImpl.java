package com.example.onlinestore.service.impl;

import com.example.onlinestore.dto.Comment;
import com.example.onlinestore.dto.CreateComment;
import com.example.onlinestore.dto.ResponseWrapperComment;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CommentServiceImpl {
    public final CommentMapper commentMapper;
    public final CommentRepository commentRepository;
    public final UserRepository userRepository;
    public final UserServiceImpl userServiceImpl;
    public final AdsRepository adsRepository;

    public CommentServiceImpl(CommentMapper commentMapper, CommentRepository commentRepository, UserRepository userRepository, UserServiceImpl userServiceImpl, AdsRepository adsRepository) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.userServiceImpl = userServiceImpl;
        this.adsRepository = adsRepository;
    }

    @Transactional(readOnly = true)
    public ResponseWrapperComment getComments(Integer id) {
        ResponseWrapperComment responseWrapperComment = new ResponseWrapperComment();
        List<CommentModel> commentList = commentRepository.findAllByAdsModelId(id);
        responseWrapperComment.setResults(commentMapper.commentListToCommentDtoList(commentList));
        responseWrapperComment.setCount(commentList.size());
        return responseWrapperComment;
    }

    @Transactional
    public Comment addComment(Integer id, CreateComment createComment) {
        CommentModel commentModel = commentMapper.toCommentModel(createComment);
        commentModel.setAdsModel(adsRepository.findById(id).orElse(null));
        commentModel.setUserModel(userRepository.findByUsername(userServiceImpl.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found")));
        commentModel.setCreatedAt((int) System.currentTimeMillis());
        commentRepository.save(commentModel);
        return commentMapper.commentModelToComment(commentModel);
    }

    @Transactional
    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public Comment updateComment(Integer commentId, Comment comment) {
        CommentModel updatedComment = commentRepository.findById(commentId).orElseThrow();
        updatedComment.setText(comment.getText());
        commentRepository.save(updatedComment);
        return commentMapper.commentModelToComment(updatedComment);
    }

    @Transactional
    public void deleteCommentsByAdId(Integer adsId) {
        commentRepository.deleteCommentsByAdsModelId(adsId);

    }



}
