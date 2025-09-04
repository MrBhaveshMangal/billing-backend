package in.bhavesh.billingsoftware.repository;

import in.bhavesh.billingsoftware.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ItemRepository extends JpaRepository<ItemEntity,Long> {
    Optional<ItemEntity> findByItemId(String id);

    //bcoz we want to display the no. of items under the one category
    Integer countByCategoryId(Long id);

}
