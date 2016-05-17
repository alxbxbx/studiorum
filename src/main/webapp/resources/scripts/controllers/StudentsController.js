'use strict';

angular.module('studiorum').controller('StudentsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function($scope, Restangular, $uibModal, $log, _) {
	
	// Initialization
	
	$scope.user = {};
	$scope.user.isStudent = true;
	$scope.baseStudents = Restangular.all('students');
	
	loadListOfStudents();
	
	// On Click Events
	
	
	
	
	$scope.clickHideUser = function () {
		$("#userModal_").modal("hide");
	}
	
	$scope.clickCreateUser = function () {	
		$scope.user = {};
		$scope.user.isStudent = true;
		$scope.user.isStudentCreate = true;
		$("#userModal_").modal("show");
	}
	
	$scope.clickEditUser = function (id) {
		Restangular.one("students", id).get().then(function(user) {
			$scope.user = user;
		    $scope.user.isStudent = true;
		    $scope.user.isStudentEdit = true;
		    $scope.user.dateOfBirth = new Date($scope.user.dateOfBirth);
		    $("#userModal_").modal("show");
		});
	}
	
	$scope.clickDeleteUser = function (id) {
		if (confirm("Are you sure?")) {
			Restangular.one("students", id).remove().then(function() {
				loadListOfStudents();
			});
		}
	}
	
	$scope.clickSaveUser = function () {
		if ($scope.user.id) {
			$scope.baseStudents.customPUT($scope.user).then(function() {
				loadListOfStudents();
				$("#userModal_").modal("hide");
			});
		} else {
			$scope.baseStudents.post($scope.user).then(function() {
				loadListOfStudents();
				$("#userModal_").modal("hide");
			});
		}
	}
	
	function loadListOfStudents() {
		Restangular.all("students").getList().then(function(students) {
			$scope.students = students;
	    });
	}

}]);
