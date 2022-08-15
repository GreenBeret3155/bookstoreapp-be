package com.hust.datn.service;

import com.google.gson.Gson;
import com.hust.datn.config.Constants;
import com.hust.datn.repository.UserRepository;
import com.hust.datn.security.SecurityUtils;
import com.hust.datn.service.dto.CustOrderDTO;
import com.hust.datn.service.dto.OrderTraceDTO;
import com.hust.datn.web.rest.MomoResource;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.mservice.config.Environment;
import com.mservice.enums.ConfirmRequestType;
import com.mservice.enums.RequestType;
import com.mservice.models.ConfirmResponse;
import com.mservice.models.PaymentResponse;
import com.mservice.models.QueryStatusTransactionResponse;
import com.mservice.models.RefundMoMoResponse;
import com.mservice.processor.ConfirmTransaction;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.processor.QueryTransactionStatus;
import com.mservice.processor.RefundTransaction;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MomoService {

    private final Logger log = LoggerFactory.getLogger(MomoService.class);

    private static final String ENTITY_NAME = "Momo-Payment";

    Gson gson = new Gson();

    @Autowired
    private CustOrderService custOrderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderTraceService orderTraceService;

    @Value("${momo.returnBaseUrl}")
    private String momoReturnBaseUrl;

    Environment environment = Environment.selectEnv("dev");
    private static final String ORDER_INFO_PREFIX = "Đơn hàng #";
    private static final String FAKE_NOTIFY_URL = "Đơn hàng #";

    public MomoService() {
    }

    public PaymentResponse createOrderMoMo(Long orderId, Long amount) throws Exception {
        PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, String.valueOf(orderId), String.valueOf(orderId), Long.toString(amount), ORDER_INFO_PREFIX + orderId, momoReturnBaseUrl, FAKE_NOTIFY_URL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
        return captureWalletMoMoResponse;
    }

    public PaymentResponse createOrderPayRequest(CustOrderDTO custOrderDTO) throws Exception {
        if (custOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Id null", ENTITY_NAME, "Id null");
        }
        CustOrderDTO custOrderDTO1 = custOrderService.findOne(custOrderDTO.getId()).orElseThrow(() -> new NotFoundException("Order"));
        if(custOrderDTO1.getPaymentType() == Constants.PAYMENT_TYPE.MOMO){
            try {
                PaymentResponse paymentResponse = createOrderMoMo(custOrderDTO1.getId(), custOrderDTO1.getAmount());
                return paymentResponse;
            } catch (Exception e){
                throw e;
            }
        } else {
            return new PaymentResponse(-1, "Đơn hàng không được thanh toán theo phương thức này");
        }
    }


    public ConfirmResponse confirmTransactionMomo(Long orderId, Long amount) throws Exception {
        ConfirmResponse confirmResponse = ConfirmTransaction.process(environment, Long.toString(orderId), Long.toString(orderId), Long.toString(amount), ConfirmRequestType.CAPTURE, "");
        return confirmResponse;
    }

    public ConfirmResponse cancelTransactionMomo(Long orderId, Long amount) throws Exception {
        ConfirmResponse confirmResponse = ConfirmTransaction.process(environment, Long.toString(orderId), Long.toString(orderId), Long.toString(amount), ConfirmRequestType.CANCEL, "");
        return confirmResponse;
    }

    public QueryStatusTransactionResponse queryOrderMoMo(Long orderId) throws Exception {
        QueryStatusTransactionResponse queryStatusTransactionResponse = QueryTransactionStatus.process(environment, String.valueOf(orderId), String.valueOf(orderId));
        return queryStatusTransactionResponse;
    }

    public RefundMoMoResponse refundTrans(CustOrderDTO custOrderDTO, OrderTraceDTO orderTraceDTO) throws Exception {
        QueryStatusTransactionResponse queryStatusTransactionResponse = gson.fromJson(orderTraceDTO.getContent(),QueryStatusTransactionResponse.class);
        if(queryStatusTransactionResponse.getResultCode() == 0){
            RefundMoMoResponse refundMoMoResponse = RefundTransaction.process(environment, Long.toString(orderTraceDTO.getId()), Long.toString(orderTraceDTO.getId()), Long.toString(custOrderDTO.getAmount()), queryStatusTransactionResponse.getTransId(), "");
            return refundMoMoResponse;
        }
        return null;
    }
}
