'use strict';

angular.module('studiorum').service('authService', ['$http', 'jwtHelper', '$rootScope', '$log',
    function ($http, jwtHelper, $rootScope, $log) {

        var service = {};

        service.login = function (username, password) {
            var payload = {
                username: username,
                password: password
            };
            return $http.post('/auth/login', payload).then(function (response) {
                return response.data.token;
            });
        };

        service.logout = function () {
            $http.defaults.headers.common.Authorization = '';
            localStorage.removeItem('jwt_token');
            $rootScope.loggedUserData = null;
        };

        service.isLoggedIn = function () {
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
        };

        service.hasRole = function (roles) {
            var valid = false;
            for (var i = 0; i < roles.length; i++) {
                if (roles[i] == loggedUserData.role) {
                    valid = true;
                }
            }
            return valid;
        };

        return service;

    }
])
;
