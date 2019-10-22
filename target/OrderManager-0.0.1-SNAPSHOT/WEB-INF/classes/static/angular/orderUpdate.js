app.controller("orderUpdate",function(storeSvc,ajaxServices,$scope,$rootScope){
	$scope.orderDtl=storeSvc.get();
	$scope.updateOrder = function() {
		ajaxServices.postReq("/app/order/update",$rootScope.jsonHeader,$scope.orderDtl,function(resp) {
					if (resp.status == 200) {
						$rootScope.notify(resp.data);
					} else {
						$rootScope.exception(resp);
					}
				});
	}
});