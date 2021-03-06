<html>
<head>
    <title>Transaction History</title>
</head>
<body>
  <h1>Transaction History</h1>
  <#if transactions?size == 0>
    <p class='message'>No transactions</p>    
  </#if>
  <ul class='history'>
    <#list transactions as transaction>
    	<li>Transaction on ${transaction.dateTime}<br />
      	Movies Rented:
      	<#list transaction.rentals as rental>
      		${rental.movie.title}<#if rental_has_next>, </#if>
      	</#list>
    	</li>
  	</#list>
	</ul>
</body>
</html>