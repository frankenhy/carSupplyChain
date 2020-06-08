package com.dongtech.service;

import com.dongtech.vo.*;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public interface CarGoodsService {

    /**
     * 查询商品清单
     * @param carGoods
     * @return
     * @throws SQLException
     */
    List<CarGoods> queryList(CarGoods carGoods) throws SQLException;

    /**
     * 查询订单
     * @return
     */
    List<CarOrder> queryOrder();

    /**
     * 查询订单明细
     * @param id
     * @return
     */
    List<CarOrderDetail> queryOrderDetail(Integer id);

    /**
     * 购物车下单
     * @param carts
     * @return
     */
    boolean saveOrder(List<Cart> carts);

    /**
     * 对订单进行拆单
     * @param orderId
     * @return
     */
    public boolean tearDown(int orderId);

    /**
     * 按维度统计销售额
     * @param dimension
     * @return
     */
    List<SalesSum> salesSum(String dimension);
}
