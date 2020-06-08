<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>第一个 ECharts 实例</title>
    <!-- 引入 echarts.js -->
    <script src="https://cdn.staticfile.org/echarts/4.3.0/echarts.min.js"></script>
</head>
<script src="/js/jquery-1.8.2.min.js"></script>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>

<div id="main1" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option1 = {
        title: {
            text: '商品销售额'
        },
        tooltip: {},
        legend: {
            data:['销售额']
        },
        xAxis: {
        },
        yAxis: {},
        series: [{
            name: '销售额',
            type: 'bar'
            }]
    };

    var arrayGoods = new Array();//X轴
    $.ajax({
        url:"/carGoods/salesSumByGoods",
        type:"Get",
        dataType:"json",
        success:function(data){
            console.log(data);
            for(var i = 0; i < data.length; i++){
                arrayGoods.push(data[i].name);//存入标题
            }

            option1.xAxis.data=arrayGoods;
            option1.series[0].data=data;
            // 使用刚指定的配置项和数据显示图表。
            myChart1.setOption(option1);
        }
    });



    var myChart2 = echarts.init(document.getElementById('main1'));
    option2 = {
        title : {
            text: '按供应商统计销售额',       //大标题

            x:'center'                 //标题位置   居中
        },
        tooltip : {
            trigger: 'item',           //数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
            formatter: "{a} <br/>{b} : {c} ({d}%)"   //{a}（系列名称），{b}（数据项名称），{c}（数值）, {d}（百分比）用于鼠标悬浮时对应的显示格式和内容
        },
        legend: {                           //图例组件。
            orient: 'vertical',             //图例列表的布局朝向
            left: 'left'
        },
        series : [              //系列列表。每个系列通过 type 决定自己的图表类型
            {
                name: '销售额',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    var arrayProduce = new Array();//legend
    $.ajax({
        url:"/carGoods/salesSumByProduce",
        type:"Get",
        dataType:"json",
        success:function(data){
            console.log(data);
            for(var i = 0; i < data.length; i++){
                arrayProduce.push(data[i].name);//存入标题
            }

            option2.legend.data=arrayProduce;
            option2.series[0].data=data;
            // 使用刚指定的配置项和数据显示图表。
            myChart2.setOption(option2);
        }
    });
</script>
</body>
</html>