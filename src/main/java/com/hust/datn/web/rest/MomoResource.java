package com.hust.datn.web.rest;

import com.hust.datn.config.Constants;
import com.hust.datn.repository.UserRepository;
import com.hust.datn.security.SecurityUtils;
import com.hust.datn.service.CustOrderService;
import com.hust.datn.service.MomoService;
import com.hust.datn.service.dto.*;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.mservice.models.PaymentResponse;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/momo")
public class MomoResource {

    private final Logger log = LoggerFactory.getLogger(MomoResource.class);

    private static final String ENTITY_NAME = "Momo-Payment";

    @Autowired
    private MomoService momoService;

    @Autowired
    private CustOrderService custOrderService;

    @Autowired
    private UserRepository userRepository;

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
}
