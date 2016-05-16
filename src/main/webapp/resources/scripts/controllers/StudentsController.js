'use strict';

angular.module('studiorum').controller('StudentsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function($scope, Restangular, $uibModal, $log, _) {

	// Initialization
	
	$scope.user = {};
	
	$scope.user.isStudent = true;
	
	$scope.baseStudents = Restangular.all('students');
	
	Restangular.all("students").getList().then(function(students) {
		$scope.students = students;
    });
	
	// On Click Events
	
	$scope.clickHideUser = function () {
		$("#addUserModal").modal("hide");
	}
	
	$scope.clickCreateUser = function () {	
		$scope.user = {};
		$("#addUserModal").modal("show");
	}
	
	$scope.clickEditUser = function (id) {
		console.log(id);
		$scope.user = Restangular.one('students', id).get();
		console.log($scope.user);
		$("#addUserModal").modal("show");
	}
	
	$scope.clickDeleteUser = function () {
		
	}
	
	$scope.clickSaveUser = function () {
		if ($scope.user.id) {
			
		} else {
			$scope.baseStudents.post($scope.user);
		}
		
	}

}]);
