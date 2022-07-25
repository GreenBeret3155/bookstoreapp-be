package com.hust.datn.web.rest;

import com.google.gson.Gson;
import com.hust.datn.config.Constants;
import com.hust.datn.repository.UserRepository;
import com.hust.datn.security.SecurityUtils;
import com.hust.datn.service.CustOrderService;
import com.hust.datn.service.MomoService;
import com.hust.datn.service.OrderTraceService;
import com.hust.datn.service.dto.*;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.mservice.models.ConfirmResponse;
import com.mservice.models.PaymentResponse;
import com.mservice.models.QueryStatusTransactionResponse;
import com.mservice.processor.QueryTransactionStatus;
import io.github.jhipster.web.util.HeaderUtil;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MomoResource {

    private final Logger log = LoggerFactory.getLogger(MomoResource.class);

    private static final String ENTITY_NAME = "Momo-Payment";

    Gson gson = new Gson();

    @Autowired
    private MomoService momoService;

    @Autowired
    private CustOrderService custOrderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderTraceService orderTraceService;

    @PostMapping("/pay")
    public ResponseEntity<?> createOrderPayRequest(@RequestBody CustOrderDTO custOrderDTO) throws Exception {
        if (custOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Id null", ENTITY_NAME, "Id null");
        }
        CustOrderDTO custOrderDTO1 = custOrderService.findOne(custOrderDTO.getId()).orElseThrow(() -> new NotFoundException("Order"));
        if(custOrderDTO1.getPaymentType() == Constants.PAYMENT_TYPE.MOMO){
            try {
                PaymentResponse paymentResponse = momoService.createOrderMoMo(custOrderDTO1.getId(), custOrderDTO1.getAmount());
                if(paymentResponse.getResultCode() == 0){
                    return ResponseEntity.ok().body(paymentResponse);
                }
                throw new BadRequestAlertException(paymentResponse.getMessage(), ENTITY_NAME, String.valueOf(paymentResponse.getResultCode()));
            } catch (Exception e){
                throw e;
            }
        } else {
            return ResponseEntity.badRequest().body(new PaymentResponse(-1, "Đơn hàng không được thanh toán theo phương thức này"));
        }
    }

    @PostMapping("/check-transaction")
    public ResponseEntity<?> getPayResult(@RequestBody PayResultDTO payResultDTO) throws Exception {
        if (payResultDTO.getOrderId() == null||
            payResultDTO.getAmount() == null) {
            throw new BadRequestAlertException("Id null", ENTITY_NAME, "Id null");
        }
        CustOrderDTO custOrderDTO1 = custOrderService.findOne(payResultDTO.getOrderId()).orElseThrow(() -> new NotFoundException("Order"));
        if(custOrderDTO1.getPaymentType().equals(Constants.PAYMENT_TYPE.MOMO)){
            try {
                QueryStatusTransactionResponse queryStatusTransactionResponse = momoService.queryOrderMoMo(payResultDTO.getOrderId());
                if(queryStatusTransactionResponse.getResultCode() == 0){
                    orderTraceService.save(new OrderTraceDTO(payResultDTO.getOrderId(), Constants.ORDER_RESULT_MESSAGE.PAY_SUCCESS, gson.toJson(queryStatusTransactionResponse), Constants.ORDER_STATE.DA_THANH_TOAN, "admin", Instant.now()));
                    return ResponseEntity.ok().body(queryStatusTransactionResponse);
                }
            } catch (Exception e){
                throw e;
            }
        }
        return ResponseEntity.badRequest().body(new ConfirmResponse());
    }
}
