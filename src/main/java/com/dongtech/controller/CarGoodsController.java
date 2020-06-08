package com.dongtech.controller;

import com.dongtech.service.CarGoodsService;
import com.dongtech.util.CartUtil;
import com.dongtech.util.WebUtil;
import com.dongtech.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gzl
 * @date 2020-04-15
 * @program: springboot-jsp
 * @description: ${description}
 */
@Controller
@RequestMapping("carGoods")
public class CarGoodsController {


    @Resource
    private CarGoodsService carVGoodsService;


    /**
     * @Author gzl
     * @Description：查询商品列表
     * @Exception
     */
    @RequestMapping("/queryList")
    public ModelAndView queryList(CarGoods carGoods)  {
        List<CarGoods> list = new ArrayList<>();
        try {
            list = carVGoodsService.queryList(carGoods);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /**
         * 模型和视图
         * model模型: 模型对象中存放了返回给页面的数据
         * view视图: 视图对象中指定了返回的页面的位置
         */
        ModelAndView modelAndView = new ModelAndView();
        //将返回给页面的数据放入模型和视图对象中
        modelAndView.addObject("list", list);
        //指定返回的页面位置
        modelAndView.setViewName("carGoods/list");
        return modelAndView;
    }


    /**
     * @Author gzl
     * @Description：查询下单列表
     * @Exception
     * @Date： 2020/4/19 11:59 PM
     */
    @RequestMapping("/queryOrder")
    public ModelAndView queryOrder()  {
        List<CarOrder> list =carVGoodsService.queryOrder();
        /**
         * 模型和视图
         * model模型: 模型对象中存放了返回给页面的数据
         * view视图: 视图对象中指定了返回的页面的位置
         */
        ModelAndView modelAndView = new ModelAndView();
        //将返回给页面的数据放入模型和视图对象中
        modelAndView.addObject("list", list);
        //指定返回的页面位置
        modelAndView.setViewName("carGoods/orderList");
        return modelAndView;
    }

    /**
     * @Author gzl
     * @Description：查询下单详情列表
     * @Exception
     * @Date： 2020/4/19 11:59 PM
     */
    @RequestMapping("/queryOrderDetail/{orderId}")
    public ModelAndView queryOrderDetail(@PathVariable Integer orderId)  {
        List<CarOrderDetail> list =carVGoodsService.queryOrderDetail(orderId);
        /**
         * 模型和视图
         * model模型: 模型对象中存放了返回给页面的数据
         * view视图: 视图对象中指定了返回的页面的位置
         */
        ModelAndView modelAndView = new ModelAndView();
        //将返回给页面的数据放入模型和视图对象中
        modelAndView.addObject("list", list);
        //指定返回的页面位置
        modelAndView.setViewName("carGoods/orderDetailList");
        return modelAndView;
    }

    /**
     * 添加商品到购物车
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addGoodsToCart")
    @ResponseBody
    public String addGoodsToCart(int id, HttpServletRequest request, HttpServletResponse response){
//        System.out.println(id);
        String result = "Ok";

        try {
            Cookie cookie;

            //根据id获取商品信息
            CarGoods carGoods = new CarGoods();
            carGoods.setId((long) id);
            List<CarGoods> carGoodsList = carVGoodsService.queryList(carGoods);
            if (carGoodsList.size()<=0){
                return "无库存";
            }
            carGoods = carGoodsList.get(0);

            //从Cookie中获取购物车信息
            List<Cart> cartInCookie= CartUtil.getCartInCookie(response, request);

            int exists = 0;
            //如购物车中商品已存在，先判断库存是否满足，满足则数量+1
            for (Cart cart : cartInCookie) {
                if (cart.getId() == (long) id) {
                    if (cart.getNum() < carGoods.getNum()) {
                        cart.setNum(cart.getNum() + 1);
                        exists = 1;
                        break;
                    } else {
                        return "购买数量大于库存数量";
                    }

                }
            }
            //如购物车中商品不存在，则加入商品
            if (exists == 0) {
                Cart cart = CartUtil.copyCarGoods(carGoods);
                cart.setNum(1);
                cartInCookie.add(cart);
            }

            //Cookie更新购物车
            WebUtil.addCookie(response, CartUtil.CART,
                    URLEncoder.encode(CartUtil.makeCookieValue(cartInCookie), "utf-8"), 60 * 30);

            result = "Ok";
        } catch (Exception e) {
            e.printStackTrace();
            result="Failed";
        }

        return result;
    }

    /**
     * 获取购物车列表
     *
     * @param request
     * @param response
     * @return 购物车列表
     */
    @RequestMapping("/getCart")
    public ModelAndView getCart(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Cart> carts = CartUtil.getCartInCookie(response, request);
            modelAndView.addObject(CartUtil.CART, carts);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("carGoods/cartList");
        return  modelAndView;
    }

    /**
     * 下单
     * @return
     */
    @RequestMapping("/addOrder")
    public ModelAndView addOrder(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();

        try {
            //获取购物车商品
            List<Cart> carts = CartUtil.getCartInCookie(response, request);
            if (carts.size()<=0){
                throw new Exception("购物车为空");
            }
            //下单
            if(!carVGoodsService.saveOrder(carts)){
                throw new Exception("下单失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("error", e.getLocalizedMessage());
            modelAndView.setViewName("/error");
            return modelAndView;
        }

        //清空购物车
        WebUtil.addCookie(response, CartUtil.CART, null, 0);

        modelAndView.setViewName("carGoods/cartList");
        return  modelAndView;
    }

    /**
     * 清空购物车
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("deleteAllCart")
    public ModelAndView deleteAllCart(HttpServletResponse response, HttpServletRequest request){
        WebUtil.addCookie(response, CartUtil.CART, null, 0);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("carGoods/cartList");
        return  modelAndView;

    }

    /**
     * 删除购物车中的商品
     * @param id
     * @param num 删除商品的数量，0为全部
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/delGoodsInCart")
    public ModelAndView delGoodsInCart(int id, int num, HttpServletRequest request, HttpServletResponse response){

        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Cart> cartInCookie = CartUtil.getCartInCookie(response, request);

            //判断删除全部商品还是减少数量
            if (num==0) {
                cartInCookie.removeIf(cart -> cart.getId() == id);
            }
            else{
                cartInCookie.stream().filter(cart -> cart.getId() == id)
                        .forEach(cart -> {
                    cart.setNum(cart.getNum()-1);
                });
            }
            //删除数量为0的商品
            cartInCookie.removeIf(cart -> cart.getNum()==0);

            //若购物车中没有商品，则删除购物车
            if(cartInCookie.size()<=0){
                WebUtil.addCookie(response, CartUtil.CART,
                        null, 0);
            }else{
                WebUtil.addCookie(response, CartUtil.CART,
                        URLEncoder.encode(CartUtil.makeCookieValue(cartInCookie), "utf-8"), 60 * 30);
            }
            modelAndView.addObject(CartUtil.CART, cartInCookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("carGoods/cartList");
        return  modelAndView;
    }

    /**
     * 拆单请求
     * @param orderId
     * @return
     */
    @RequestMapping("/tearDownDetail")
    @ResponseBody
    public String tearDownDetail(int orderId){
        if(carVGoodsService.tearDown(orderId))
            return "Ok";
        else
            return "Failed";
    }

    /**
     * 按商品统计销售额
     * @return
     */
    @RequestMapping("/salesSumByGoods")
    @ResponseBody
    public Object salesSumByGoods(){

        List<SalesSum> salesSums = carVGoodsService.salesSum("goods_name");
        return salesSums;

    }

    /**
     * 按供应商统计销售额
     * @return
     */
    @RequestMapping("/salesSumByProduce")
    @ResponseBody
    public Object salesSumByProduce(){

        List<SalesSum> salesSums = carVGoodsService.salesSum("produce");
        return salesSums;

    }
}
