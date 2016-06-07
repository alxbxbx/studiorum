'use strict';

angular.module('studiorum').controller('LoginModalController', ['$http', '$scope', 'user', '$uibModalInstance', '$log', '_',
                         	                            function($http, $scope, user, $uibModalInstance, $log, _) {

	$scope.user = user;

	$scope.ok = function() {
		$http.post('/auth/login', $scope.user).then(function(response) {
			var token = "Bearer " + response.data.token;
			$http.defaults.headers.common.Authorization = token;
			localStorage.setItem("jwt_token", response.data.token);
		}, function(error) {
			$scope.error = error;
			$log.info('Error during loggin in!');
		});
		$uibModalInstance.close('ok');
	};
	
	$scope.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};

}]);
