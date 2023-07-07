package com.example.onlinestore.controller;

import com.example.onlinestore.dto.*;
import com.example.onlinestore.model.ImageModel;

import com.example.onlinestore.service.AdsService;
import com.example.onlinestore.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Tag(name = "Объявления", description = "Методы работы с объявлениями и комментариями.")
public class AdsController {
    private final AdsService adsService;
    private final CommentService commentService;


    @Operation(summary = "Получить все объявления",
            tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
                            )
                    )
            })
    @GetMapping()
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @Operation(summary = "Добавить объявление",
            tags = "Объявления",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = CreateAds.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)    //TODO
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ads> addAds(@NotNull Authentication authentication,@RequestPart("properties") CreateAds properties,
                                      @RequestPart("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(adsService.addAd(properties,file,authentication));
    }

    @Operation(summary = "Получить комментарии объявления",
            tags = "Комментарии",
            parameters = {
                    @Parameter(
                            name = "id",
                            in = ParameterIn.PATH,
                            required = true,
                            schema = @Schema      //TODO
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperComment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            })
    @GetMapping("{id}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getComments(id));
    }

    @Operation(summary = "Добавить комментарий к объявлению",
            tags = "Комментарии",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateComment.class)
                    )
            ),
            parameters = {
                    @Parameter(
                            name = "id",
                            in = ParameterIn.PATH,
                            required = true,
                            schema = @Schema      //TODO
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            })
    @PostMapping("{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Integer id, @RequestBody CreateComment createCommentDto) {
        return ResponseEntity.ok(commentService.addComment(id, createCommentDto));
    }

    @Operation(summary = "Получить информацию об объявлении",
            tags = "Объявления",
            parameters = {
                    @Parameter(
                            name = "id",
                            in = ParameterIn.PATH,
                            required = true,
                            schema = @Schema()      //TODO
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = FullAds.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            })
    @GetMapping("{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable Integer id) {
        return ResponseEntity.ok(adsService.getAds(id));
    }

    @Operation(summary = "Удалить объявление",
            tags = "Объявления",
            parameters = {
                    @Parameter(
                            name = "id",
                            in = ParameterIn.PATH,
                            required = true,
                            schema = @Schema()      //TODO
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No Content"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeAd(Authentication authentication, @PathVariable int id) {
        if (adsService.removeAd(authentication.getName(), id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Обновить информацию об объявлении",
            tags = "Объявления",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateAds.class)
                    )
            ),
            parameters = {
                    @Parameter(
                            name = "id",
                            in = ParameterIn.PATH,
                            required = true,
                            schema = @Schema      //TODO
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            })
    @PatchMapping("{id}")
    public ResponseEntity<Ads> updateAds(@NotNull Authentication authentication, @PathVariable int id, @RequestBody CreateAds createAds) {
        Ads ads = adsService.updateDto(id, createAds, authentication.getName());
        if (ads != null) {
            return ResponseEntity.ok(ads);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Удалить комментарий",
            tags = "Комментарии",
            parameters = {
                    @Parameter(
                            name = "adId",
                            in = ParameterIn.PATH,
                            required = true,
                            schema = @Schema()      //TODO
                    ),
                    @Parameter(
                            name = "commentId",
                            in = ParameterIn.PATH,
                            required = true,
                            schema = @Schema()      //TODO
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            })
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Обновить комментарий",
            tags = "Комментарии",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Comment.class)
                    )
            ),
            parameters = {
                    @Parameter(
                            name = "adId",
                            in = ParameterIn.PATH,
                            required = true,
                            schema = @Schema()      //TODO
                    ),
                    @Parameter(
                            name = "commentId",
                            in = ParameterIn.PATH,
                            required = true,
                            schema = @Schema()      //TODO
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            })
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(Authentication authentication,
                                                     @PathVariable("adId") Integer adId,
                                                     @PathVariable("commentId") Integer commentId,
                                                     @Valid @RequestBody Comment comment) {
        Comment comment1 = commentService.updateComment(adId, commentId, comment, authentication);
        if (comment1 != null) {
            return ResponseEntity.ok(comment1);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Получить объявления авторизованного пользователя",
            tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ResponseWrapperAds.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            })
    @GetMapping("me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe() {

        return ResponseEntity.ok(adsService.getAdsMe());
    }

    @Operation(summary = "Обновить картинку объявления",
            tags = "Объявления",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema()     //TODO
                    )
            ),
            parameters = {
                    @Parameter(
                            name = "id",
                            in = ParameterIn.PATH,
                            required = true,
                            schema = @Schema      //TODO
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    schema = @Schema()      //TODO
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            })
    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id, @RequestParam MultipartFile image) throws IOException{
        adsService.updateAdImage(id, image);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/image/{id}/from-db")
//    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
//        ImageModel imageModel = imageService.read(id);
//        if (null != imageModel) {
//            HttpHeaders headers = new HttpHeaders();
    public ResponseEntity<byte[]> getAdImage(@PathVariable Integer id) {
       ImageModel imageModel = adsService.getAdImage(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(imageModel.getImage().length);//гет image или гет image byte

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(imageModel.getImage());
    }
}
