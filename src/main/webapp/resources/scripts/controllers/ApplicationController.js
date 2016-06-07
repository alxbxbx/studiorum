'use strict';

angular.module('studiorum').controller('ApplicationController', ['$rootScope', 'jwtHelper', '$http', '$scope', '$uibModal', '$log', '_',
    function ($rootScope, jwtHelper, $http, $scope, $uibModal, $log, _) {

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

        $scope.isLoggedIn = function () {
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
            var payload = jwtHelper.decodeToken(token);
            $rootScope.loggedUserData = payload.userdata;
            return true;
        }

        $scope.logout = function () {
            $http.defaults.headers.common.Authorization = '';
            localStorage.removeItem('jwt_token');
            $rootScope.loggedUserData = null;
        }

    }]);
