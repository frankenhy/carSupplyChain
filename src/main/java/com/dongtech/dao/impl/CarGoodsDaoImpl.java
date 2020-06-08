package com.dongtech.dao.impl;


import com.dongtech.dao.CarGoodsDao;
import com.dongtech.util.JDBCUtil;
import com.dongtech.vo.*;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据层，只负责与数据库的数据交互，将数据进行存储读取操作
 */
public class CarGoodsDaoImpl implements CarGoodsDao {


    @Override
    public List<CarGoods> queryList(CarGoods carGoods) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CarGoods> bookList = new ArrayList<CarGoods>();
        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM cargoods where 1=1 and num>0");
            if(!StringUtils.isEmpty(carGoods.getId())){
                sql.append(" and id =").append(carGoods.getId());
            }
            if(!StringUtils.isEmpty(carGoods.getName())){
                sql.append("  and name like '%").append(carGoods.getName()).append("%'");
            }
            if(!StringUtils.isEmpty(carGoods.getType())){
                sql.append("  and type='").append(carGoods.getType()).append("'");
            }
            //3 操作数据库——查询一条数据记录
            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            //4 处理返回数据——将返回的一条记录封装到一个JavaBean对象
            while (rs.next()) {
                CarGoods vo = new CarGoods(rs.getLong("id"),
                        rs.getString("number"),
                        rs.getString("name"),
                        rs.getString("produce"),
                        rs.getBigDecimal("price"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getInt("num")

                );
                bookList.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(rs, ps, conn);
        }
        return bookList;
    }

    /**
     * @Author gzl
     * @Description：查询订单信息
     * @Exception
     * @Date： 2020/4/20 12:04 AM
     */
    @Override
    public List<CarOrder> queryOrder() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CarOrder> carOrderList = new ArrayList<CarOrder>();
        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM car_orders where 1=1");
            //3 操作数据库——查询一条数据记录
            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            //4 处理返回数据——将返回的一条记录封装到一个JavaBean对象
            while (rs.next()) {
                CarOrder vo = new CarOrder(rs.getLong("id"),
                        rs.getString("number"),
                        rs.getBigDecimal("price"),
                        rs.getInt("teardown_flag")
                );
                carOrderList.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(rs, ps, conn);
        }
        return carOrderList;
    }

    @Override
    public CarOrder queryOrder(String number) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CarOrder carOrder = null;
        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            String sql =  "SELECT * FROM car_orders where number='"+number+"'";
            //3 操作数据库——查询一条数据记录
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            //4 处理返回数据——将返回的一条记录封装到一个JavaBean对象
            while (rs.next()) {
                carOrder = new CarOrder(rs.getLong("id"),
                        rs.getString("number"),
                        rs.getBigDecimal("price"),
                        rs.getInt("teardown_flag")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(rs, ps, conn);
        }
        return carOrder;
    }

    /**
     * @Author gzl
     * @Description：查询订单详情
     * @Exception
     * @Date： 2020/4/20 12:17 AM
     */
    @Override
    public List<CarOrderDetail> queryOrderDetail(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CarOrderDetail> carOrderDetailsList = new ArrayList<CarOrderDetail>();
        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM car_orders_details where 1=1");
            if(!StringUtils.isEmpty(id)){
                sql.append(" and order_id =").append(id);
            }
            //3 操作数据库——查询一条数据记录
            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            //4 处理返回数据——将返回的一条记录封装到一个JavaBean对象
            while (rs.next()) {
                CarOrderDetail vo = new CarOrderDetail(
                        rs.getLong("id"),
                        rs.getString("goods_name"),
                        rs.getInt("num"),
                        rs.getString("produce"),
                        rs.getBigDecimal("price"),
                        rs.getLong("order_id")
                );
                carOrderDetailsList.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(rs, ps, conn);
        }
        return carOrderDetailsList;
    }

    @Override
    public String queryMaxOrderNumber(String prefix) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT max(number) FROM car_orders where number like '"+prefix+"%'";
        String maxNumber = null;

        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                maxNumber = rs.getString(1);
            }
            if(maxNumber==null){
                maxNumber = prefix + "00000";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            JDBCUtil.close(rs, ps, conn);
        }
        return maxNumber;
    }

    @Override
    public boolean saveOrder(CarOrder carOrder) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean result = false;

        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
//            final int[] totalprice = {0};
            String sql = "INSERT INTO car_orders(number, price) VALUES(?,?)";
            ps = conn.prepareStatement(sql);
//            long randomNum = System.currentTimeMillis();
            ps.setString(1, carOrder.getNumber());
            ps.setBigDecimal(2, carOrder.getPrice());
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(ps, conn);
        }
        return result;
    }

    public boolean saveOrderDetail(CarOrderDetail carOrderDetail) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean result = false;

        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
//            final int[] totalprice = {0};
            String sql = "INSERT INTO car_orders_details(goods_name, num, produce, order_id, price)" +
                    " values (?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            long randomNum = System.currentTimeMillis();
            ps.setString(1, carOrderDetail.getGoodsname());
            ps.setInt(2,carOrderDetail.getNum());
            ps.setString(3, carOrderDetail.getProduce());
            ps.setLong(4,carOrderDetail.getOrderId());
            ps.setBigDecimal(5, carOrderDetail.getPrice());
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(ps, conn);
        }
        return result;
    }

    @Override
    public boolean saveOrderDetail(List<CarOrderDetail> carOrderDetails) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean result = false;

        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            String sql = "INSERT INTO car_orders_details(goods_name, num, produce, order_id, price)" +
                    " values (?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            for (CarOrderDetail carOrderDetail:carOrderDetails) {
                ps.setString(1, carOrderDetail.getGoodsname());
                ps.setInt(2, carOrderDetail.getNum());
                ps.setString(3, carOrderDetail.getProduce());
                ps.setLong(4, carOrderDetail.getOrderId());
                ps.setBigDecimal(5, carOrderDetail.getPrice());
                ps.addBatch();
            }
            ps.executeBatch();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(ps, conn);
        }
        return result;
    }

    @Override
    public boolean updateGoodsNum(List<Cart> carts) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean result = false;

        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            String sql = "update cargoods set num=num-? where id=?";
            ps = conn.prepareStatement(sql);
            for (Cart cart:carts) {
                ps.setInt(1, cart.getNum());
                ps.setLong(2, cart.getId());
                ps.addBatch();
            }
            ps.executeBatch();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(ps, conn);
        }
        return result;
    }

    @Override
    public boolean updateOrderTearDownFlag(int orderId) {
        Connection conn = null;
        Statement stmt = null;
        boolean result = false;

        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            stmt = conn.createStatement();
            String sql = "update car_orders set teardown_flag=1 where id="+orderId;
            stmt.executeUpdate(sql);
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(stmt, conn);
        }
        return result;
    }

    @Override
    public boolean saveTearDownDetail(List<TearDownDetail> tearDownDetails) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean result = false;

        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            String sql = "INSERT INTO `tear_down_details`(`order_id`,`produce`,`cargoods_name`,`num`)\n" +
                    "VALUES(?,?,?,?);";
            ps = conn.prepareStatement(sql);
            for (TearDownDetail tearDownDetail:tearDownDetails) {
                ps.setLong(1, tearDownDetail.getOrderId());
                ps.setString(2, tearDownDetail.getProduce());
                ps.setString(3, tearDownDetail.getCargoods_name());
                ps.setInt(4, tearDownDetail.getNum());
                ps.addBatch();
            }
            ps.executeBatch();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(ps, conn);
        }
        return result;
    }

    @Override
    public List<SalesSum> salesSumByGoodsName(String dimension) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SalesSum> salesSums = new ArrayList<>();
        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            String sql = "select  " + dimension + ", sum(price*num) " +
                    "from car_orders_details " +
                    "group by " + dimension + " order by 2 desc";
            //3 操作数据库——查询一条数据记录
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            //4 处理返回数据——将返回的一条记录封装到一个JavaBean对象
            while (rs.next()) {
                SalesSum salesSum = new SalesSum();
                salesSum.setName(rs.getString(1));
                salesSum.setValue(rs.getDouble(2));

                salesSums.add(salesSum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(rs, ps, conn);
        }
        return salesSums;
    }

}
