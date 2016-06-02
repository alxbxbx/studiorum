'use strict';

angular.module('studiorum').service('AuthService', function () {
	
	return {
        login : function(username, password) {
        	
        	var data = {
    			username: username,
    			password: password
        	};
        	
            return $http.post('/api/auth/login', data).then(function(response) {
                return response.data.token;
            });
        },

        hasRole : function(role) {
            return $http.get('/api/role/' + role).then(function(response){
                console.log(response);
                return response.data;
            });
        }
    };

});
