package in.bhavesh.billingsoftware.service.impl;

import in.bhavesh.billingsoftware.entity.CategoryEntity;
import in.bhavesh.billingsoftware.io.CategoryRequest;
import in.bhavesh.billingsoftware.io.CategoryResponse;
import in.bhavesh.billingsoftware.repository.CategoryRepository;
import in.bhavesh.billingsoftware.service.CategoryService;
import in.bhavesh.billingsoftware.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;

    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file){
        String imgUrl=fileUploadService.uploadFile(file);
        CategoryEntity newCategory=convertToEntity(request);
        newCategory.setImgUrl(imgUrl);
        newCategory=categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> read(){
        return  categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> convertToResponse(categoryEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryId){
        CategoryEntity existingCategory=categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(()->new RuntimeException("CAtegory not found: "+categoryId));
        fileUploadService.deleteFile(existingCategory.getImgUrl());


        categoryRepository.delete(existingCategory);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        return CategoryResponse.builder()
                .categoryId((newCategory.getCategoryId()))
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imgUrl(newCategory.getImgUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .build();
    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
        return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description((request.getDescription()))
                .bgColor(request.getBgColor())
                .build();
    }
}
