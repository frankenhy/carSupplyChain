<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/list.css">
    <title>下单列表</title>
</head>
<script src="/js/jquery-1.8.2.min.js"></script>
<script>
    function tearDown(orderId){
        $.post("tearDownDetail",
            {
                "orderId":orderId
            },
            function(data, status){
                alert("拆单: " + data);
                location.reload();
            });
    }
</script>
<body>
<div class="w">
    <div class="list">
        <div class="list-bd">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr>
                    <th width="18%">编号</th>
                    <th width="5%">价格</th>
                    <th width="10%">操作</th>
                    <%--<th width="10%">数量</th>--%>
                </tr>
                <c:forEach var="order" items="${list}">
                    <tr id="order">
                        <td >${order.number}</td>
                        <td >${order.price}</td>
                        <td >
                            <a href="#" class="abtn" onclick="javascript:window.location.href='${pageContext.request.contextPath}/carGoods/queryOrderDetail/${order.id}'">订单详情</a>
                            <c:if test="${order.tearDownFlag==0}">
                                &nbsp;<a href="#" class="abtn" onclick="tearDown(${order.id});">拆单</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>