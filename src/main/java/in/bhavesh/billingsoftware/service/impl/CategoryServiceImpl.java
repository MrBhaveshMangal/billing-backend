package in.bhavesh.billingsoftware.service.impl;
import in.bhavesh.billingsoftware.entity.CategoryEntity;
import in.bhavesh.billingsoftware.io.CategoryRequest;
import in.bhavesh.billingsoftware.io.CategoryResponse;
import in.bhavesh.billingsoftware.repository.CategoryRepository;
import in.bhavesh.billingsoftware.repository.ItemRepository;
import in.bhavesh.billingsoftware.service.CategoryService;
import in.bhavesh.billingsoftware.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;
    private final ItemRepository itemRepository;

    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file) {

        String imgUrl = fileUploadService.uploadFile(file);

        CategoryEntity entity = CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .imgUrl(imgUrl)
                .build();

        entity = categoryRepository.save(entity);
        return convertToResponse(entity);
    }

    @Override
    public List<CategoryResponse> read() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public void delete(String categoryId) {
        CategoryEntity entity = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        fileUploadService.deleteFile(entity.getImgUrl());
        categoryRepository.delete(entity);
    }

    private CategoryResponse convertToResponse(CategoryEntity e) {
        Integer count = itemRepository.countByCategory_Id(e.getId());
        return CategoryResponse.builder()
                .categoryId(e.getCategoryId())
                .name(e.getName())
                .description(e.getDescription())
                .bgColor(e.getBgColor())
                .imgUrl(e.getImgUrl())
                .items(count)
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }
}
