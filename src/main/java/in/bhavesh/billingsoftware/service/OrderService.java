package in.bhavesh.billingsoftware.service;

import in.bhavesh.billingsoftware.io.OrderRequest;
import in.bhavesh.billingsoftware.io.OrderResponse;
import in.bhavesh.billingsoftware.io.PaymentVerificationRequest;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    //this createOrder function will accept parameters from OrderRequest
    //and retrurn the value based upon the OrderResponse
    OrderResponse createOrder(OrderRequest request);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();

    OrderResponse verifyPayment(PaymentVerificationRequest request);

    Double sumSalesByDate(LocalDate date);

    Long countByOrderDate(LocalDate date);

    List<OrderResponse> findRecentOrders();
}
