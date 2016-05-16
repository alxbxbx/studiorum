'use strict';

angular.module('studiorum').controller('StudentsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function($scope, Restangular, $uibModal, $log, _) {

	Restangular.all("students").getList().then(function(entries) {
		$scope.students = entries;
    });

}]);
