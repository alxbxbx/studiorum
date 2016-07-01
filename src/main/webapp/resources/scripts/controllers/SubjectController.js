'use strict';

angular.module('studiorum')
    .controller('SubjectController', ['$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams',
        function ($scope, Restangular, $uibModal, $log, _, $routeParams) {

            $scope.subject = {};
            $scope.duty = {};
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
            
            $scope.getDuties = function () {
            	Restangular.one("subjects", $routeParams.id).all("duties").getList().then(function (duties) {
                    $scope.duties = duties;
                    console.log($scope.duties);
                });
            }

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
                var allIds = ids.join(',');
                
                var path = "subjects/" + $routeParams.id + "/students";
                Restangular.all(path).post(allIds).then(function (data) {
                	$scope.successModal();
                }, function(response){
                	console.log(response);
                });
                
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
            
            $scope.openModal = function (duty) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/duty.html',
                    controller: DutyModalCtrl,
                    scope: $scope,
                    resolve: {
                        duty: function () {
                            return duty;
                        }
                    }
                });
                modalInstance.result.then(function (value) {
                    $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
                }, function (value) {
                    $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
                });
            };
            var DutyModalCtrl = ['$scope', '$uibModalInstance', 'duty', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, duty, Restangular, $log, _) {
                    $scope.duty = duty;
                    $scope.duty.subjectDTO = $scope.subject;
                    $scope.ok = function () {
                        if ($scope.duty.id) {
                            Restangular.all('duties').customPUT($scope.duty).then(function (data) {
                            	$scope.getDuties();  
                            });
                        } else {
                            Restangular.all('duties').post($scope.duty).then(function (data) {
                            	
                            	$scope.getDuties();
                                },
                                // callback za gresku sa servera
                                function (response) {
                                	console.log(response);
                                });
                        }
                        $uibModalInstance.close('ok');
                    };

                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };


            }];
            
            $scope.deleteDuty = function (id) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/delete.html',
                    controller: StudentDeleteCtrl,
                    scope: $scope,
                    resolve: {
                        id: function () {
                            return id;
                        }
                    }
                });
                modalInstance.result.then(function (value) {
                    $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
                }, function (value) {
                    $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
                });
            }
            var StudentDeleteCtrl = ['$scope', '$uibModalInstance', 'id', 'Restangular', '$log', '_',
	     	    function ($scope, $uibModalInstance, id, Restangular, $log, _) {
	     	        $scope.ok = function () {
	     	        	Restangular.one("duties", id).remove().then(function () {
	     	        		$scope.getDuties();
	     	            });
	     	            $uibModalInstance.close('ok');
	     	        };
	     	        $scope.cancel = function () {
	     	            $uibModalInstance.dismiss('cancel');
	     	        };
	     	
	     	}];
            $scope.successModal = function () {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/success.html',
                    controller: SuccessCtrl,
                    scope: $scope,
                    resolve: {
                    }
                });
                modalInstance.result.then(function (value) {
                    $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
                }, function (value) {
                    $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
                });
            }
            var SuccessCtrl = ['$scope', '$uibModalInstance', 'Restangular', '$log', '_',
 	     	    function ($scope, $uibModalInstance, Restangular, $log, _) {
 	     	        $scope.ok = function () {
 	     	            $uibModalInstance.close('ok');
 	     	        };
 	     	        $scope.cancel = function () {
 	     	            $uibModalInstance.dismiss('cancel');
 	     	        };
 	     	}];

            $scope.getSubject();
            $scope.getStudents();
            $scope.getDuties();

        }]);
