<!DOCTYPE html>
<html>
<head>
    <title>${title!"Video World"}</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"></link>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
	  	google.load("jquery", "1.4");
  	</script>
</head>
<body>
	<div id="header">
		<h2>Video Rental</h2>
		<a href="/logout" class="tab">Logout</a>
		<a href="rentals" class="tab">Current Rentals</a>
		<a href="history" class="tab">History</a>
		<a href="/" class="tab">Rent Movies</a>
	</div>
	${body}
	<div id="footer">
	</div>
</body>
</html>
