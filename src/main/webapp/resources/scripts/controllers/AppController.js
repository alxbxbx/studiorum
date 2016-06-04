'use strict';

angular.module('studiorum').controller('AppController', ['$http', '$scope', 'authService', 'Restangular', '$uibModal', '$log', '_', 
                                                         function ($http, $scope, authService, Restangular, $uibModal, $log, _) {
	

	$scope.token = null;
	$scope.error = null;
	$scope.roleStudent = false;
	$scope.roleProfessor = false;
	$scope.roleUser = false;
	
	$scope.user = {};
	
	$scope.openModalLogin = function() {
		var modalInstance = $uibModal.open({
			templateUrl: '/static/views/modals/login.html',
			controller: LoginModalController,
			scope: $scope,
			resolve: {
				user: function() {
					return null;
				}
			}
		});
		modalInstance.result.then(function(value) {
			$log.info('Modal finished it\'s job.');
		}, function(value) {
			$log.info('Modal dismissed.')
		});
	};
	
	var LoginModalController = ['$http', '$scope', '$uibModalInstance', 'user', 'Restangular', '$log', '_',
	                            function($http, $scope, $uibModalInstance, user, Restangular, $log, _) {
		$scope.user = user;
		$scope.ok = function() {
			console.log($scope.user);
			$http.post('/api/auth/login', $scope.user).then(function(data) {
				console.log(data);
			}, function(error) {
				$log.info('Error during loggin in!');
			});
			$uibModalInstance.close('ok');
		};
		$scope.cancel = function() {
			$uibModalInstance.dismiss('cancel');
		};
	}];
	
	/*
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
	*/
	
}]);
