'use strict';

angular.module('studiorum').controller('AppController', ['authService', function (authService) {
	
	$scope.token = null;
	$scope.error = null;
	$scope.roleOne = false;
	$scope.roleTwo = false;
	
	$scope.login = function() {
		$scope.error = null;
		authService.login('?_USERNAME_?').then(function(token) {
			$scope.token = token;
			$http.defaults.headers.common.Authorization = '?_USERNAME_?' + token;
			$scope.checkRoles();
		}, function(error) {
			$scope.error = error;
			// '?_USERNAME_?' = '';
		});
	}
	
	$scope.checkRoles = function() {
		authService.hasRole('roleOne').then(function(roleOne) {
			$scope.roleOne = roleOne;
		});
		authService.hasRole('roleTwo').then(function(roleTwo) {
			$scope.roleTwo = roleTwo;
		});
	}
	
	$scope.logout = function() {
		$scope.userName = '';
		$scope.token = null;
		$http.defaults.headers.common.Authorization = '';
	}
	
}]);
