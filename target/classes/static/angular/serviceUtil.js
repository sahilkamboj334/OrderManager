app.service("storeSvc",function(){
	var obj={};
return{
	store : store,
	get :get
};
	function store(object){
		obj = object;
	}
	function get(){
		return obj;
	}

	
});
/*app.service("dataSvc",function(loginSvc){
	this.userPk=function(){
		return loginSvc.getpk();
	};
});*/
app.factory("ajaxServices",['$http','$rootScope',function($http,$rootScope){
	return{
		postReq: function(url,headers,data,callback){
			$rootScope.showLoader=true;
			$http({
				method:'post',
				url:url,
				data:JSON.stringify(data),
				headers:headers
			}).then(function(response){
				callback(response);
				$rootScope.showLoader=false;
			}).catch(function(response){
				console.log(response);
			});

		},
		getReq: function(url,headers,callback){
			$rootScope.showLoader=true;
			$http({
				method:'get',
				url:url,
				headers:headers
			}).then(function(response){
				callback(response);
				$rootScope.showLoader=false;
			}).catch(function(response){
				console.log(response);
			});

		}
	}

}]);
