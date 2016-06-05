'use strict';

angular.module('studiorum').controller('ApplicationController', ['jwtHelper', '$http', '$scope', '$uibModal', '$log', '_',
                                                         function (jwtHelper, $http, $scope, $uibModal, $log, _) {
	
	$scope.user = {};
	
	$scope.openModalLogin = function() {
		var modalInstance = $uibModal.open({
			animation: false,
			templateUrl: '/static/views/modals/login.html',
			controller: 'LoginModalController',
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
	
	$scope.isLoggedIn = function() {
		
		var token = localStorage.getItem('jwt_token');
		if (!token) {
			$log.info('Token not found.');
			return false;
		}
		
		var isTokenExpired = jwtHelper.isTokenExpired(token);
		if (isTokenExpired) {
			$log.info('Token expired.');
			return false;
		}
		
		return true;
	}
	
	$scope.logout = function() {
		$http.defaults.headers.common.Authorization = '';
		localStorage.removeItem('jwt_token');
	}
	
}]);
