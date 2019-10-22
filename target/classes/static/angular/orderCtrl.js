app.controller("order",function($scope,ajaxServices,$rootScope,$location,storeSvc){
	$scope.orderedList=[];
	$scope.isDelete=false;
	$scope.selected={};
	$scope.searchKey="";
	$scope.getOrders=function(){
		ajaxServices.postReq('/app/get/orders','','',function(resp){
			if(resp.status==200){
				$scope.orderedList=resp.data;
			}else{
				console.log(resp);
			}
		});
	}
	$scope.getOrders();
	$scope.total=function(order){
		var val=0;
		var list=order.orderDetailsArray;
		for(var i=0;i<list.length;i++){
			val+=list[i].qty;
		}
		return val;
	}
	$scope.confirm=function(obj){
		$scope.selected=obj;
		$scope.isDelete=true;
		
	}
	$scope.remove=function(){
		ajaxServices.postReq("/app/order/delete",$rootScope.jsonHeader,$scope.selected,function(resp){
			if(resp.status==200){
				$rootScope.notify(resp.data);
				$scope.getOrders();
				$scope.isDelete=false;
			}else{
				$rootScope.exception(resp);
			}
		});
		
	}
	$scope.loadSelected=function(obj){
		storeSvc.store(obj);
		$location.path("/orderDetailUpdate");
	}
});