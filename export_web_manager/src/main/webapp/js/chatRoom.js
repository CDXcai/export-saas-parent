$(function () {


    //绑定输入框的enter事件
    $("#sendMessageContext").keydown(function(e){
        if(e.keyCode == 13){
            $("#sendMessage").click();
        }
    });

    //绑定单击事件
    $("#sendMessage").on("click",function () {
        // 获取到聊天框页面
        var $messageBox = $("#messageBox>ul");
        // 获取输入框的内容
       var $text = $("#sendMessageContext").val();
       // 清空输入框的内容
        $("#sendMessageContext").val("");
        // 发送ajax请求
        // url, [data], [callback], [type]
        var $url = "/chatRoom/sendMessage.do";
        var $data = {"context":$text};
        $.post($url,$data,function (data) {
            $messageBox.append(" <li '>\n" +
                "\n" +
                "                    <div class=\"send\">\n" +
                "                        <font style='font-size: 10px;'>"+data['userName']+"   时间：" + data['time']+"</font><br>\n" +
                "                        <div>\n" + data['context']+
                "                        </div>\n" +
                "                    </div>\n" +
                "                </li>");

            // 滚动条到最后
            var $context = $(".context");
            $context.scrollTop($context.scrollTop() + 300);
            // 获取当前信息的个数
            var $messageSzie = $(".context>ul").children("li").length;
            console.log($messageSzie)
            // 如果数量超过10个，则删除前面的消息
            if($messageSzie > 30){
                $(".context>ul").children("li").first().remove();
            }
        },"json");
    });



    // 设置定时任务每秒执行一次
    setInterval(function () {
        // 获取到聊天框页面
        var $messageBox = $("#messageBox>ul");
        // 发送ajax
        // url, [data], [callback], [type]
        var $url = "/chatRoom/receiveMassage.do";
        var $data = {};

        $.post($url,$data,function (data) {
            for(var i=0;i<data.length;i++){
                // 取出当前遍历的元素
                var $content = data[i];

                $messageBox.append(" <li '>\n" +
                    "\n" +
                    "                    <div class=\"receive\">\n" +
                    "                        <font style='font-size: 10px;'>"+$content['userName']+"   时间：" + $content['time']+"</font><br>\n" +
                    "                        <div>\n" + $content['context']+
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </li>");

                // 滚动条到最后
                var $context = $(".context");
                $context.scrollTop($context.scrollTop() + 300);
                // 获取当前信息的个数
                var $messageSzie = $(".context>ul").children("li").length;
                console.log($messageSzie)
                // 如果数量超过10个，则删除前面的消息
                if($messageSzie > 30){
                    $(".context>ul").children("li").first().remove();
                }
            }
        },"json");
        //style='list-style-type:none;



    },1000);

})