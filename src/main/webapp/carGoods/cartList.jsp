<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/list.css">
    <title>购物车</title>
</head>
<script src="/js/jquery-1.8.2.min.js"></script>
<script>
    function addOrder(){
        if( $("#goods")[0]!=undefined ){
            window.location.href='${pageContext.request.contextPath}/carGoods/addOrder';
        }else{
            alert("购物车为空！");
        }
    }

    function clearCart(){
        if( $("#goods")[0]!=undefined ){
            window.location.href='${pageContext.request.contextPath}/carGoods/deleteAllCart';
        }else{
            alert("购物车为空！");
        }
    }

    function deleteGoods(id, num){
        $.post("delGoodsInCart",
            {
                "id":id,
                "num":num
            },
            function(){
                location.reload();
            });
    }
</script>
<body>
<div class="w">
    <header>
        <a href="#" >
            <input type="button" onclick="javascript:window.location.href='${pageContext.request.contextPath}/carGoods/queryList'" value="返回" class="btn">
        </a>
        <a href="#" >
            <input id="addOrder" type="button" onclick="addOrder();" value="下单" class="btn">
        </a>
        <a href="#" >
            <input type="button" onclick="clearCart();" value="清空购物车" class="btn">
        </a>
    </header>
    <div class="list">
        <div class="list-bd">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr>
                    <th width="18%">名称</th>
                    <th width="5%">价格</th>
                    <th width="10%">描述</th>
                    <th width="10%">数量</th>
                    <th width="10%">操作</th>
                </tr>
                <c:forEach var="goods" items="${cart}">
                    <tr id="goods">
                        <td >${goods.name}</td>
                        <td >${goods.price}</td>
                        <td >${goods.description}</td>
                        <td >${goods.num}</td>
                        <td>
                            <a href="#" class="abtn" onclick="deleteGoods(${goods.id}, 1);">删除一件</a>
                            &nbsp<a href="#" class="abtn" onclick="deleteGoods(${goods.id}, 0);">全部删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>