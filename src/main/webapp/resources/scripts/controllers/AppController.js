'use strict';

angular.module('studiorum').controller('AppController', ['$http', '$scope', 'authService', 'Restangular', '$uibModal', '$log', '_', 
                                                         function ($http, $scope, authService, Restangular, $uibModal, $log, _) {
	

	$scope.token = null;
	$scope.error = null;
	
	$scope.roleStudent = false;
	$scope.roleProfessor = false;
	$scope.roleAdmin = false;
	$scope.roleSsluzba = false;
	
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
			$log.info('Modal dismissed.');
		});
	};
	
	var LoginModalController = ['$http', '$scope', '$uibModalInstance', 'user', 'Restangular', '$log', '_',
	                            function($http, $scope, $uibModalInstance, user, Restangular, $log, _) {
		$scope.user = user;
		$scope.ok = function() {
			$http.post('/auth/login', $scope.user).then(function(response) {
				$scope.token = response.data.token;
				$http.defaults.headers.common.Authorization = response.data.token;
				console.log("AUTH TOKEN => " + response.data.token);	
				$scope.checkRoles();
			}, function(error) {
				$scope.error = error;
				$log.info('Error during loggin in!');
			});
			$uibModalInstance.close('ok');
		};
		$scope.cancel = function() {
			$uibModalInstance.dismiss('cancel');
		};
	}];
	
	$scope.checkRoles = function() {
		authService.hasRole('admin').then(function(user) {
			$scope.roleUser = user;
			console.log("isAdmin => " + $scope.roleAdmin);
		});
		authService.hasRole('ssluzba').then(function(ssluzba) {
			$scope.roleSsluzba = ssluzba;
			console.log("isSsluzba => " + $scope.roleSsluzba);
		});
		authService.hasRole('student').then(function(student) {
			$scope.roleStudent = student;
			console.log("isStudent => " + $scope.roleStudent);
		});
		authService.hasRole('professor').then(function(professor) {
			$scope.roleProfessor = professor;
			console.log("isProfessor => " + $scope.roleProfessor);
		});
	}
	
	$scope.loggedIn = function() {
		return $scope.token !== null;
	}
	
	$scope.logout = function() {
		$scope.userName = '';
		$scope.token = null;
		$http.defaults.headers.common.Authorization = '';
	}
	
}]);
