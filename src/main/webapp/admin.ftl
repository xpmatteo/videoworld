<html>
<head>
    <title>Administration</title>
</head>
<body>
    <h1>Customers</h1>
    <ul>
	<#list users as user>
	<li>${user.name}</li>
	</#list>
	</ul>
    <h1>Promotion</h1>
    <form id="promotion" name="promotion" action="admin">
        <p>Current promotion state:<br/>
        - free extra-days every 3 days rental: ${currentPromotionExtraDays}</br>
        - advertisement text: '${currentPromotionText}'</p>
        <br/>
        Promotion advertisement: <input type="text" size="60" value="Every 3 days rental, a free extra-day is assigned!" name="promotionText">
        <input type="submit" name="StartPromotionBtn" value="Start the Promotion">
        <br/>
        <input type="submit" name="StopPromotionBtn" value="Stop the promotion">
        <br/>
    </form>
</body>
</html>