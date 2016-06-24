'use strict';

angular.module('studiorum').controller('UserModalController', ['$scope', '$uibModalInstance', 'user', 'Restangular', '$log', '_',
    function ($scope, $uibModalInstance, user, Restangular, $log, _) {

        $scope.user = user;

        $scope.ok = function () {
            if ($scope.user.id) {
                Restangular.all('students').customPUT($scope.user).then(function (data) {
                });
            }
            $uibModalInstance.close('ok');
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    }]);
