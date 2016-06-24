'use strict';

angular.module('studiorum')
    .controller('SubjectController', ['$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams',
        function ($scope, Restangular, $uibModal, $log, _, $routeParams) {

            $scope.subject = {};
            $scope.dnd = {
                selectedStudent: null,
                listOfAllStudents: [],
                listOfAttendingStudents: []
            };


            $scope.getSubject = function () {
                Restangular.one("subjects", $routeParams.id).get().then(function (subject) {
                    $scope.subject = subject;
                });
            };

            $scope.getStudents = function () {
                Restangular.all("students").getList().then(function (entries) {
                    $scope.dnd.listOfAllStudents = entries;
                });
                Restangular.one("subjects", $routeParams.id).all("students").getList().then(function (entries) {
                    $scope.dnd.listOfAttendingStudents = entries;
                });
            };

            $scope.save = function () {
                var ids = [];
                for (var i = 0, len = $scope.dnd.listOfAttendingStudents.length; i < len; i++) {
                    ids.push($scope.dnd.listOfAttendingStudents[i].id);
                }
                Restangular.one("subjects", $routeParams.id).one("students").post(ids.join(','));
            };

            $scope.getSubject();
            $scope.getStudents();

        }]);
