'use strict';

angular.module('studiorum', [
    'ngResource',
    'ngRoute',
    'restangular',
    'ui.bootstrap',
    'lodash',
    'dndLists',
    'pascalprecht.translate',
    'angular-jwt'
  ]).config(['$httpProvider', '$routeProvider', '$translateProvider', 'jwtInterceptorProvider',
             function($httpProvider, $routeProvider, $translateProvider, jwtInterceptorProvider) {
	  
	// Route Configuration
	  
    $routeProvider
    	.when('/', {
        templateUrl: '/static/views/home.html',
        controller: 'HomeController',
        controllerAs: 'homeCtrl'
      })
      .when('/students', {
        templateUrl: '/static/views/students.html',
        controller: 'StudentsController',
        controllerAs: 'studentsCtrl'
      })
      .when('/professors', {
        templateUrl: '/static/views/professors.html',
        controller: 'ProfessorsController',
        controllerAs: 'professorsCtrl'
      })
      .when('/subjects', {
        templateUrl: '/static/views/subjects.html',
        controller: 'SubjectsController',
        controllerAs: 'subjectsCtrl'
      })
      .when('/students/:id', {
        templateUrl: '/static/views/oneStudent.html',
        controller: 'OneStudentController',
        controllerAs: 'oneStudentCtrl'
      })
      .when('/subjects/:id', {
        templateUrl: '/static/views/oneSubject.html',
        controller: 'OneSubjectController',
        controllerAs: 'oneSubjectCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
    
    // Translate Configuration
	
    $translateProvider
	    .useStaticFilesLoader({
	      prefix: '/static/translations/',
	      suffix: '.json'
	    })
	    .preferredLanguage('en');
    
    // JWT Configuration
    
    jwtInterceptorProvider.tokenGetter = function() {
        return localStorage.getItem('jwt_token');
    };

    $httpProvider.interceptors.push('jwtInterceptor');
    
  }]).run(['$http', 'Restangular', '$log','$rootScope', function($http, Restangular, $log, $rootScope) {
	  
    Restangular.setBaseUrl("api");
    Restangular.setErrorInterceptor(function(response) {
      if (response.status === 500) {
        $log.info("Internal server error.");
        return true;
      }
      return true;
    });
    
    $rootScope.lang = 'en';

    $rootScope.default_float = 'left';
    $rootScope.opposite_float = 'right';

    $rootScope.default_direction = 'ltr';
    $rootScope.opposite_direction = 'rtl';
    
    /**
     *  JSON Web Token on each page load
     */
 
    /*// Get token from local storage
    var jwt_token = localStorage.getItem('jwt_token');
    
    // Check if token expired
    var jwt_token_expired = jwtHelper.isTokenExpired(expToken);
    
    // Set Authorization header to each HTTP request
    $http.defaults.headers.common.Authorization = jwt_token;
    
    // Set logged user data in root scope
    $rootScope.loggedUser = jwtHelper.decodeToken(jwt_token);*/
    
  }]);
