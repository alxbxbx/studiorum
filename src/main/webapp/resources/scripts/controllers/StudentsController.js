'use strict';

angular.module('studiorum').controller('StudentsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function ($scope, Restangular, $uibModal, $log, _) {

    // Initialization
	$scope.maxSize = 7;
	$scope.bigTotalItems = 1300;
	$scope.bigCurrentPage = 3;
    $scope.user = {};
    $scope.user.isStudent = true;

    loadListOfStudents();

    // On Click Events
    $scope.clickDeleteUser = function (id) {
        if (confirm("Are you sure?")) {
            Restangular.one("students", id).remove().then(function () {
                loadListOfStudents();
            });
        }
    }

    function loadListOfStudents() {
        Restangular.all("students").getList().then(function (students) {
            $scope.students = students;
        });
    }

    $scope.openModal = function (user) {
        user.isStudent = true;
        var modalInstance = $uibModal.open({
            templateUrl: '/static/views/modals/uniUser.html',
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
            console.log($scope.user);
            $scope.ok = function () {
                if ($scope.user.id) {
                    Restangular.all('students').customPUT($scope.user).then(function (data) {
                        loadListOfStudents();
                    });
                } else {
                    Restangular.all('students').post($scope.user).then(function (data) {
                            loadListOfStudents();
                        },
                        // callback za gresku sa servera
                        function () {
                            $log.info('something went wrong!');
                        });
                }
                $uibModalInstance.close('ok');
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };


        }];

}]);
