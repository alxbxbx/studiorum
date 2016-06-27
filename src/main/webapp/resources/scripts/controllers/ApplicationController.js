'use strict';

angular.module('studiorum').controller('ApplicationController', ['$rootScope', '$http', '$scope', '$uibModal', '$log', '_', 'authService',
    function ($rootScope, $http, $scope, $uibModal, $log, _, authService) {

        $scope.user = {};

        $scope.openLanguagesModal = function () {
            var modalInstance = $uibModal.open({
                animation: false,
                templateUrl: '/static/views/modals/languages.html',
                controller: 'LanguagesModalController',
                resolve: {
                    user: function () {
                        return null;
                    }
                }
            });
            modalInstance.result.then(function (value) {
            }, function (value) {
            });
        }

        $scope.openLoginModal = function () {
            var modalInstance = $uibModal.open({
                animation: false,
                templateUrl: '/static/views/modals/login.html',
                controller: 'LoginModalController',
                resolve: {
                    user: function () {
                        return null;
                    }
                }
            });
            modalInstance.result.then(function (value) {
            }, function (value) {
            });
        };

        $scope.isLoggedIn = authService.isLoggedIn;

        $scope.hasRole = authService.hasRole;

        $scope.logout = authService.logout;

    }]);
