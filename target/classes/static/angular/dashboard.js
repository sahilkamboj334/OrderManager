app.controller("dashboard",function($scope,$rootScope,ajaxServices){
	$scope.dashboard=function(){
		console.log("updating");
		ajaxServices.postReq("/app/get/orders",$rootScope.jsonHeader,"",function(resp){
			if(resp.status==200){
				dashboardData(resp.data);
			}else{
				$rootScope.exception(resp);
			}
		});
	}
	$scope.dashboard();
	
	function dashboardData(orderList){
		$scope.totalOrder=0;
		$scope.totalProfitEarned=0;
		$scope.totalPendingPayment=0;
		$scope.totalOrderPending=0;
		$scope.pendingPaymentList=[];
		$scope.totalOrder=orderList.length;
		for(var i=0;i<orderList.length;i++){
			$scope.totalProfitEarned+=orderList[i].orderProfit;
			if(!orderList[i].isPaymentDone){
				$scope.pendingPaymentList.push(orderList[i]);
				$scope.totalPendingPayment+=orderList[i].orderPrice+orderList[i].orderDeliveryCharge;
			}
			if(orderList[i].orderStatus=='Pending'){
				$scope.totalOrderPending++;
			}
		}
	}
	
	$scope.updateOrder=function(obj){
		var order=obj;
		order.isPaymentDone=true;
		ajaxServices.postReq("/app/order/update",$rootScope.jsonHeader,order,function(resp){
			if(resp.status==200){
				$rootScope.notify(resp.data);
				$scope.dashboard();
				console.log("callled");

			}else{
				$rootScope.exception(resp.data);
			}
		});
	}
});

