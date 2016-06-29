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
                }
            });
            modalInstance.result.then(function (value) {
            }, function (value) {
            });
        }
        
        $scope.addUserModal = function() {
        	var modalInstance = $uibModal.open({
                animation: false,
                templateUrl: '/static/views/modals/user.html',
                controller: 'UserModalController',
                resolve: {
                	user: function () {
                        return $scope.user;
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
