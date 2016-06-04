'use strict';

angular.module('studiorum').service('authService', ['$http', function ($http) {
	
	return {
        login : function(username, password) {
        	var payload = {
    			username: username,
    			password: password
        	};
            return $http.post('/api/auth/login', payload).then(function(response) {
                return response.data.token;
            });
        },
        hasRole : function(role) {
            return $http.get('/api/role/' + role).then(function(response){
                return response.data;
            });
        }
    };

}]);
