app.controller("orderDetail", function($scope, ajaxServices, $rootScope,$location,storeSvc) {
	$scope.orderList = [];
	$scope.manufactureSelect = false;
	$scope.orderHash = {};
	$scope.orderDetail = {};
	$scope.productList = [];
	$scope.today = new Date().toISOString().split("T")[0];
	$scope.manufactureList = [];
	$scope.isDirty = false;
	$scope.getManufactures = function() {
		ajaxServices.getReq('/app/get/active/manufactures', '', function(resp) {
			if (resp.status == 200) {
				$scope.manufactureList = resp.data;
			} else {
				console.log(resp);
			}
		});
	}
	$rootScope.getActiveDistributor();
	$scope.getManufactures();
	$scope.selected = {};
	$scope.onSelectChange = function() {
		var url = "/app/get/products/" + $scope.selected.primaryKey;
		ajaxServices.getReq(url, '', function(resp) {
			if (resp.status == 200) {
				$scope.productList = resp.data;
				$scope.manufactureSelect = true;
			} else {
				console.log(resp);
			}

		});
	}
	
	$scope.addOrder = function() {
		var error={
				"type":"error",
				"message":"Please Select All valid Data.",
				"date":""
		};
		var orderCost = 0;
		var orderPrice = 0;
		if($scope.orderList.length<=0 || $scope.orderHash.distributor==undefined || $scope.orderHash.orderDate==undefined
				|| $scope.orderHash.orderStatus==undefined){
			console.log($scope.orderHash.distributor,""+$scope.orderHash.orderDate)
			$rootScope.notify(error);
			return;
		}
		$scope.orderHash.orderDate = new Date($scope.orderHash.orderDate)
				.toLocaleDateString();
		var qnty = 0;
		for (var i = 0; i < $scope.orderList.length; i++) {
			qnty += $scope.orderList[i].qty;
			orderCost += $scope.orderList[i].cost;
			orderPrice += $scope.orderList[i].price;
		}
		var orderProfit = orderPrice - orderCost;
		$scope.orderHash.orderCost = orderCost;
		$scope.orderHash.orderPrice = orderPrice;
		$scope.orderHash.orderProfit = orderProfit;
		$scope.orderHash.orderDetailsArray = $scope.orderList;
		ajaxServices.postReq("/app/orders/add", {
			'Content-Type' : 'application/json'
		}, $scope.orderHash, function(resp) {
			if (resp.status == 200) {
				$location.path("/orders");
				$rootScope.notify(resp.data);
				$scope.orderHash = {};

			} else {
				console.log(resp);
			}
		});
		$scope.orderList = [];

	}

	$scope.cartValue = 0;

	function updateCartValue() {
		$scope.cartValue = 0;
		for (var i = 0; i < $scope.orderList.length; i++) {
			$scope.cartValue += $scope.orderList[i].cost;
		}
	}
	$scope.addOrderDetail = function(product) {
		var order_detail = {};
		order_detail.product = product;
		var qty = parseInt($("#" + product.productCode).val());
		order_detail.qty = qty;
		if (isNaN(qty)) {
			$scope.isDirty = true;
			return;
		}
		$scope.isDirty = false;
		$("#btn" + product.productCode).attr("disabled", true);
		order_detail.cost = qty * product.productCost;
		order_detail.price = qty * product.productSellingPrice;
		order_detail.profit = order_detail.price - order_detail.cost;
		$scope.orderList.push(order_detail);
		updateCartValue();

	}
	$scope.removeOrder = function(order) {
		$scope.orderList.splice($scope.orderList.indexOf(order), 1);
		updateCartValue();
		$("#btn" + order.product.productCode).attr("disabled", false);
	}
});