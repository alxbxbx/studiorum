'use strict';

angular.module('studiorum')
.controller('OneSubjectController', ['$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams', 
                                     function($scope, Restangular, $uibModal, $log, _, $routeParams) {
	
	$scope.subject = {};
	$scope.subjectStudents = [];
	$scope.allStudents = [];
	
	
	getSubject();
	getStudents();
	
	function getSubject(){
		Restangular.one("subjects", $routeParams.id).get().then(function(subject) {
			$scope.subject = subject;
		});
	}
	
	function getStudents(){
		Restangular.all("students").getList().then(function(entries){
			$scope.allStudents = entries;
		});
		/*
		Restangular.all("subjects", $routeParams.id, "students").getList.then(function(entries){
			$scope.subjectStudents = entries;
		}); */
	}
	
	
}]);