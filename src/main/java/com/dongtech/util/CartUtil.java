package com.dongtech.util;

import com.dongtech.vo.CarGoods;
import com.dongtech.vo.Cart;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class CartUtil {

    public static final String CART = "cart";

    /**
     * 制作cookie所需value
     *
     * @param cartVos 购物车列表
     * @return 解析为字符串的购物车列表，属性间使用"="相隔，对象间使用"=="相隔
     */
    public static String makeCookieValue(List<Cart> cartVos) {
        StringBuffer buffer_2st = new StringBuffer();
        for (Cart item : cartVos) {
            buffer_2st.append(item.getId() + "=" + item.getType() + "=" + item.getName() + "="
                    + item.getPrice() + "=" + item.getDescription() + "=" + item.getNum() + "="
                    + item.getProduce() + "=" + item.getNumber() + "==");
        }
        return buffer_2st.toString().substring(0, buffer_2st.toString().length() - 2);
    }

    /**
     * 获取cookie中的购物车列表
     *
     * @param response
     * @param request
     * @return 购物车列表
     * @throws UnsupportedEncodingException 抛出异常
     */
    public static List<Cart> getCartInCookie(HttpServletResponse response, HttpServletRequest request) throws
            UnsupportedEncodingException {
        // 定义空的购物车列表
        List<Cart> items = new ArrayList<>();
        String value_1st ;
        // 购物cookie
        Cookie cart_cookie = WebUtil.getCookie(request, CART);
        // 判断cookie是否为空
        if (cart_cookie != null) {
            // 获取cookie中String类型的value,从cookie获取购物车
            value_1st = URLDecoder.decode(cart_cookie.getValue(), "utf-8");
            // 判断value是否为空或者""字符串
            if (value_1st != null && !"".equals(value_1st)) {
                // 解析字符串中的数据为对象并封装至list中返回给上一级
                String[] arr_1st = value_1st.split("==");
                for (String value_2st : arr_1st) {
                    String[] arr_2st = value_2st.split("=");
                    Cart item = new Cart();
                    item.setId(Long.parseLong(arr_2st[0])); //商品id
                    item.setType(arr_2st[1]); //商品类型ID
                    item.setName(arr_2st[2]); //商品名
                    item.setPrice(new BigDecimal(arr_2st[3])); //商品市场价格
                    item.setDescription(arr_2st[4]);//商品详情
                    item.setNum(Integer.parseInt(arr_2st[5]));//加入购物车数量
                    item.setProduce(arr_2st[6]);
                    item.setNumber(arr_2st[7]);
                    items.add(item);
                }
            }
        }
        return items;

    }

    /**
     * 将商品信息复制到购物车对象
     * @param carGoods
     * @return
     */
    public static Cart copyCarGoods(CarGoods carGoods) {
        Cart cart = new Cart();
        cart.setPrice(carGoods.getPrice());
        cart.setId(carGoods.getId());
        cart.setName(carGoods.getName());
        cart.setDescription(carGoods.getDescription());
        cart.setType(carGoods.getType());
        cart.setProduce(carGoods.getProduce());
        cart.setNumber(carGoods.getNumber());
        return cart;
    }

}
