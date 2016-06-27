'use strict';
angular.module('studiorum').controller('SubjectsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function ($scope, Restangular, $uibModal, $log, _) {


    // Initialization

    $scope.subject = {};
    $scope.baseSubjects = Restangular.all('subjects');

    loadListOfSubjects();


    


    function loadListOfSubjects() {
        Restangular.all("subjects").getList().then(function (entries) {
            $scope.subjects = entries;
        });
    }


    $scope.openModal = function (subject) {

        var modalInstance = $uibModal.open({
            templateUrl: '/static/views/modals/subject.html',
            controller: SubjectModalCtrl,
            scope: $scope,
            resolve: {
                subject: function () {
                    return subject;
                }
            }
        });
        modalInstance.result.then(function (value) {
            $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
        }, function (value) {
            $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
        });
    };


    var SubjectModalCtrl = ['$scope', '$uibModalInstance', 'subject', 'Restangular', '$log', '_',
        function ($scope, $uibModalInstance, subject, Restangular, $log, _) {
            $scope.subject = subject;
            $scope.ok = function () {
                if ($scope.subject.id) {
                    Restangular.all('subjects').customPUT($scope.subject).then(function (data) {
                        loadListOfSubjects();
                    });
                } else {
                    Restangular.all('subjects').post($scope.subject).then(function (data) {
                            loadListOfSubjects();
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
    
    $scope.clickDeleteSubject = function (id) {
        var modalInstance = $uibModal.open({
            templateUrl: '/static/views/modals/delete.html',
            controller: SubjectDeleteCtrl,
            scope: $scope,
            resolve: {
                id: function () {
                    return id;
                }
            }
        });
        modalInstance.result.then(function (value) {
            $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
        }, function (value) {
            $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
        });
    }
    var SubjectDeleteCtrl = ['$scope', '$uibModalInstance', 'id', 'Restangular', '$log', '_',
        function ($scope, $uibModalInstance, id, Restangular, $log, _) {
            $scope.ok = function () {
            	Restangular.one("subjects", id).remove().then(function () {
                    loadListOfSubjects();
                });
                $uibModalInstance.close('ok');
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };


        }];

}]);
