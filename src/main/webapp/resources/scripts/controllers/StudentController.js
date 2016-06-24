'use strict';

angular.module('studiorum')
    .controller('StudentController', ['$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams', 'Upload', '$timeout',
        function ($scope, Restangular, $uibModal, $log, _, $routeParams, Upload, $timeout) {

            // Initialization
            $scope.user = {};
            $scope.user.isStudent = true;

            $scope.getFiles = function () {
                Restangular.one("students/" + $routeParams.id + "/files").get().then(function (files) {
                    $scope.files = files;
                });
            };

            $scope.getStudent = function () {
                Restangular.one("students", $routeParams.id).get().then(function (user) {
                    $scope.user = user;
                });
            };

            $scope.uploadFile = function (file) {
                $scope.file = file;
                if (file) {
                    Upload.upload({
                        url: '/api/students/' + $scope.user.id + '/files',
                        data: {file: file}
                    }).then(function (response) {
                        $timeout(function () {
                            $scope.getFiles();
                        });
                    }, function (response) {
                        /*if (response.status > 0) {
                         $scope.errorMsg = response.status + ': ' + response.data;
                         }*/
                    }, function (evt) {
                        $scope.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                    });
                }
            };

            $scope.openModal = function (user) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/studentModal.html',
                    controller: 'UserModalController',
                    scope: $scope,
                    resolve: {
                        user: function () {
                            return user;
                        }
                    }
                });
                modalInstance.result.then(function (value) {
                }, function (value) {
                });
            };

            // Pull data
            $scope.getStudent();
            $scope.getFiles();
        }]);
