<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        $.ajax({
            url: 'Statistics',
            method: 'GET',
            success: function (data) {
                if (data.totalSales !== undefined) {
                $('#totalSales').text(data.totalSales);
            } else {
                $('#totalSales').text('N/A');
            }
            
            if (data.totalExpenses !== undefined) {
                $('#totalExpenses').text(data.totalExpenses);
            } else {
                $('#totalExpenses').text('N/A');
            }
            
            if (data.totalVisitors !== undefined) {
                $('#totalVisitors').text(data.totalVisitors);
            } else {
                $('#totalVisitors').text('N/A');
            }
            
            if (data.totalOrders !== undefined) {
                $('#totalOrders').text(data.totalOrders);
            } else {
                $('#totalOrders').text('N/A');
            }
            },
            error: function () {
               
            }
        });
    });
</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/overview.css">
<div class="overview">
    <div class="overview-item">
        <img src="${pageContext.request.contextPath}/JSP/Dashboard/pic/get.svg" alt="">
        <h2>Total Sales</h2>
        <p>$<span id="totalSales">Loading...</span></p>
    </div>
    <div class="overview-item">
        <img src="${pageContext.request.contextPath}/JSP/Dashboard/pic/out.svg" alt="">
        <h2>Total Expenses</h2>
        <p>$<span id="totalExpenses">Loading...</span></p>
    </div>
    <div class="overview-item">
        <img src="${pageContext.request.contextPath}/JSP/Dashboard/pic/visit.svg" alt="">
        <h2>Total Visitors</h2>
        <p><span id="totalVisitors">Loading...</span></p>
    </div>
    <div class="overview-item">
        <img src="${pageContext.request.contextPath}/JSP/Dashboard/pic/order.svg" alt="">
        <h2>Total Orders</h2>
        <p><span id="totalOrders">Loading...</span></p>
    </div>
</div>
