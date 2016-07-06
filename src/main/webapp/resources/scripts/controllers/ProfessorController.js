'use strict';

angular.module('studiorum')
    .controller('ProfessorController', ['$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams', 'Upload', '$timeout', '$http', '$location',
        function ($scope, Restangular, $uibModal, $log, _, $routeParams, Upload, $timeout, $http, $location) {

            Restangular.one('/professors/' + $routeParams.id).get().then(function (data) {
                $scope.professor = data;
            });

            Restangular.one('/professors/' + $routeParams.id + "/roles").get().then(function (data) {
                $scope.professorRoles = data;
            });

        }]);
