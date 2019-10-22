app.controller("categoryCtrl",function(ajaxServices,$scope,$rootScope){
	$scope.categoryHash={};
	$scope.iscatHide = true;
	$scope.iscatUpdateHide=true;
	$rootScope.catList=[];
	$scope.searchKey="";

	$scope.getCategories=function getCategories(){
		ajaxServices.postReq('/app/categories','','',function(resp){
			if(resp.status==200){
				$rootScope.catList = resp.data;
			}
			else
				console.log(resp);
		});
	}
	$scope.getCategories();
	$scope.add=function(){
		$scope.iscatHide = false;
	}
	$scope.close=function(){
		$scope.iscatHide = true;
		$scope.iscatUpdateHide=true;
	}
	$scope.addCategory=function addCategory(){
		ajaxServices.postReq('/app/addCategory',{'Content-Type' : 'application/json'},$scope.categoryHash,function(resp){
			if(resp.status==200){
				$rootScope.notify(resp.data);
				$scope.iscatHide = true;
				$scope.categoryHash={};
				$scope.getCategories();
			}else{
				$scope.categoryHash={};
				console.log(resp);
			}

		});
	}
	$scope.loadSelected=function(obj){
		$scope.categoryHash=obj;
		$scope.iscatUpdateHide = false;
	}
	$scope.update=function(obj)
	{
		ajaxServices.postReq('/app/update/category',{'Content-Type':'application/json'},obj,function(resp){
			if(resp.status==200){
				$rootScope.notify(resp.data);
				$scope.iscatUpdateHide = true;
				$scope.categoryHash={};
				$scope.getCategories();
			}else{
				console.log(resp);
			}
			
		});
		
	}
	$scope.remove= function(obj)
	{
		ajaxServices.postReq('/app/delete/category',{'Content-Type':'application/json'},obj,function(resp){
			if(resp.status==200){
				$rootScope.notify(resp.data);
				$scope.getCategories();
				
			}else{
				console.log(resp);
			}
			
		});
		
	}
	
	
});