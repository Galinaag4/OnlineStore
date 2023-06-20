package com.example.onlinestore.controller;

import com.example.onlinestore.dto.Comment;
import com.example.onlinestore.dto.ResponseWrapperAds;
import com.example.onlinestore.dto.ResponseWrapperComment;
import com.example.onlinestore.mapper.CommentMapper;
import com.example.onlinestore.repository.CommentRepository;
import com.example.onlinestore.service.AdsService;
import com.example.onlinestore.service.CommentService;
import com.example.onlinestore.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("ads")
@RequiredArgsConstructor
@Tag(name = "Комментарии", description = "Методы работы с комментариями.")
public class CommentController {
    private final AdsService adsService;
    private final ImageService imageService;
    private final CommentService commentService;
    private CommentMapper commentMapper;
    private CommentRepository commentRepository;

    @Operation(
            operationId = "getComments",
            summary = "getComments",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperAds.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperComment<Comment>> getComments(@PathVariable("ad_pk") Integer adPk,
                                                                       @PathVariable("id") Integer id) {
        ResponseWrapperComment<Comment> comment = new ResponseWrapperComment<>(commentService.getComments(adPk,id));
        return ResponseEntity.ok(comment);
    }
    @Operation(
            operationId = "addComments",
            summary = "addComments",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = Comment.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComments(@PathVariable("id") Integer id,
                                                  @NotNull @Valid @RequestBody Comment comment,
                                                  Authentication authentication) {
        return ResponseEntity.ok(commentService.addComments(id, comment, authentication));
    }
    @Operation(
            operationId = "deleteComments",
            summary = "deleteComments",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComments(@PathVariable("adId") Integer adId,
                                               @PathVariable("commentId") Integer commentId) {
        commentService.deleteComments(adId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @Operation(
            operationId = "updateComments",
            summary = "updateComments",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = Comment.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComments(
                                                     @PathVariable("adId") Integer adId,
                                                     @PathVariable("commentId") Integer commentId,
                                                     @Valid @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.updateComments(adId, commentId, comment));
    }
}
