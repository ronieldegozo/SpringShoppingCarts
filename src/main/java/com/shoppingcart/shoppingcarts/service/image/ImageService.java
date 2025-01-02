package com.shoppingcart.shoppingcarts.service.image;

import com.shoppingcart.shoppingcarts.dto.ImageDto;
import com.shoppingcart.shoppingcarts.exceptions.ResourceNotFoundException;
import com.shoppingcart.shoppingcarts.model.Image;
import com.shoppingcart.shoppingcarts.model.Product;
import com.shoppingcart.shoppingcarts.repository.ImageRepository;
import com.shoppingcart.shoppingcarts.service.product.InterfaceProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ImageService implements InterfaceImageService {

    private final ImageRepository imageRepository;
    private final InterfaceProductService interfaceProductService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id" + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(
                imageRepository::delete,
                () -> new ResourceNotFoundException("No image found with id" + id)
        );
    }

    @Override
    public List<ImageDto> saveImages (List<MultipartFile> files, Long product_id) {

        Product product = interfaceProductService.getProductById(product_id);
        List<ImageDto> savedImageDto = new ArrayList<>();

        for(MultipartFile file : files) {
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob((file.getBytes())));
                image.setProduct(product);

                String buildDownloadUrl = "/rest/v1/images/images/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                imageRepository.save(image);

                Image saveImage = imageRepository.save(image);
                saveImage.setDownloadUrl(buildDownloadUrl + saveImage.getId());
                imageRepository.save(saveImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId((long) saveImage.getId());
                imageDto.setFileName(saveImage.getFileName());
                imageDto.setDownloadUrl(saveImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            }catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
