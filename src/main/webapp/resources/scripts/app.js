'use strict';

angular.module('studiorum', [
    'ngResource',
    'ngRoute',
    'ngFileUpload',
    'restangular',
    'ui.bootstrap',
    'lodash',
    'dndLists',
    'pascalprecht.translate',
    'angular-jwt'
]).config(['$httpProvider', '$routeProvider', '$translateProvider', 'jwtInterceptorProvider',
    function ($httpProvider, $routeProvider, $translateProvider, jwtInterceptorProvider) {

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
                templateUrl: '/static/views/student.html',
                controller: 'StudentController',
                controllerAs: 'studentCtrl'
            })
            .when('/subjects/:id', {
                templateUrl: '/static/views/subject.html',
                controller: 'SubjectController',
                controllerAs: 'subjectCtrl'
            })
            .when('/about', {
                templateUrl: '/static/views/about.html',
                controller: 'AboutController',
                controllerAs: 'aboutCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });

        // Translate Configuration

        $translateProvider.useStaticFilesLoader({
            prefix: '/static/translations/',
            suffix: '.json'
        });

        var languageKey = localStorage.getItem('languageKey');
        if (languageKey) {
            $translateProvider.preferredLanguage(languageKey);
        } else {
            $translateProvider.preferredLanguage('rs');
        }

        // JWT Configuration

        jwtInterceptorProvider.tokenGetter = function () {
            return localStorage.getItem('jwt_token');
        };

        $httpProvider.interceptors.push('jwtInterceptor');

    }]).run(['$http', 'Restangular', '$log', '$rootScope', 'jwtHelper', function ($http, Restangular, $log, $rootScope, jwtHelper) {

    var token = localStorage.getItem('jwt_token');
    var languageKey = localStorage.getItem('languageKey');

    Restangular.setBaseUrl("api");

    Restangular.setErrorInterceptor(function (response) {
        if (response.status === 500) {
            $log.info("Internal server error.");
            return true;
        }
        return true;
    });

    if (token) {
        var payload = jwtHelper.decodeToken(token);
        $rootScope.loggedUserData = payload.userdata;
        console.log($rootScope.loggedUserData);
    }

    if (languageKey) {
        $rootScope.lang = languageKey;
    } else {
        $rootScope.lang = 'rs';
    }

}]);
