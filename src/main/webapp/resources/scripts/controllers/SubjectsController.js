'use strict';

angular.module('studiorum').controller('SubjectsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function($scope, Restangular, $uibModal, $log, _) {

	Restangular.all("subjects").getList().then(function(entries) {
		$scope.subjects = entries;
    });

}]);