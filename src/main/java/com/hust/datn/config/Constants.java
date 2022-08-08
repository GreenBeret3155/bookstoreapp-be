package com.hust.datn.config;

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
    }

    public interface ORDER_STATE {
        Integer DANG_THANH_TOAN = 0;
        Integer DANG_XU_LY = 1;
        Integer DA_THANH_TOAN = 2;
        Integer DA_HUY = 3;
    }
}
