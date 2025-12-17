package in.bhavesh.billingsoftware.controller;

import com.razorpay.RazorpayException;
import in.bhavesh.billingsoftware.io.OrderResponse;
import in.bhavesh.billingsoftware.io.PaymentRequest;
import in.bhavesh.billingsoftware.io.PaymentVerificationRequest;
import in.bhavesh.billingsoftware.io.RazorPayOrderResponse;
import in.bhavesh.billingsoftware.service.OrderService;
import in.bhavesh.billingsoftware.service.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final RazorpayService razorpayService;
    private final OrderService orderService;

    @PostMapping("/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorPayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException {
        return razorpayService.createOrder(request.getAmount(), request.getCurrency());
    }

    @PostMapping("/verify")
    public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request){
        return orderService.verifyPayment(request);

    }
}
