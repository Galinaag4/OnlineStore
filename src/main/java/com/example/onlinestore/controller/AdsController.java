package com.example.onlinestore.controller;

import com.example.onlinestore.dto.Ads;
import com.example.onlinestore.dto.CreateAds;
import com.example.onlinestore.dto.FullAds;
import com.example.onlinestore.dto.ResponseWrapperAds;
import com.example.onlinestore.service.AdsService;
import com.example.onlinestore.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Объявления", description = "Методы работы с объявлениями.")
public class AdsController {
    private final AdsService adsService;
    private final ImageService imageService;

    @Operation(
            operationId = "getAllAds",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperAds.class))
                    })
            }
    )
    @GetMapping()
    public ResponseEntity<ResponseWrapperAds<Ads>> getAllAds(@RequestParam(required = false) String title) {
        ResponseWrapperAds<Ads> ads = new ResponseWrapperAds(adsService.getAllAds(title));
        return ResponseEntity.ok(ads);
    }


    @Operation(
            operationId = "addAds",
            summary = "addAds",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = Ads.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Ads> addAds(@NotNull Authentication authentication,
                                         @RequestPart("properties") @Valid @javax.validation.constraints.NotNull @NotBlank CreateAds properties,
                                         @RequestPart("image") MultipartFile image
    ) throws IOException {
        return ResponseEntity.ok(adsService.save(properties,authentication, image));
    }
    @Operation(
            operationId = "getAdsId",
            summary = "getFullAd",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = FullAds.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<FullAds> getAdsId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adsService.getFullAdsModel(id));
    }

    @Operation(
            operationId = "deleteAds",
            summary = "deleteAds",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAds(Authentication authentication, @PathVariable("id") Integer id) {
        adsService.deleteAds(id, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @Operation(
            operationId = "updateAdsId",
            summary = "updateAdsId",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = Ads.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Ads> updateAdsId(@PathVariable("id") Integer id,
                                            @Valid @RequestBody CreateAds createAds,
                                            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
        }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateImageUser(@PathVariable Integer id, @RequestPart MultipartFile image){
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
}
}
