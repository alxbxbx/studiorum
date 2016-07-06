'use strict';
angular.module('studiorum').controller('ProfessorsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function ($scope, Restangular, $uibModal, $log, _) {


    // Initialization

    $scope.user = {};
    $scope.user.isProfessor = true;

    loadListOfProfessors();


    


    function loadListOfProfessors() {
        Restangular.all("professors").getList().then(function (entries) {
            $scope.professors = entries;
        });
    }


    $scope.openModal = function (user) {
        user.isProfessor = true;
        var modalInstance = $uibModal.open({
            templateUrl: '/static/views/modals/user.html',
            controller: ProfessorModalCtrl,
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
        	loadListOfProfessors();
            $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
        });
    };


    var ProfessorModalCtrl = ['$scope', '$uibModalInstance', 'user', 'Restangular', '$log', '_',
        function ($scope, $uibModalInstance, user, Restangular, $log, _) {
            $scope.user = user;
            $scope.ok = function () {
                if ($scope.user.id) {
                    Restangular.all('professors').customPUT($scope.user).then(function (data) {
                        loadListOfProfessors();
                    });
                } else {
                    Restangular.all('professors').post($scope.user).then(function (data) {
                            loadListOfProfessors();
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
    
    $scope.clickDeleteProfessor = function (id) {
        var modalInstance = $uibModal.open({
            templateUrl: '/static/views/modals/delete.html',
            controller: ProfessorDeleteCtrl,
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
    
    var ProfessorDeleteCtrl = ['$scope', '$uibModalInstance', 'id', 'Restangular', '$log', '_',
	  function ($scope, $uibModalInstance, id, Restangular, $log, _) {
	      $scope.ok = function () {
	    	  Restangular.one("professors", id).remove().then(function () {
	                loadListOfProfessors();
	            });
	          $uibModalInstance.close('ok');
	      };
	      $scope.cancel = function () {
	          $uibModalInstance.dismiss('cancel');
	      };
	  }];

}]);
