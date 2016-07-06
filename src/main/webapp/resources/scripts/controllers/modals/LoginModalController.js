'use strict';

angular.module('studiorum').controller('LoginModalController', ['$http', '$scope', '$uibModalInstance', '$log', '_', 'authService',
    function ($http, $scope, $uibModalInstance, $log, _, authService) {

        $scope.user = {};

        $scope.ok = function () {
            authService.login($scope.user).then(function (response) {
                var token = "Bearer " + response.data.token;
                $http.defaults.headers.common.Authorization = token;
                localStorage.setItem("jwt_token", response.data.token);
                $uibModalInstance.close('ok');
                window.location = "/#/";
            }, function (error) {
                $scope.error = error.data.message;
            });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    }]);
