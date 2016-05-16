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
		$scope.user.isStudent = true;
		$("#addUserModal").modal("show");
	}
	
	$scope.clickEditUser = function (id) {
		$scope.user = Restangular.one('students', id).get();
		$scope.user.isStudent = true;
		
		// Ovde moramo uraditi konverziju jer <input type="date"> pukne
		// ako nije pravi Date objekat
		$scope.userx.dateOfBirth = new Date($scope.userx.dateOfBirth);
		
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
