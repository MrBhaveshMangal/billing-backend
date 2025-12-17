package in.bhavesh.billingsoftware.repository;

import in.bhavesh.billingsoftware.entity.OrderEntity;
import in.bhavesh.billingsoftware.entity.OrderItemEntity;
import org.hibernate.query.Order;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderEntityRepository extends JpaRepository<OrderEntity,Long > {

    Optional<OrderEntity> findByOrderId(String orderId);

    //This will give a list of order entity
    List<OrderEntity> findAllByOrderByCreatedAtDesc();

    @Query("SELECT SUM(o.grandTotal) FROM OrderEntity o WHERE o.createdAt BETWEEN :startOfDay AND :endOfDay")
    Double sumSalesByDateRange(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.createdAt BETWEEN :startOfDay AND :endOfDay")
    Long countByOrderDateRange(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT o FROM OrderEntity o ORDER BY o.createdAt DESC")
    List<OrderEntity> findRecentOrders(Pageable pageable);
}
