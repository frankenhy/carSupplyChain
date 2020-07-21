package com.dongtech.dao;


import com.dongtech.vo.*;

import java.util.List;

public interface CarGoodsDao {

    /**
     * 查询商品清单
     * @param carGoods
     * @return
     */
    List<CarGoods> queryList(CarGoods carGoods);

    /**
     * 查询所有订单
     * @return
     */
    List<CarOrder> queryOrder();

    /**
     * 根据订单编号查询订单
     * @param number
     * @return
     */
    CarOrder queryOrder(String number);

    /**
     * 查询订单明细
     * @param id
     * @return
     */
    List<CarOrderDetail> queryOrderDetail(Integer id);

    /**
     * 查询最大订单号
     * @param prefix
     * @return
     */
    String queryMaxOrderNumber(String prefix);

    /**
     * 保存订单
     * @param carOrder
     * @return
     */
    boolean saveOrder(CarOrder carOrder);

    boolean saveOrderDetail(CarOrderDetail carOrderDetail);

    /**
     * 保存订单明细
     * @param carOrderDetails
     * @return
     */
    boolean saveOrderDetail(List<CarOrderDetail> carOrderDetails);

    /**
     * 更新商品库存数量
     * @param carts
     * @return
     */
    boolean updateGoodsNum(List<Cart> carts);

    /**
     * 更新订单的拆单标识
     * @param orderId
     * @return
     */
    boolean updateOrderTearDownFlag(int orderId);


    /**
     * 保存拆单
     * @param tearDownDetails
     * @return
     */
    boolean saveTearDownDetail(List<TearDownDetail> tearDownDetails);


    /**
     * 按指定维度统计销售额
     * @return
     */
    List<SalesSum> salesSumByGoodsName(String dimension);

}
