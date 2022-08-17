package com.hust.datn.web.rest;

import com.google.gson.Gson;
import com.hust.datn.config.Constants;
import com.hust.datn.repository.UserRepository;
import com.hust.datn.security.SecurityUtils;
import com.hust.datn.service.*;
import com.hust.datn.service.dto.*;
import com.hust.datn.web.rest.errors.BadRequestAlertException;

import com.mservice.models.PaymentResponse;
import com.mservice.models.RefundMoMoResponse;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST controller for managing {@link com.hust.datn.domain.CustOrder}.
 */
@RestController
@RequestMapping("/api")
public class CustOrderResource {

    private final Logger log = LoggerFactory.getLogger(CustOrderResource.class);

    private static final String ENTITY_NAME = "custOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderTraceService orderTraceService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductAmountService productAmountService;

    @Autowired
    private ProductAmountTraceService productAmountTraceService;

    @Autowired
    private MomoService momoService;

    Gson gson = new Gson();

    private final CustOrderService custOrderService;

    public CustOrderResource(CustOrderService custOrderService) {
        this.custOrderService = custOrderService;
    }

    /**
     * {@code POST  /cust-orders} : Create a new custOrder.
     *
     * @param custOrderDTO the custOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custOrderDTO, or with status {@code 400 (Bad Request)} if the custOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-orders")
    public ResponseEntity<CustOrderDTO> createCustOrder(@RequestBody CustOrderDTO custOrderDTO) throws URISyntaxException {
        log.debug("REST request to save CustOrder : {}", custOrderDTO);
        if (custOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new custOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustOrderDTO result = custOrderService.save(custOrderDTO);
        return ResponseEntity.created(new URI("/api/cust-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody CustOrderDetailDTO custOrderDetailDTO) throws Exception {
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElseThrow(() -> new BadRequestAlertException("User not exists", ENTITY_NAME, "idnull"));
        String userLogin = SecurityUtils.getCurrentUserLogin().orElse(null);
        CartDTO cartDTO = cartService.findOneByUserId(userId).orElseThrow(() -> new BadRequestAlertException("null", ENTITY_NAME, "null"));
        if (custOrderDetailDTO.getInfo() == null ||
            custOrderDetailDTO.getInfo().getId() == null ||
            custOrderDetailDTO.getOrder().getPaymentType() == null ||
            custOrderDetailDTO.getItems() == null ||
            custOrderDetailDTO.getItems().size() == 0 ) {
            throw new BadRequestAlertException("null", ENTITY_NAME, "null");
        }
        //check amount
        List<ProductAmountChangeDTO> productAmountChangeDTOList = custOrderDetailDTO.getItems().stream().map(e -> new ProductAmountChangeDTO(e)).collect(Collectors.toList());
        if(!productAmountService.checkAmount(productAmountChangeDTOList)){
            return ResponseEntity.badRequest().body(new ResponseMessageDTO(0, "Số lượng hàng trong kho không đủ hoặc sản phẩm đã bị ngưng bán"));
        }

        OrderInfoDTO orderInfoDTO = orderInfoService.findOne(custOrderDetailDTO.getInfo().getId()).orElseThrow(() -> new BadRequestAlertException("null",ENTITY_NAME,"null"));

        CustOrderDTO result = custOrderService.save(new CustOrderDTO(custOrderDetailDTO.getInfo().getId(), userId, custOrderDetailDTO.getOrder().getPaymentType(), custOrderDetailDTO.getItems()));
        List<OrderItemDTO> orderItemDTOS = custOrderDetailDTO.getItems().stream().map(e -> new OrderItemDTO(e, result.getId())).collect(Collectors.toList());
        orderItemDTOS = orderItemService.saveAll(orderItemDTOS);
        productAmountTraceService.saveAll(custOrderDetailDTO.getItems().stream().map(e -> new ProductAmountTraceDTO(result.getId(), userLogin, e)).collect(Collectors.toList()));
        custOrderDetailDTO.setInfo(orderInfoDTO);
        custOrderDetailDTO.setItems(orderItemDTOS);
        custOrderDetailDTO.setOrder(result);
        cartItemService.deleteCartItemsByProductId(custOrderDetailDTO.getItems().stream().map(e -> e.getProductId()).collect(Collectors.toList()), cartDTO.getId());

        if(result.getPaymentType() == Constants.PAYMENT_TYPE.MOMO){
            PaymentResponse paymentResponse = momoService.createOrderPayRequest(result);
            custOrderDetailDTO.setPaymentResponse(paymentResponse);
            orderTraceService.save(new OrderTraceDTO(result.getId(), Constants.ORDER_RESULT_MESSAGE.CREATE_ORDER, gson.toJson(paymentResponse), Constants.DANG_THANH_TOAN, "system"));
        } else {
            orderTraceService.save(new OrderTraceDTO(result.getId(), Constants.ORDER_RESULT_MESSAGE.CREATE_ORDER, null, Constants.DANG_XU_LY, "system"));
        }
        return ResponseEntity.created(new URI("/order/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(custOrderDetailDTO);
    }

    /**
     * {@code PUT  /cust-orders} : Updates an existing custOrder.
     *
     * @param custOrderDTO the custOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custOrderDTO,
     * or with status {@code 400 (Bad Request)} if the custOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-orders")
    public ResponseEntity<CustOrderDTO> updateCustOrder(@RequestBody CustOrderDTO custOrderDTO) throws URISyntaxException {
        log.debug("REST request to update CustOrder : {}", custOrderDTO);
        if (custOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustOrderDTO result = custOrderService.save(custOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custOrderDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/orders-detail")
    public ResponseEntity<List<CustOrderDetailDTO>> getAllCustOrdersByUser() {
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElseThrow(() -> new BadRequestAlertException("User not exists", ENTITY_NAME, "idnull"));
        List<CustOrderDTO> result = custOrderService.findAllByUserId(userId);
        List<CustOrderDetailDTO> orderDetailDTOS = result.stream().map(e -> {
            OrderInfoDTO orderInfoDTO = orderInfoService.findOne(e.getOrderInfoId()).orElse(null);
            List<OrderItemDTO> orderItemDTOS = orderItemService.findAllByOrderId(e.getId());
            List<OrderTraceDTO> orderTraceDTOS = orderTraceService.findAllByOrderId(e.getId());
            return new CustOrderDetailDTO(e, orderInfoDTO, orderItemDTOS, orderTraceDTOS);
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(orderDetailDTOS);
    }

    @GetMapping("/order-detail/{id}")
    public ResponseEntity<CustOrderDetailDTO> getCustOrderByUser(@PathVariable() Long id) throws NotFoundException {
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElseThrow(() -> new BadRequestAlertException("User not exists", ENTITY_NAME, "idnull"));
        CustOrderDTO result = custOrderService.findOne(id).orElseThrow(() -> new NotFoundException(ENTITY_NAME + id));
//        if(!result.getUserId().equals(userId)){
//            throw new NotFoundException(ENTITY_NAME + id);
//        }
        OrderInfoDTO orderInfoDTO = orderInfoService.findOne(result.getOrderInfoId()).orElse(null);
        List<OrderItemDTO> orderItemDTOS = orderItemService.findAllByOrderId(result.getId());
        List<OrderTraceDTO> orderTraceDTOS = orderTraceService.findAllByOrderId(result.getId());
        return ResponseEntity.ok().body(new CustOrderDetailDTO(result, orderInfoDTO, orderItemDTOS, orderTraceDTOS));
    }

    @GetMapping("/bot-order-detail/{id}")
    public ResponseEntity<CustOrderDetailDTO> getCustOrderBotByUser(@PathVariable() Long id) throws NotFoundException {
        CustOrderDTO result = custOrderService.findOne(id).orElseThrow(() -> new NotFoundException(ENTITY_NAME + id));
        OrderInfoDTO orderInfoDTO = orderInfoService.findOne(result.getOrderInfoId()).orElse(null);
        List<OrderItemDTO> orderItemDTOS = orderItemService.findAllByOrderId(result.getId());
        List<OrderTraceDTO> orderTraceDTOS = orderTraceService.findAllByOrderId(result.getId());
        return ResponseEntity.ok().body(new CustOrderDetailDTO(result, orderInfoDTO, orderItemDTOS, orderTraceDTOS));
    }

    @PostMapping("/order-detail/query")
    public ResponseEntity<?> queryCustOrder(@RequestBody CustOrderDTO custOrderDTO, Pageable pageable) throws NotFoundException {
        if(custOrderDTO.getId() != null){
            CustOrderDTO result = custOrderService.findOne(custOrderDTO.getId()).orElseThrow(() -> new NotFoundException(ENTITY_NAME + custOrderDTO.getId()));
            return ResponseEntity.ok().body(new ArrayList<>(Arrays.asList(result)));
        }
        Page<CustOrderDTO> page = custOrderService.findAllByState(custOrderDTO.getState(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/order-detail/cancel")
    public ResponseEntity<?> cancelCustOrder(@RequestBody CustOrderDTO custOrderDTO) throws NotFoundException {
        if(custOrderDTO.getId() == null){
            throw new BadRequestAlertException("Id null", "Id null", "Id null");
        }
        CustOrderDTO custOrderDTO1 = custOrderService.findOne(custOrderDTO.getId()).orElseThrow(() -> new NotFoundException(ENTITY_NAME + custOrderDTO.getId()));
        String userLogin = SecurityUtils.getCurrentUserLogin().orElse(null);
        if(Constants.ORDER_STATE_CLIENT_CONDITION.DUOC_HUY.contains(custOrderDTO1.getState())){
            orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), Constants.ORDER_RESULT_MESSAGE.CANCEL_SUCCESS, gson.toJson(custOrderDTO1), Constants.DA_HUY, userLogin));
            return ResponseEntity.ok().body(new ResponseMessageDTO(1, Constants.ORDER_RESULT_MESSAGE.CANCEL_SUCCESS));
        }
        if(Constants.ORDER_STATE_CLIENT_CONDITION.YEU_CAU_HUY.contains(custOrderDTO1.getState())){
            if(custOrderDTO1.getPaymentType().equals(Constants.PAYMENT_TYPE.COD)){
                orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), Constants.ORDER_RESULT_MESSAGE.CANCEL_SUCCESS, gson.toJson(custOrderDTO1), Constants.DA_HUY, userLogin));
            } else {
                orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), Constants.ORDER_RESULT_MESSAGE.CANCEL_REQUEST_SUCCESS, gson.toJson(custOrderDTO1), Constants.YEU_CAU_HUY, userLogin));
            }
            return ResponseEntity.ok().body(new ResponseMessageDTO(1, Constants.ORDER_RESULT_MESSAGE.CANCEL_SUCCESS));
        }
        return ResponseEntity.badRequest().body(new ResponseMessageDTO(0, Constants.ORDER_RESULT_MESSAGE.CANCEL_FAILED));
    }

    @PostMapping("/order-detail/get-next-state")
    public ResponseEntity<?> getNextStateCustOrder(@RequestBody CustOrderDTO custOrderDTO) throws NotFoundException {
        if(custOrderDTO.getId() == null){
            throw new BadRequestAlertException("Id null", "Id null", "Id null");
        }
        CustOrderDTO custOrderDTO1 = custOrderService.findOne(custOrderDTO.getId()).orElseThrow(() -> new NotFoundException(ENTITY_NAME + custOrderDTO.getId()));
        for (Map.Entry<Integer, List<Integer>> set : Constants.NEXT_STATE.entrySet()) {
            if(custOrderDTO1.getState().equals(set.getKey())){
                return ResponseEntity.ok().body(set.getValue());
            }
        }
        return ResponseEntity.badRequest().body(new ResponseMessageDTO(0, "Không có hành động tiếp theo"));
    }

    @PostMapping("/order-detail/next")
    public ResponseEntity<?> handleNextStateCustOrder(@RequestBody OrderTraceDTO orderTraceDTO) throws NotFoundException {
        if(orderTraceDTO.getOrderId() == null ||
            orderTraceDTO.getState() == null ||
            orderTraceDTO.getDescription() == null
        ){
            throw new BadRequestAlertException("Null", "Null", "Null");
        }
        CustOrderDTO custOrderDTO1 = custOrderService.findOne(orderTraceDTO.getOrderId()).orElseThrow(() -> new NotFoundException(ENTITY_NAME + orderTraceDTO.getOrderId()));
        String userLogin = SecurityUtils.getCurrentUserLogin().orElse(null);
        for (Map.Entry<Integer, List<Integer>> set : Constants.NEXT_STATE.entrySet()) {
            if(custOrderDTO1.getState().equals(set.getKey()) && !set.getValue().contains(orderTraceDTO.getState())){
                return ResponseEntity.badRequest().body(new ResponseMessageDTO(0, "Trạng thái không hợp lệ"));
            }
        }
        if(orderTraceDTO.getState().equals(Constants.DA_HUY)){
            OrderTraceDTO res = new OrderTraceDTO();
            switch (custOrderDTO1.getState()){
                case Constants.DA_THANH_TOAN:
                    res = orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), orderTraceDTO.getDescription(), orderTraceDTO.getContent(), Constants.DA_HUY, userLogin));
                    break;
                case Constants.DANG_XU_LY:
                    res = orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), orderTraceDTO.getDescription(), orderTraceDTO.getContent(), Constants.DA_HUY, userLogin));
                    break;
                case Constants.DA_XAC_NHAN:
                    res = orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), orderTraceDTO.getDescription(), orderTraceDTO.getContent(), Constants.DA_HUY, userLogin));
                    break;
                case Constants.DANG_GIAO_HANG:
                    res = orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), orderTraceDTO.getDescription(), orderTraceDTO.getContent(), Constants.DA_HUY, userLogin));
                    break;
                case Constants.YEU_CAU_HUY:
                    //hoan tien
                    RefundMoMoResponse refundMoMoResponse = handleRefund(custOrderDTO1);
                    if(refundMoMoResponse.getResultCode() == 0){
                        res = orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), Constants.ORDER_RESULT_MESSAGE.REFUND_SUCCESS, gson.toJson(refundMoMoResponse), Constants.DA_HUY, userLogin));
                    } else {
                        return ResponseEntity.badRequest().body(refundMoMoResponse);
                    }
                    break;
            }
            return ResponseEntity.ok().body(res);

        }
        OrderTraceDTO res = new OrderTraceDTO();
        switch (custOrderDTO1.getState()){
            case Constants.DA_THANH_TOAN:
                res = orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), orderTraceDTO.getDescription(), orderTraceDTO.getContent(), Constants.DA_XAC_NHAN, userLogin));
                break;
            case Constants.DANG_XU_LY:
                res = orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), orderTraceDTO.getDescription(), orderTraceDTO.getContent(), Constants.DA_XAC_NHAN, userLogin));
                break;
            case Constants.DA_XAC_NHAN:
                res = orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), orderTraceDTO.getDescription(), orderTraceDTO.getContent(), Constants.DANG_GIAO_HANG, userLogin));
                break;
            case Constants.DANG_GIAO_HANG:
                res = orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), orderTraceDTO.getDescription(), orderTraceDTO.getContent(), Constants.DA_GIAO_HANG, userLogin));
                break;
            case Constants.YEU_CAU_HUY:
                res = orderTraceService.save(new OrderTraceDTO(custOrderDTO1.getId(), orderTraceDTO.getDescription(), orderTraceDTO.getContent(), Constants.DANG_XU_LY, userLogin));
                break;
        }
        return ResponseEntity.ok().body(res);
//        return ResponseEntity.badRequest().body(null);
    }

    private RefundMoMoResponse handleRefund(CustOrderDTO oldOrderState){
        OrderTraceDTO orderTraceDTO = orderTraceService.findLastByOrderIdAndState(oldOrderState.getId(), Constants.ORDER_STATE.DA_THANH_TOAN);
        try{
            RefundMoMoResponse refundMoMoResponse = momoService.refundTrans(oldOrderState, orderTraceDTO);
            return refundMoMoResponse;
        } catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    @PostMapping("/order-detail/test")
    public ResponseEntity<?> testMomo(@RequestBody CustOrderDTO custOrderDTO) throws Exception {
        if(custOrderDTO.getId() == null||
            custOrderDTO.getState() == null
        ){
            throw new BadRequestAlertException("Null", "Null", "Null");
        }
        CustOrderDTO custOrderDTO1 = custOrderService.findOne(custOrderDTO.getId()).orElseThrow(() -> new NotFoundException(ENTITY_NAME + custOrderDTO.getId()));
        OrderTraceDTO orderTraceDTO = orderTraceService.findLastByOrderId(custOrderDTO1.getId());
        try{
            momoService.refundTrans(custOrderDTO1, orderTraceDTO);
        } catch (Exception e){
            log.error(e.toString());
        }
        return ResponseEntity.ok().body(null);
    }

    /**
     * {@code GET  /cust-orders} : get all the custOrders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custOrders in body.
     */
    @GetMapping("/cust-orders")
    public ResponseEntity<List<CustOrderDTO>> getAllCustOrders(Pageable pageable) {
        log.debug("REST request to get a page of CustOrders");
        Page<CustOrderDTO> page = custOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cust-orders/:id} : get the "id" custOrder.
     *
     * @param id the id of the custOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-orders/{id}")
    public ResponseEntity<CustOrderDTO> getCustOrder(@PathVariable Long id) {
        log.debug("REST request to get CustOrder : {}", id);
        Optional<CustOrderDTO> custOrderDTO = custOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custOrderDTO);
    }

    /**
     * {@code DELETE  /cust-orders/:id} : delete the "id" custOrder.
     *
     * @param id the id of the custOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-orders/{id}")
    public ResponseEntity<Void> deleteCustOrder(@PathVariable Long id) {
        log.debug("REST request to delete CustOrder : {}", id);
        custOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
