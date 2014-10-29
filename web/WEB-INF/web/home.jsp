<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>MySWORD Home</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="description" content="MySWORD home page">
    <style type="text/css">
        li {
            margin: 0;
            padding: 0;
            list-style-type: none;
        }

        a, img {
            border: 0;
        }

        img {
            height: 400;
        }

        .roundabout-holder {
            width: 700px;
            height: 400px;
            margin: 0 auto;
        }

        .roundabout-moveable-item {
            width: 800px;
            height: 400px;
            cursor: pointer;
            border: 3px solid #ccc;
            border: 3px solid rgba(0, 0, 0, 0.08);
            border-radius: 4px;
            -moz-border-radius: 4px;
            -webkit-border-radius: 4px;
        }

        .roundabout-moveable-item img {
            width: 100%;
        }

        .roundabout-in-focus {
            border: 3px solid rgba(0, 0, 0, 0.2);
        }
    </style>
    <script type="text/javascript" src="js/jquery/jquery.js"></script>
    <script type="text/javascript" src="js/3D/jquery.roundabout.js"></script>
    <script type="text/javascript" src="js/3D/jquery.roundabout-shapes.js"></SCRIPT>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#myroundabout').roundabout({
                autoplay: true,//自动播放
                autoplayDuration: 3000,//时间
                autoplayPauseOnHover: true,//自动鼠标移上停滞
                shape: 'square',  //支持属性theJuggler、figure8、waterWheel、square、conveyorBeltLeft、conveyorBeltRight、goodbyeCruelWorld、diagonalRingLeft、diagonalRingRight、rollerCoaster、tearDrop、tickingClock、flurry、nowSlide、risingEssence随便换
                minOpacity: 1,
                autoplayCallback: function() {
//                    switch ($('.roundabout-in-focus')[0].id) {
//                        case "cei" :
//                            $('#description').html("This is the SSC CEI picture!");
//                            break;
//                        case "datamapper" :
//                            $('#description').html("This is the Axway development tool Datamapper!");
//                            break;
//                    }
                },
                clickToFocusCallback: function() {}
            });
            window.frameElement.height = $('body').height() + 20;
        });
    </script>
</head>
<body>
<div style="height: 50px;"></div>
<ul class="roundabout" id="myroundabout">
    <%--<li id="cei"><img id="asdfasfsd" src="image/home/C_EI.jpg"></li>--%>
    <li id="datamapper"><a href="http://logistics.intranet.dbschenker.com/ho-intra/start/" target="_blank"><img src="image/home/Intranet.jpg"></a></li>
    <li id="swing"><a href="https://xibapdev.dc.signintra.com:8443" target="_blank"><img src="image/home/SystemOverview.jpg"></a></li>
    <li id="sword"><a href="https://xibapdev.dc.signintra.com:8443/EDIConf/" target="_blank"><img src="image/home/SWORD.jpg"></a></li>
</ul>
<div id="description"></div>
</body>
</html>
