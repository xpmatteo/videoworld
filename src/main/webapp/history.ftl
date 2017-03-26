 <html>
<head>
    <title>Transaction History</title>
</head>
<body>
    <h1>Transaction History</h1>
    <ul>
    <#list transactions as transaction>
        <li>Transaction on ${transaction.dateTime}<br />
    	Movies Rented:
		<#list transaction.rentals as rental>
			${rental.movie.title}<#if rental_has_next>, </#if>
		</#list>
		</li>
	</#list>
	</ul>
    <br/>
    <h1>Frequent renter points</h1>

    <pre>${frequentRenterPointsStatement}</pre>


    <h1>Purchases</h1>
    The total amount of all your purchases is: $ ${totalAmountCharged}
    <br/>
    <ul>
    <#list transactions as transaction>
    <li>${transaction.dateTime} - Total charges: $ ${transaction.getTotalAmountCharged()}<br />
    </li>
    </#list>
    </ul>
</body>
</html>