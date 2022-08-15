package com.hust.datn.config;

import java.util.*;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymous";

    public interface ES {
        String BASE_RPT_GRAPH = "base-rpt-graph";
        String OBJ_RPT_GRAPH = "obj-rpt-graph";
        String TYPE = "_doc";
        String PRODUCT_INDEX = "sync_product_2";
        String AUTHOR_INDEX = "sync_author";
        String CATEGORY_INDEX = "sync_category";
        Integer PRODUCT_SEARCH_TYPE = 1;
        Integer AUTHOR_SEARCH_TYPE = 2;
        Integer CATEGORY_SEARCH_TYPE = 3;
    }

    private Constants() {
    }

    public interface NOTIFICATION_TYPE {
        Integer NEW_ROOM = 1;
        Integer UPDATE_ROOM = 2;
    }

    public interface PAYMENT_TYPE {
        Integer COD = 1;
        Integer VNPAY = 2;
        Integer MOMO = 3;
    }

    public interface ORDER_RESULT_MESSAGE {
        String PAY_SUCCESS = "Thanh toán thành công";
        String PAY_FAILED = "Thanh toán thất bại";
        String CREATE_ORDER = "Tạo đơn hàng thành công";
        String CANCEL_SUCCESS = "Hủy đơn hàng thành công";
        String CANCEL_FAILED = "Hủy đơn hàng thất bại";
        String CANCEL_REQUEST_SUCCESS = "Tạo yêu cầu hủy đơn hàng thành công";
        String HANDLE_ORDER_SUCCESS = "Đơn hàng được xác nhận, đang xử lý giao hàng";
        String PROCESSING_ORDER = "Đơn hàng đang được xử lý";
        String DELIVERY_ORDER_PROCESS = "Đơn hàng đang được giao";
        String DELIVERY_ORDER_SUCCESS = "Đơn hàng đã được giao";
        String REFUND_SUCCESS = "Đơn hàng được hoàn tiền thành công";
    }

    public interface ORDER_STATE {
        Integer DANG_THANH_TOAN = 0;
        Integer DANG_XU_LY = 1;
        Integer DA_THANH_TOAN = 2;
        Integer DA_HUY = 3;
        Integer DA_XAC_NHAN = 4;
        Integer DANG_GIAO_HANG = 5;
        Integer DA_GIAO_HANG = 6;
        Integer YEU_CAU_HUY = 7;
    }

    public static final int DANG_THANH_TOAN = 0;
    public static final int DANG_XU_LY = 1;
    public static final int DA_THANH_TOAN = 2;
    public static final int DA_HUY = 3;
    public static final int DA_XAC_NHAN = 4;
    public static final int DANG_GIAO_HANG = 5;
    public static final int DA_GIAO_HANG = 6;
    public static final int YEU_CAU_HUY = 7;

    public static final Map<Integer, List<Integer>> NEXT_STATE  = new HashMap<Integer, List<Integer>>() {{
        put(Constants.ORDER_STATE.DA_THANH_TOAN, new ArrayList<>(Arrays.asList(Constants.ORDER_STATE.DA_XAC_NHAN, Constants.ORDER_STATE.DA_HUY)));
        put(Constants.ORDER_STATE.DANG_XU_LY, new ArrayList<>(Arrays.asList(Constants.ORDER_STATE.DA_XAC_NHAN, Constants.ORDER_STATE.DA_HUY)));
        put(Constants.ORDER_STATE.DA_XAC_NHAN, new ArrayList<>(Arrays.asList(Constants.ORDER_STATE.DANG_GIAO_HANG, Constants.ORDER_STATE.DA_HUY)));
        put(Constants.ORDER_STATE.DANG_GIAO_HANG, new ArrayList<>(Arrays.asList(Constants.ORDER_STATE.DA_GIAO_HANG, Constants.ORDER_STATE.DA_HUY)));
        put(Constants.ORDER_STATE.YEU_CAU_HUY, new ArrayList<>(Arrays.asList(Constants.ORDER_STATE.DA_HUY, Constants.ORDER_STATE.DANG_XU_LY)));
    }};

    public interface ORDER_STATE_CLIENT_CONDITION {
        List<Integer> DUOC_HUY = new ArrayList<>(Arrays.asList(ORDER_STATE.DANG_THANH_TOAN, ORDER_STATE.DANG_XU_LY));
        List<Integer> YEU_CAU_HUY = new ArrayList<>(Arrays.asList(ORDER_STATE.DA_THANH_TOAN));
    }


}
