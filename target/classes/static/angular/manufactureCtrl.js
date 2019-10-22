app.controller("manufactureCtrl",function($scope,ajaxServices,$rootScope){
	$scope.list=[];
	$scope.searchKey="";
	$scope.objectHash={};
	$scope.addModalHide=true;
	$scope.updateModalHide=true;
	$scope.getData=function(){
		ajaxServices.getReq('/app/get/manufactures','',function(resp){
			if(resp.status==200){
				$scope.list=resp.data;
			}else{
				console.log(resp);
			}
			
		});
	}
	$scope.getData();
	
	$scope.add=function(){
		$scope.addModalHide = false;
	}
	$scope.close=function(){
		$scope.addModalHide = true;
		$scope.updateModalHide=true;
	}
	
	$scope.addManufacture=function(){
		ajaxServices.postReq('/app/addManufacture',{'Content-Type' : 'application/json'},$scope.objectHash,function(resp){
			if(resp.status==200){
				$rootScope.notify(resp.data);
				$scope.addModalHide = true;
				$scope.objectHash={};
				$scope.getData();
			}else{
				$scope.objectHash={};
				console.log(resp);
			}

		});
	}
	
	
	$scope.loadSelected=function(obj){
		$scope.objectHash=obj;
		$scope.updateModalHide = false;
	}
	$scope.update=function(obj)
	{
		ajaxServices.postReq('/app/update/manufacture',{'Content-Type':'application/json'},obj,function(resp){
			if(resp.status==200){
				$rootScope.notify(resp.data);
				$scope.updateModalHide = true;
				$scope.objectHash={};
				$scope.getData();
			}else{
				console.log(resp);
			}
			
		});
		
	}
});
