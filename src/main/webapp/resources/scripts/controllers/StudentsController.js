'use strict';

angular.module('studiorum').controller('StudentsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function($scope, Restangular, $uibModal, $log, _) {

	$scope.user = {};
	
	$scope.user.isStudent = true;
	
	$scope.clickShowPopupUser = function () {
		$("#addUserModal").modal("show");
	}
	
	$scope.clickHidePopupUser = function () {
		$("#addUserModal").modal("hide");
	}
	
	$scope.clickSaveUser = function () {
		console.log($scope.user);
	}
	
	$scope.clickSaveStudent = function() {
		console.log($scope.user);
		if (200) {
			// hide with success message
		} else {
			// show error
		}
	}
	
	Restangular.all("students").getList().then(function(students) {
		$scope.students = students;
    });

}]);
