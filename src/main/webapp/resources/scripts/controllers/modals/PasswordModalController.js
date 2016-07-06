'use strict';

angular.module('studiorum').controller('PasswordModalController', ['$scope', '$uibModalInstance', 'Restangular', '$log', '_',
    function ($scope, $uibModalInstance, Restangular, $log, _) {

        $scope.ok = function () {
        	$uibModalInstance.close('ok');            
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    }]);