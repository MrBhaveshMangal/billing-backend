package in.bhavesh.billingsoftware.service;

import com.razorpay.RazorpayException;
import in.bhavesh.billingsoftware.io.RazorPayOrderResponse;

public interface RazorpayService {

    RazorPayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}
