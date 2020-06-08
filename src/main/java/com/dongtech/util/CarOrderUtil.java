package com.dongtech.util;

import com.dongtech.dao.CarGoodsDao;
import com.dongtech.dao.impl.CarGoodsDaoImpl;
import com.dongtech.vo.Cart;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gzl
 * @date 2020-04-16
 * @program: springboot-jsp
 * @description: ${description}
 */
public class CarOrderUtil {

    /**
     * 生成一个订单编号，格式：ORD2020060100001
     * @return 订单编号
     */
    public static String createOrderNumber(){
        int beginIndex = 11; //ord+日期共11位
        int numberLength = 5; //订单序号5位数字
        String prefix = "ORD";

        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String time=sdf.format(date);
        prefix += time;

        //查询数据库中最大编号
        CarGoodsDao carGoodsDao = new CarGoodsDaoImpl();
        String maxNumber = carGoodsDao.queryMaxOrderNumber(prefix);

        String orderNumber = maxNumber.substring(beginIndex);
        orderNumber = String.format("%0" + numberLength + "d", Integer.parseInt(orderNumber) + 1);
        return prefix + orderNumber;
    }
}
