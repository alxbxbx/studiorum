'use strict';

angular
  .module('studiorum', [
    'ngResource',
    'ngRoute',
    'restangular',
    'ui.bootstrap',
    'lodash',
    'dndLists',
    'pascalprecht.translate'
  ])
  .config(['$routeProvider','$translateProvider', function($routeProvider, $translateProvider) {
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
    
    $translateProvider
	    .useStaticFilesLoader({
	      prefix: '/static/translations/',
	      suffix: '.json'
	    })
	    .preferredLanguage('ar');
  }])
  // run se izvrsava pre svega ostalog
  .run(['Restangular', '$log','$rootScope', function(Restangular, $log, $rootScope) {
    // postavimo base url za Restangular da ne bismo morali da ga
    // navodimo svaki put kada se obracamo back endu
    // poziv vrsimo na http://localhost:8080/api/
    Restangular.setBaseUrl("api");
    Restangular.setErrorInterceptor(function(response) {
      if (response.status === 500) {
        $log.info("internal server error");
        return true; // greska je obradjena
      }
      return true; // greska nije obradjena
    });
    
    $rootScope.lang = 'en';

    $rootScope.default_float = 'left';
    $rootScope.opposite_float = 'right';

    $rootScope.default_direction = 'ltr';
    $rootScope.opposite_direction = 'rtl';
  }]);
