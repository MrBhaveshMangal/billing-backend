package in.bhavesh.billingsoftware.service;

import in.bhavesh.billingsoftware.io.ItemRequest;
import in.bhavesh.billingsoftware.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    ItemResponse add(ItemRequest request , MultipartFile file);

    List<ItemResponse> fetchItems();

    void deleteItem(String itemId);
}
