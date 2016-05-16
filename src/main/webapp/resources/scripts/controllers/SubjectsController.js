'use strict';
angular.module('studiorum').controller('SubjectsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function($scope, Restangular, $uibModal, $log, _) {
	

	// Initialization
	
	$scope.subject = {};
	$scope.baseSubjects = Restangular.all('subjects');
	
	loadListOfSubjects();
	
	// On Click Events
	
	$scope.clickHideUser = function () {
		$("#subjectModal_").modal("hide");
	}
	
	$scope.clickCreateSubject = function () {
		console.log("NOTHING HAPPENED")
		$scope.subject = {};
		//$scope.user.isStudentCreate = true;
		$("#subjectModal_").modal("show");
	}
	
	$scope.clickEditSubject = function (id) {
		Restangular.one("subjects", id).get().then(function(subject) {
			$scope.subject = subject;
		    $("#subjectModal_").modal("show");
		});
	}
	
	$scope.clickDeleteSubject = function (id) {
		if (confirm("Are you sure?")) {
			Restangular.one("subjects", id).remove().then(function() {
				loadListOfSubjects();
			});
		}
	}
	
	$scope.clickSaveSubject = function () {
		if ($scope.subject.id) {
			$scope.baseSubjects.customPUT($scope.subject).then(function() {
				loadListOfSubjects();
				$("#subjectModal_").modal("hide");
			});
		} else {
			$scope.baseSubjects.post($scope.subject).then(function() {
				loadListOfSubjects();
				$("#subjectModal_").modal("hide");
			});
		}
	}
	
	
	
	
	
	function loadListOfSubjects(){
		Restangular.all("subjects").getList().then(function(entries) {
			$scope.subjects = entries;
	    });	
	}
	

}]);
