'use strict';

angular.module('studiorum').service('authService', ['$http', function ($http) {

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

    service.hasRole = function (role) {
        return $http.get('/api/account/roles/' + role).then(function (response) {
            return response.data;
        });
    };

    return service;

}]);
