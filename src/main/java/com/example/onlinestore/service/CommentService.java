package com.example.onlinestore.service;

import com.example.onlinestore.dto.Comment;
import com.example.onlinestore.dto.CreateComment;
import com.example.onlinestore.dto.ResponseWrapperComment;
import com.example.onlinestore.exception.AdsNotFoundException;
import com.example.onlinestore.exception.CommentNotFoundException;
import com.example.onlinestore.mapper.CommentMapper;
import com.example.onlinestore.model.CommentModel;
import com.example.onlinestore.model.UserModel;
import com.example.onlinestore.repository.AdsRepository;
import com.example.onlinestore.repository.CommentRepository;
import com.example.onlinestore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CommentService {
    public final CommentMapper commentMapper;
    public final CommentRepository commentRepository;
    public final UserRepository userRepository;
    public final UserService userService;
    public final AdsRepository adsRepository;
    private final PropertyService propertyService;

    public CommentService(CommentMapper commentMapper, CommentRepository commentRepository, UserRepository userRepository, UserService userService, AdsRepository adsRepository, PropertyService propertyService) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.adsRepository = adsRepository;
        this.propertyService = propertyService;
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
        commentModel.setUserModel(userRepository.findByUsername(userService.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found")));
        commentModel.setCreatedAt(System.currentTimeMillis());
        commentRepository.save(commentModel);
        return commentMapper.commentModelToComment(commentModel);
    }

    @Transactional
    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public Comment updateComment(Integer adId, Integer commentId, @NotNull Comment comment, Authentication authentication) {
        CommentModel commentModel = commentRepository.findByAdsModel_IdAndId(adId, commentId).orElseThrow(CommentNotFoundException::new);
        UserModel commentOwner = commentModel.getUserModel();
        if (propertyService.isThisUserOrAdmin(authentication.getName(), commentOwner)) {
            if (commentModel.getAdsModel().getId() != adId) {
                throw new AdsNotFoundException();
            }
            commentModel.setText(comment.getText());
            commentRepository.save(commentModel);
            adsRepository.save(commentModel.getAdsModel());
            return commentMapper.commentModelToComment(commentModel);
        }
        return comment;
    }

}
