'use strict';

angular.module('studiorum')
    .controller('StudentController', ['$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams',
        function ($scope, Restangular, $uibModal, $log, _, $routeParams) {

            $scope.user = {};
            $scope.user.isStudent = true;
            getStudent();


            function getStudent() {
                Restangular.one("students", $routeParams.id).get().then(function (user) {
                    $scope.user = user;
                });
            }

            $scope.openModal = function (user) {

                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/studentModal.html',
                    controller: StudentModalCtrl,
                    scope: $scope,
                    resolve: {
                        user: function () {
                            return user;
                        }
                    }
                });
                modalInstance.result.then(function (value) {
                    $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
                }, function (value) {
                    $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
                });
            };

            var StudentModalCtrl = ['$scope', '$uibModalInstance', 'user', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, user, Restangular, $log, _) {
                    $scope.user = user;
                    $scope.ok = function () {
                        if ($scope.user.id) {
                            Restangular.all('students').customPUT($scope.user).then(function (data) {
                            });
                        }
                        $uibModalInstance.close('ok');
                    };

                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };


                }];


        }]);