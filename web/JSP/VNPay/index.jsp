<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Complete order</title>
        <!-- Bootstrap core CSS -->
        <link href="/vnpay_jsp/assets/bootstrap.min.css" rel="stylesheet"/>
        <!-- Custom styles for this template -->
        <link href="/vnpay_jsp/assets/jumbotron-narrow.css" rel="stylesheet">      
        <script src="/vnpay_jsp/assets/jquery-1.11.3.min.js"></script>
    </head>

    <body>

         <div class="container">
           <div class="header clearfix">

                <h3 class="text-muted">Complete order</h3>
            </div>
                <div class="form-group">
                    <button onclick="pay()">Pay with bank account</button><br>
                </div>
                <div class="form-group">
                    <button onclick="window.location.href = '${pageContext.request.contextPath}/setOrder'">Pay when shipped</button><br>
                </div>
            <p>
                &nbsp;
            </p>
        </div> 
        <script>
             function pay() {
              window.location.href = "${pageContext.request.contextPath}/JSP/VNPay/vnpay_pay.jsp?total=${param.total}";
            }
        </script>
    </body>
</html>
