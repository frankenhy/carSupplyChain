package com.dongtech.service.impl;


import com.dongtech.dao.CarGoodsDao;
import com.dongtech.dao.impl.CarGoodsDaoImpl;
import com.dongtech.service.CarGoodsService;
import com.dongtech.util.CarOrderUtil;
import com.dongtech.vo.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarGoodsServiceImpl implements CarGoodsService {

    CarGoodsDao dao = new CarGoodsDaoImpl();


    @Override
    public List<CarGoods> queryList(CarGoods carGoods) throws SQLException {
        return dao.queryList(carGoods);
    }

    @Override
    public List<CarOrder> queryOrder() {
        return dao.queryOrder();
    }

    @Override
    public List<CarOrderDetail> queryOrderDetail(Integer id) {
        return dao.queryOrderDetail(id);
    }

    @Override
    public boolean saveOrder(List<Cart> carts) {
        //生成订单号
        String orderNumber = CarOrderUtil.createOrderNumber();

        //计算金额
        double totalAmount = carts.stream().mapToDouble(cart -> cart.getPrice().doubleValue() * cart.getNum()).sum();
//        System.out.println(totalAmount);

        //新增订单
        CarOrder carOrder = new CarOrder(null, orderNumber, BigDecimal.valueOf(totalAmount), 0);
        boolean result = dao.saveOrder(carOrder);
        if (!result){
            return false;
        }
        carOrder = dao.queryOrder(orderNumber);

        //新增订单明细
        List<CarOrderDetail> carOrderDetails = new ArrayList<>();
        for (Cart cart : carts) {
            CarOrderDetail carOrderDetail = new CarOrderDetail(null, cart.getName(), cart.getNum(),
                    cart.getProduce(), cart.getPrice(), carOrder.getId());
            carOrderDetails.add(carOrderDetail);
        }
        if(!dao.saveOrderDetail(carOrderDetails)){
            return false;
        }

        //修改库存
        return dao.updateGoodsNum(carts);
    }

    @Override
    public boolean tearDown(int orderId){
        //获取订单明细
        List<CarOrderDetail> carOrderDetails = queryOrderDetail(orderId);

        List<TearDownDetail> tearDownDetails = new ArrayList<>();

        //拆单
        for(CarOrderDetail carOrderDetail:carOrderDetails){
            TearDownDetail tearDownDetail = new TearDownDetail();
            tearDownDetail.setCargoods_name(carOrderDetail.getGoodsname());
            tearDownDetail.setOrderId(carOrderDetail.getOrderId());
            tearDownDetail.setNum(carOrderDetail.getNum());
            tearDownDetail.setProduce(carOrderDetail.getProduce());
            tearDownDetails.add(tearDownDetail);
        }
        if(!dao.saveTearDownDetail(tearDownDetails)){
            return false;
        }

        //更新订单拆单标识
        return dao.updateOrderTearDownFlag(orderId);

    }

    @Override
    public List<SalesSum> salesSum(String dimension) {
        return dao.salesSumByGoodsName(dimension);
    }
}
