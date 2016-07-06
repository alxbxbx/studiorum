'use strict';

angular.module('studiorum')
    .controller('ProfessorController', ['$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams', 'Upload', '$timeout', '$http', '$location',
        function ($scope, Restangular, $uibModal, $log, _, $routeParams, Upload, $timeout, $http, $location) {

            $scope.getUser = function () {
                Restangular.one('/professors/' + $routeParams.id).get().then(function (data) {
                    $scope.user = data;
                });
            };

            $scope.getUser();

            Restangular.one('/professors/' + $routeParams.id + "/roles").get().then(function (data) {
                $scope.professorRoles = data;
            });

            $scope.openModal = function (user) {
                
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/editStudent.html',
                    controller: ProfessorModalController,
                    scope: $scope,
                    resolve: {
                        user: function () {
                            return user;
                        }
                    }
                });

                modalInstance.result.then(function (value) {
                    $scope.getProfessor();
                }, function (value) {
                    $scope.getProfessor();
                });

            };

            var ProfessorModalController = ['$scope', '$uibModalInstance', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, Restangular, $log, _) {

                    $scope.ok = function () {
                        Restangular.all('professors').customPUT($scope.user).then(function () {
                            $scope.getProfessor();
                            $uibModalInstance.close('ok');
                        });
                    };

                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };

                }];
            
            $scope.uploadPicture = function (file) {
                $scope.file = file;
                if (file) {
                    $scope.loading = true;
                    Upload.upload({
                        url: '/api/professors/' + $scope.user.id + '/pictures',
                        data: {file: file}
                    }).then(function (response) {
                        $timeout(function () {
                        	$scope.getUser();
                            $scope.loading = false;
                        });
                    }, function (response) {
                        $scope.loading = false;
                    }, function (evt) {
                        $scope.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                    });
                }
            };

        }]);
