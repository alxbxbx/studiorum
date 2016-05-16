'use strict';

angular.module('studiorum').controller('StudentsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function($scope, Restangular, $uibModal, $log, _) {
	
	// Initialization
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
		$scope.user.isStudentCreate = true;
		$("#addUserModal").modal("show");
	}
	
	$scope.clickEditUser = function (id) {
		Restangular.one("students", id).get().then(function(user) {
		    $scope.user = user;
		    $scope.user.isStudent = true;
		    $scope.user.isStudentEdit = true;
		    $scope.user.dateOfBirth = new Date($scope.user.dateOfBirth);
		    $("#addUserModal").modal("show");
		});
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
