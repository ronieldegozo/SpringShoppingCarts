package com.shoppingcart.shoppingcarts.controller;

import com.shoppingcart.shoppingcarts.dto.ImageDto;
import com.shoppingcart.shoppingcarts.exceptions.ResouseNotFoundException;
import com.shoppingcart.shoppingcarts.model.Image;
import com.shoppingcart.shoppingcarts.response.ApiResponse;
import com.shoppingcart.shoppingcarts.service.image.InterfaceImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final InterfaceImageService imageService;

    /**
     * Add a Image into a Product base on productId
     * Endpoint: http://localhost:5000/rest/v1/images?product=productId
     * @return ResponseEntity with the status of the operation
     */
    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse> saveImagesInProductId (@RequestParam List<MultipartFile> files, @PathVariable Long productId){
        try {
            List<ImageDto> images = imageService.saveImages(files, productId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Image already attach in Product!", images));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Images Upload failed", e.getMessage()));
        }
    }

    /**
     * Get Image by Id
     * Endpoint: http://localhost:5000/rest/v1/images?1
     * @param imageId Image Id
     * @return ResponseEntity with the status of the operation
     */
    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return  ResponseEntity.status(HttpStatus.FOUND).contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
                .body(resource);
    }

    /**
     * Update Image by ImageId
     * Endpoint: http://localhost:5000/rest/v1/images?1
     * @param imageId Image Id
     * @return ResponseEntity with the status of the operation
     */
    @PutMapping("/{imageId}")
    public ResponseEntity<ApiResponse> updateImage (@PathVariable Long imageId, @RequestBody MultipartFile file){
        try {
            Image image = imageService.getImageById(imageId);

            if(image != null){
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update Image successfully!", null));
            }
        } catch (ResouseNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Images Upload failed", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage (@PathVariable Long imageId){
        try {
            Image image = imageService.getImageById(imageId);

            if(image != null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete Image successfully!", image));
            }
        } catch (ResouseNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Delete Upload failed", INTERNAL_SERVER_ERROR));
    }


}
