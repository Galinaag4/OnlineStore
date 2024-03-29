package com.example.onlinestore.service;

import com.example.onlinestore.dto.Comment;
import com.example.onlinestore.dto.CreateComment;
import com.example.onlinestore.dto.ResponseWrapperComment;
import com.example.onlinestore.dto.Role;
import com.example.onlinestore.exception.AccessDeniedException;
import com.example.onlinestore.exception.AdsNotFoundException;
import com.example.onlinestore.exception.CommentNotFoundException;
import com.example.onlinestore.mapper.CommentMapper;
import com.example.onlinestore.model.CommentModel;
import com.example.onlinestore.model.ImageModel;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс - сервис для работы с комментариями
 *
 * @see CommentModel
 * @see CommentRepository
 */
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


    /**
     * Метод ищет и возвращает список всех комментариев к объявлению по id объявления
     *
     * @param id
     * {@link CommentRepository#findAllByAdsModelId(Integer)}
     * {@link CommentMapper#commentListToCommentDtoList(List)}
     *
     * @return  {@link ResponseWrapperComment}
     */
    @Transactional(readOnly = true)
    public ResponseWrapperComment getComments(Integer id) {
        ResponseWrapperComment responseWrapperComment = new ResponseWrapperComment();
        List<CommentModel> commentList = commentRepository.findAllByAdsModelId(id);
        responseWrapperComment.setResults(commentMapper.commentListToCommentDtoList(commentList));
        responseWrapperComment.setCount(commentList.size());
        return responseWrapperComment;
    }

    /**
     * Метод создает комментарий к объявлению по id объявления
     *
     * @param id,createComment
     *
     * {@link AdsRepository#findById(Object)}
     * {@link UserRepository#findByUsername(String)}
     * {@link CommentRepository#save(Object)}
     *
     * @return  {@link Comment}
     */
    @Transactional
    public Comment addComment(Integer id, CreateComment createComment, Authentication authentication) {
        CommentModel commentModel = commentMapper.toCommentModel(createComment);
        commentModel.setAdsModel(adsRepository.findById(id).orElse(null));
        commentModel.setUserModel(userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found")));
        commentModel.setCreatedAt(LocalDateTime.now());
        commentRepository.save(commentModel);
        return commentMapper.commentModelToComment(commentModel);
    }

    /**
     * Метод удаляет комментарий к объявлению по id объявления
     *
     * @param commentId,adId,authentication
     *
     * {@link CommentRepository#findByAdsModel_IdAndId(Integer, Integer)}
     * {@link CommentRepository#delete(Object)}
     *
     * @throws AccessDeniedException если нет доступа к удалению объявления
     */
    @Transactional
    public void deleteComment(Integer adId, Integer commentId, Authentication authentication) {
//        commentRepository.deleteById(commentId);
        if (checkRights(commentId, authentication)) {
            CommentModel commentModel = commentRepository.findByAdsModel_IdAndId(adId, commentId)
                    .orElseThrow(CommentNotFoundException::new);
            commentRepository.delete(commentModel);
        } else {
            throw new AccessDeniedException();
        }
    }


    /**
     * Метод редактирует комментарий к объявлению по id
     *
     * @param adId,commentId,comment,authentication
     *
     * {@link CommentRepository#findByAdsModel_IdAndId(Integer, Integer)}
     * {@link PropertyService#isThisUserOrAdmin(String, UserModel)}
     * {@link CommentRepository#save(Object)}
     * {@link AdsRepository#save(Object)}
     *
     * @throws CommentNotFoundException если комментарий не найден
     * @throws AdsNotFoundException если объявление не найдено
     *
     * @return  {@link Comment}
     */
    @Transactional
    public Comment updateComment(Integer adId, Integer commentId, @NotNull Comment comment, Authentication authentication) {
        CommentModel commentModel = commentRepository.findByAdsModel_IdAndId(adId, commentId)
                .orElseThrow(CommentNotFoundException::new);
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

    /**
     * Метод проверяет наличие доступа к редактированию или удалению комментария по id
     *
     * @param id,authentication
     *
     * {@link CommentRepository#findById(Object)}
     * {@link UserService#getUserModel(Authentication)}
     *
     * @throws CommentNotFoundException если комментарий не найден
     */
    public boolean checkRights (Integer id, Authentication authentication) {
        CommentModel commentModel = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        UserModel currentUser = userService.getUserModel(authentication);
        String userModelCommentLogin = commentModel.getUserModel().getUsername();
        Role currentUserRole = currentUser.getRole();
        String currentUserLogin = authentication.getName();
        return userModelCommentLogin.equals(currentUserLogin) || currentUserRole == Role.ADMIN;
    }

}
