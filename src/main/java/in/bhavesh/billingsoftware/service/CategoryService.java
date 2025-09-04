package in.bhavesh.billingsoftware.service;

import in.bhavesh.billingsoftware.io.CategoryRequest;
import in.bhavesh.billingsoftware.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

     CategoryResponse add(CategoryRequest request, MultipartFile file);

     //To read the categories
     List<CategoryResponse> read();

     void delete(String categoryID);

}
