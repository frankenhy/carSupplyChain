package com.dongtech;

import com.dongtech.controller.CarGoodsController;
import com.dongtech.service.CarGoodsService;
import com.dongtech.service.impl.CarGoodsServiceImpl;
import com.dongtech.util.CarOrderUtil;
import com.dongtech.vo.CarGoods;
import com.dongtech.vo.SalesSum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJspApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    public void contextLoads() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from cargoods");
            CarGoods carGoods = null;
            while (resultSet.next()) {
                carGoods = new CarGoods();
                carGoods.setId(resultSet.getLong("id"));
                carGoods.setName(resultSet.getString("name"));
                System.out.println(carGoods);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
        }

    }

    @Test
    public  void orderNumberTest(){

        CarOrderUtil carOrderUtil = new CarOrderUtil();
        String result = carOrderUtil.createOrderNumber();
        System.out.println(result);
        Assert.assertEquals("ORD2020060400001", result);
    }

    @Test
    public void salesSumTest(){
        CarGoodsService carGoodsService = new CarGoodsServiceImpl();
        System.out.println(carGoodsService.salesSum("goods_name"));
        Assert.assertTrue(true);
    }
}
