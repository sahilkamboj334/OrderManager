"use strict";
var loginModule=angular.module("loginApp",['angular-growl','ngRoute']);
loginModule.config(['growlProvider', function(growlProvider) {
	growlProvider.globalTimeToLive(2000);
	growlProvider.globalDisableCountDown(true);
	growlProvider.globalPosition("top-center");
}]);
/*loginModule.service("loginSvc",function(){
	let pk;
	this.setPk=function(PK){
		pk=PK;
	};
	this.getpk=function(){
		return pk;
	};
});*/
loginModule.config(function($routeProvider,$locationProvider){
	$routeProvider.when("/signup", {
		templateUrl : "/signup-page"
	}).when("/signin", {
		templateUrl : "/signin-page"
	}).otherwise({
		redirectTo : "/signin"
	});
	$locationProvider.html5Mode(true);
});
loginModule.controller("login",function($rootScope,$scope,$window,$http,growl,$location){
	$scope.user={};
	$rootScope.pk;
	$scope.newuser={};
	$scope.validate=function(){
		var obj=$scope.user;
		if(isNaN(obj.phoneNumber) || obj.password==undefined){
			notify({'message':'Please Enter login credentials.','type':'error'});
			return;
		}
		postAjax("/validate",$scope.user,{"Content-Type":"application/json"},function(resp){
			if(resp.data.type==='success'){
				/*loginSvc.setPk(resp.data.authUserPk);*/
				$window.location.href="/app/home";
			}else{
				notify(resp.data);
			}

		});

	}
	$scope.navigate=function(path){
		$location.path(path);
	}
	function notify(obj){
		if(obj.type=='error'){
			growl.error(obj.message,{
				title : 'Error!'
			});
		}else if(obj.type=='success'){
			growl.success(obj.message,{
				title : 'Success!'
			});
		}
	}
	$scope.signup=function(){
		var obj=$scope.newuser;
		if(obj.name==undefined || isNaN(obj.phoneNumber) || obj.password==undefined){
			notify({'message':'Please fill required data.','type':'error'});
			return;
		}
		postAjax("/register",$scope.newuser,{"Content-Type":"application/json"},function(resp){
			if(resp.status==200){
				notify(resp.data);
			}else{
				notify(resp.data);
			}

		});
	}
	let postAjax=(url,data,headers,callback)=>{
		$http({
			method:'post',
			url:url,
			data:JSON.stringify(data),
			headers:headers
		}).then(function(response){
			callback(response);
		}).catch(function(response){
			console.log(response);
		});
	}
});
