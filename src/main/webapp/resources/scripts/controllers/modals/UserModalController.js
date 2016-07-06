'use strict';

angular.module('studiorum').controller('UserModalController', ['$rootScope','$scope', '$uibModalInstance', 'user', 'Restangular', '$log', '_', '$uibModal',
    function ($rootScope, $scope, $uibModalInstance, user, Restangular, $log, _, $uibModal) {

        $scope.user = user;

        $scope.ok = function () {
        	
        	if($scope.user.password != $scope.user.passwordAgain){
        		$scope.passwordModal();
        	}else if(($scope.user.name == "") || ($scope.user.lastName == "") || ($scope.user.username == "")){
        		$scope.wrongModal();
        	}
        	else{
        		if ($scope.user.id) {
                    Restangular.all('users').customPUT($scope.user).then(function (data) {
                    });
                }else{
                	 Restangular.all('users').post($scope.user).then(function (data) {
                     });
                }
        		$uibModalInstance.close('ok');  
                
        	}
        	          
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
        
        $scope.passwordModal = function () {
            var modalInstance = $uibModal.open({
                templateUrl: '/static/views/modals/noPassword.html',
                controller: 'PasswordModalController',
                scope: $scope,
                resolve: {
                }
            });
            modalInstance.result.then(function (value) {
            }, function (value) {
            });
        };
        $scope.wrongModal = function () {
            var modalInstance = $uibModal.open({
                templateUrl: '/static/views/modals/wrongData.html',
                controller: 'PasswordModalController',
                scope: $scope,
                resolve: {
                }
            });
            modalInstance.result.then(function (value) {
            }, function (value) {
            });
        };

    }]);
