app.controller("productCtrl",function($scope,ajaxServices,$rootScope){
	$scope.productList=[];
	$scope.objectHash = {};
	$scope.searchKey="";
	$scope.isProductHide = true;
	$scope.manufactureList=[];
	$scope.selected="2";
	$scope.load=function(){
		ajaxServices.getReq("/app/get/products","",function(resp){
			if(resp.status==200){
				$scope.productList=resp.data;
			}
			else{
				console.log("Something wrong happen"+resp);
			}
		});
		ajaxServices.getReq('/app/get/active/manufactures','',function(resp){
			$scope.manufactureList= resp.data;
		});
	}
	$scope.load();
	$scope.add = function add() {
		ajaxServices.postReq('/app/addProduct',{'Content-Type' : 'application/json'},$scope.objectHash,function(resp){
			if(resp.status===200){
				$rootScope.notify(resp.data);
				$scope.isProductHide = true;
				$scope.load();
				$scope.objectHash={};
			}else{
				$scope.objectHash={};
				console.log(resp);
			}

		});
	}
	$scope.update = function update() {
		console.log($scope.updateHash,"===");
		
		ajaxServices.postReq('/app/update/product',{'Content-Type' : 'application/json'},$scope.updateHash,function(resp){
			if(resp.status===200){
				$rootScope.notify(resp.data);
				$scope.isUpdateProductHide = true;
				$scope.load();
				$scope.updateHash={};
			}else{
				$scope.objectHash={};
				console.log(resp);
			}

		});
	}
	$scope.catList=[];
	$scope.getCategories=function getCategories(){
		ajaxServices.postReq('/app/categories','','',function(resp){
			if(resp.status==200){
				$scope.catList = resp.data;
			}
			else
				console.log(resp);
		});
	}
	$scope.getCategories();
	$scope.updateHash={};
	$scope.isUpdateProductHide=true;
	$scope.loadSelected=function(product){
		$scope.updateHash=product;
		console.log(product);
		$scope.isUpdateProductHide=false;
	}
	$scope.addModal=function(){
		$scope.isProductHide = false;
	}
	$scope.close=function(){
		$scope.isProductHide = true;
		$scope.isUpdateProductHide=true;
		$rootScope.hide=true;
	}
	$scope.onFileChanged = function(event) {
		console.log('Image Uploading');
		readFiles(event.files, function() {
		});
	};

	function readFiles(files, callback) {
		var reader = new FileReader();
		function readFile(index) {
			if (index >= files.length) {
				callback()
			}
			var file = files[index];
			if (file.type.indexOf("image") == -1) {
				notif({
					msg : "Unsupported file format.",
					type : "error"
				});
				return;
			}
			reader.onload = function(e) {
				var icalStr = e.target.result;
				$scope.objectHash.productImage = icalStr.split("base64,")[1]
				console.log($scope.objectHash.productImage);
			};
			reader.readAsDataURL(file);
		}
		readFile(0);
	}
});
