package in.bhavesh.billingsoftware.service.impl;

import in.bhavesh.billingsoftware.entity.CategoryEntity;
import in.bhavesh.billingsoftware.entity.ItemEntity;
import in.bhavesh.billingsoftware.io.ItemRequest;
import in.bhavesh.billingsoftware.io.ItemResponse;
import in.bhavesh.billingsoftware.repository.CategoryRepository;
import in.bhavesh.billingsoftware.repository.ItemRepository;
import in.bhavesh.billingsoftware.service.FileUploadService;
import in.bhavesh.billingsoftware.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {
        String imgUrl=fileUploadService.uploadFile(file);//This will gives us the public URL
        ItemEntity newItem=convertToEntity(request);
        CategoryEntity existingCategory= categoryRepository.findByCategoryId(request.getCategoryid())
                .orElseThrow(()->new RuntimeException("Category not found"+request.getCategoryid()));
        newItem.setCategory(existingCategory);
        newItem.setImgUrl(imgUrl);
        newItem=itemRepository.save(newItem);
        return convertToResponse(newItem);
    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
                .itemid(newItem.getItemId())
                .name(newItem.getName())
                .description(newItem.getDescription())
                .price(newItem.getPrice())
                .imgurl(newItem.getImgUrl())
                .categoryName(newItem.getCategory().getName())
                .categoryid(newItem.getCategory().getCategoryId())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest request) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    @Override
    public List<ItemResponse> fetchItems() {
        return itemRepository.findAll()
                .stream()
                .map(itemEntity -> convertToResponse(itemEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {
        //To delete item we have to do two things like
        //remove image from our minIO and then remove
        //the item from the list
        ItemEntity existingitem=itemRepository.findByItemId(itemId)
                .orElseThrow(()->new RuntimeException("Item not found: "+itemId));
        boolean isFileDelete=fileUploadService.deleteFile(existingitem.getImgUrl());
        if(isFileDelete){
            itemRepository.delete(existingitem);
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to delete the image");
        }

    }
}
