package com.mservice.shared.utils;

//import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/***
 * @author uyen.tran
 */
public class LogUtils {
     static Logger logger;

    public static void init(){
        logger = LoggerFactory.getLogger(LogUtils.class);
//        BasicConfigurator.configure();
    }

    public static void info(String serviceCode, Object object){
//        logger.info(String.valueOf(new StringBuilder().append("[").append(serviceCode).append("]: ").append(object)));
    }
    public static void info(Object object){
//        logger.info((String) object);
    }

    public static void debug(Object object){
//        logger.debug((String) object);
    }

    public static void error(Object object){
//        logger.error((String) object);
    }

//    public static void error(Object object) {
//        logger.error(object);
//    }

    public static void warn(Object object){
//        logger.warn((String) object);
    }
}
