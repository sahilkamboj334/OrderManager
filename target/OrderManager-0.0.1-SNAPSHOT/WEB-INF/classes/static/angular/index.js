var app = angular.module("myapp", [ "ngRoute", "angular-growl"]);
app.config(function($routeProvider, $locationProvider) {
	$routeProvider.when("/productcat", {
		templateUrl : "/app/productcat"
	}).when("/product", {
		templateUrl : "/app/products"
	}).when("/orderAdd", {
		templateUrl : "/app/orderPage"
	}).when("/orders", {
		templateUrl : "/app/orders"
	}).when("/manufactures", {
		templateUrl : "/app/manufacture"
	}).when("/distributors", {
		templateUrl : "/app/distributors"
	}).when("/dashboard", {
		templateUrl : "/app/dashboard"
	}).when("/orderDetailUpdate", {
		templateUrl : "/app/orderDetail/update"
	}).otherwise({
		redirectTo : "/dashboard"
	});
	$locationProvider.html5Mode(true);

});
app.config([ 'growlProvider', function(growlProvider) {
	growlProvider.globalTimeToLive(2000);
	growlProvider.globalDisableCountDown(true);
	growlProvider.globalPosition("bottom-right");
} ]);

app.controller("homeCtrl", function($scope, ajaxServices, $rootScope, growl,
		$location,$window) {
/*	console.log(dataSvc.userPk(),"///////////");*/
	$scope.searchKey="";
	$rootScope.showLoader = false;
	$rootScope.distributorList = [];
	$rootScope.errorMsg = "Something Went Wrong!";
	$scope.isDistributorsHide = true;
	$scope.isUpdateDistributorsHide = true;
	$rootScope.jsonHeader = {
		"Content-Type" : "application/json"
	};
	$scope.distributorHash = {};
	$rootScope.exception = function(obj) {
		growl.error(obj.data, {
			title : 'Error!'
		})
	}
	setInterval(function() {
		document.getElementById("time").innerHTML = new Date()
				.toLocaleTimeString();
	}, 1000);
	$rootScope.notify = function(notifyObj) {
		if (notifyObj.type == 'warning') {
			growl.warning(notifyObj.message, {
				title : 'Warning!'
			});
		} else if (notifyObj.type == 'success') {
			growl.success(notifyObj.message, {
				title : 'Success!'
			});
		} else if (notifyObj.type == 'error') {
			growl.error(notifyObj.message, {
				title : 'Error!'
			});
		}
	}

	$rootScope.getDistributors = function getDistributors() {
		ajaxServices.postReq('/app/get/distributors', '', '', function(resp) {
			if (resp.status == 200) {
				$rootScope.distributorList = resp.data;
			} else
				console.log(resp);
		});
	}

	$scope.logout = function() {
		ajaxServices.postReq("/app/logout", '', '', function(resp) {
			console.log(resp.data);
			if (resp.status == 200) {
				$window.location.href=resp.data;
			} else {
				console.log(resp.data);
			}
		});
	}
	$rootScope.activeDistributors = [];
	$rootScope.getActiveDistributor = function() {
		ajaxServices.getReq('/app/get/active/distributors', '', function(resp) {
			if (resp.status == 200) {
				$rootScope.activeDistributors = resp.data;
			} else
				console.log(resp);
		});
	}
	$scope.navigate = function(url) {
		document.location.href = url;
	}

	$scope.addModel = function(el) {
		if (el == 'distributor')
			$scope.isDistributorsHide = false;
	}

	$scope.close = function close() {
		$scope.isDistributorsHide = true;
		$scope.isUpdateDistributorsHide = true;
	}

	$scope.addDistributors = function addDistributors() {
		ajaxServices.postReq('/app/addDistributor', {
			'Content-Type' : 'application/json'
		}, $scope.distributorHash, function(resp) {
			if (resp.status == 200) {
				$rootScope.notify(resp.data);
				$scope.isDistributorsHide = true;
				$scope.distributorHash = {};
				$rootScope.getDistributors();
			} else {
				$scope.distributorHash = {};
				console.log(resp);
			}

		});
	}
	$scope.loadSelected = function(obj) {
		$scope.distributorHash = obj;
		$scope.isUpdateDistributorsHide = false;
	}
	$scope.update = function(obj) {
		ajaxServices.postReq('/app/update/distributor', {
			'Content-Type' : 'application/json'
		}, obj, function(resp) {
			if (resp.status == 200) {
				$rootScope.notify(resp.data);
				$scope.isUpdateDistributorsHide = true;
				$scope.distributorHash = {};
				$rootScope.getDistributors();
			} else {
				console.log(resp);
			}

		});

	}

});