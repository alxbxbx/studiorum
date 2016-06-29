 'use strict';

angular.module('studiorum')
    .controller('StudentController', ['$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams', 'Upload', '$timeout', '$http', '$location',
        function ($scope, Restangular, $uibModal, $log, _, $routeParams, Upload, $timeout, $http, $location) {

            // Initialization
            $scope.user = {};
            $scope.loading = false;
            $scope.user.isStudent = true;

            $scope.getFiles = function () {
                Restangular.one("students/" + $routeParams.id + "/files").get().then(function (files) {
                    $scope.files = files;
                });
            };
            
            $scope.getSubjects = function() {
            	Restangular.one("students/" + $routeParams.id + "/subjects").get().then(function (subjects) {
                    $scope.subjects = subjects;
                });
            }
            $scope.goTo = function(id){
            	$location.path("/subjects/" + id);
            }

            $scope.getStudent = function () {
                Restangular.one("students", $routeParams.id).get().then(function (user) {
                    $scope.user = user;
                });
            };

            $scope.removeFile = function (fileId) {
                Restangular.one('students', $routeParams.id).one('files', fileId).remove().then(function () {
                    $scope.getFiles();
                });
            };

            $scope.downloadFile = function (fileId) {
                Restangular.one('students', $routeParams.id).one('files', fileId).get().then(function (result) {
                    // Fix this
                    window.location.href = result;
                });
            };

            $scope.uploadFile = function (file) {
                $scope.file = file;
                if (file) {
                    $scope.loading = true;
                    Upload.upload({
                        url: '/api/students/' + $scope.user.id + '/files',
                        data: {file: file}
                    }).then(function (response) {
                        $timeout(function () {
                            $scope.getFiles();
                            $scope.loading = false;
                        });
                    }, function (response) {
                        $scope.loading = false;
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
            $scope.getSubjects();

        }]);
