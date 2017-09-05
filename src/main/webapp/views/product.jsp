<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>${Product.name } page</title>
<link type="text/css" rel="stylesheet" href="./style/main.css" />
<body>
	<h2>Super shop</h2>
	<div class="prodpage">
		<h2>Super ${Product.name } page</h2>
		<p>
			Price: <span style="color: green">${Product.price }</span>
		</p>
		<p>
			In stock: <span style="color: blue">${Product.count }</span>
		</p>
		<div style="padding-bottom: 5px;">
			<a href="cart?name=${Product.name}&id=${Product.id }&cart=add"><input
				type="button" value="Add to Cart" /></a>
		</div>
	</div>
	<div class="cart">
		<h2>Your Cart</h2>
		<c:forEach items="${Cart}" var="item">
			<div style="padding-bottom: 5px;">
				<span>${item.key}</span>: ${item.value} <a
					href="cart?name=${item.key }&cart=remove&id=${Product.id}"><input
					type="button" value="Remove" /></a>
			</div>
		</c:forEach>
		<div style="padding-bottom: 7px;">
			<a href="cart?cart=clear&id=${Product.id}"><input type="button"
				value="Clear" /></a>
		</div>
		<div style="padding-bottom: 5px;">
			<a href="purchase"><input type="button" value="Purchase" /></a>
		</div>
    </div>
	<a href="./">Back to Mainpage</a>
</body>
</html>
