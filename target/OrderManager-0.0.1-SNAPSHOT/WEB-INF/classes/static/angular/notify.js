app.controller("notify",['Notification',function($rootScope,Notification){
	
	$rootScope.notifySuccess=function(msg){
		Notification.success(msg);
	}
	$rootScope.notifyError=function(msg){
		Notification.error(msg);
	}
	
}]);
