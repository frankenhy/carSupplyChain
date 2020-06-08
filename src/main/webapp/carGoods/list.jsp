<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/list.css">
    <title>维护图书</title>
</head>
<script src="/js/jquery-1.8.2.min.js"></script>
<script>
    function addToCart(id){
        $.post("addGoodsToCart",
            {
                "id":id
            },
            function(data, status){
                alert("加入购物车: " + data);
                location.reload();
            });
    }
</script>
<body>
<div class="w">
    <header>
        <a href="#" >
            <input type="button" onclick="javascrtpt:window.location.href='${pageContext.request.contextPath}/carGoods/getCart'" value="我的购物车" class="btn">
        </a>
    </header>
    <div class="list">
        <div class="list-bd">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                <thead>
                    <tr>
                        <th width="18%">名称</th>
                        <th width="18%">描述</th>
                        <th width="5%">价格</th>
                        <th width="10%">生产商</th>
                        <th width="9%">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="goods" items="${list}">
                    <tr>
                        <td >${goods.name}</td>
                        <td >${goods.description}</td>
                        <td >${goods.price}</td>
                        <td >${goods.produce}</td>
                        <td ><a href="#" class="abtn" onclick="addToCart(${goods.id});">加入购物车</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>