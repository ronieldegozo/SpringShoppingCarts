package com.shoppingcart.shoppingcarts.service.image;

import com.shoppingcart.shoppingcarts.dto.ImageDto;
import com.shoppingcart.shoppingcarts.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InterfaceImageService {

    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long product_id);
    void updateImage(MultipartFile file, Long imageId);

}
