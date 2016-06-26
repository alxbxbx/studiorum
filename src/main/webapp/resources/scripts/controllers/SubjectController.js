'use strict';

angular.module('studiorum')
    .controller('SubjectController', ['$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams',
        function ($scope, Restangular, $uibModal, $log, _, $routeParams) {

            $scope.subject = {};
            $scope.searchText = "";
            $scope.dnd = {
                selectedStudent: null,
                listOfAllStudents: [],
                listOfAttendingStudents: []
            };


            $scope.getSubject = function () {
                Restangular.one("subjects", $routeParams.id).get().then(function (subject) {
                    $scope.subject = subject;
                });
            };

            $scope.getStudents = function () {
                Restangular.one("subjects", $routeParams.id).all("students").getList().then(function (entries) {
                    $scope.dnd.listOfAttendingStudents = entries;
                });
                Restangular.all("students").getList().then(function (entries) {
                	var students = entries;
                	for(var i = 0, len = $scope.dnd.listOfAttendingStudents.length; i < len; i++){
                		var index = null;
                		for(var j = 0, len2 = students.length; j < len2; j++){
                			if(students[j].id == $scope.dnd.listOfAttendingStudents[i].id){
                				index = j;
                				break;
                			}
                		}
                		if(index != null){
                			students.splice(index, 1);
                		}
                	}
                	$scope.dnd.listOfAllStudents = students;
                });
            };

            $scope.save = function () {
                var ids = [];
                for (var i = 0, len = $scope.dnd.listOfAttendingStudents.length; i < len; i++) {
                    ids.push($scope.dnd.listOfAttendingStudents[i].id);
                }
                var payload = {
                    studentIds: ids.join(',')
                };
                Restangular.one("subjects", $routeParams.id).customPOST(payload, "students");
            };
            
            $scope.search = function(){
            	if($scope.searchText.length > 2){
            		Restangular.all("students/search").customGETLIST('', {
                        page: "1", 
                        size: "1000",
                        searchText: $scope.searchText
                    }).then(function (entries) {
                    	var students = entries;
                    	for(var i = 0, len = $scope.dnd.listOfAttendingStudents.length; i < len; i++){
                    		var index = null;
                    		for(var j = 0, len2 = students.length; j < len2; j++){
                    			if(students[j].id == $scope.dnd.listOfAttendingStudents[i].id){
                    				index = j;
                    				break;
                    			}
                    		}
                    		if(index != null){
                    			students.splice(index, 1);
                    		}
                    	}
                    	$scope.dnd.listOfAllStudents = students;
                    });
            	}
            	if($scope.searchText.length == 0){
            		Restangular.all("students").getList().then(function (entries) {
            			var students = entries;
                    	for(var i = 0, len = $scope.dnd.listOfAttendingStudents.length; i < len; i++){
                    		var index = null;
                    		for(var j = 0, len2 = students.length; j < len2; j++){
                    			if(students[j].id == $scope.dnd.listOfAttendingStudents[i].id){
                    				index = j;
                    				break;
                    			}
                    		}
                    		if(index != null){
                    			students.splice(index, 1);
                    		}
                    	}
                    	$scope.dnd.listOfAllStudents = students;
                    });
            	}
            }

            $scope.getSubject();
            $scope.getStudents();

        }]);
