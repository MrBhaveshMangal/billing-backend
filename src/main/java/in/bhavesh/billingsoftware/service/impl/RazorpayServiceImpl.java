package in.bhavesh.billingsoftware.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import in.bhavesh.billingsoftware.io.OrderResponse;
import in.bhavesh.billingsoftware.io.RazorPayOrderResponse;
import in.bhavesh.billingsoftware.service.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class RazorpayServiceImpl implements RazorpayService {


    @Value("${razorpay.key.id}")
    private String razorpayKeyId;
    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Override
    public RazorPayOrderResponse createOrder(Double amount, String currency) throws RazorpayException {
        RazorpayClient razorpayClient=new RazorpayClient(razorpayKeyId,razorpayKeySecret);
        JSONObject orderRequest=new JSONObject();
        orderRequest.put("amount",amount*100);
        orderRequest.put("currency",currency);
        orderRequest.put("receipt","order_rcpid_"+System.currentTimeMillis());
        orderRequest.put("payment_capture",1);

        Order order=razorpayClient.orders.create(orderRequest);
        return convertToResponse(order);
    }

    private RazorPayOrderResponse convertToResponse(Order order) {
        return RazorPayOrderResponse.builder()
                .id(order.get("id"))
                .entity(order.get("entity"))
                .amount(order.get("amount"))
                .currency(order.get("currency"))
                .status(order.get("status"))
                .created_at(order.get("created_at"))
                .receipt(order.get("receipt"))
                .build();
    }


}
