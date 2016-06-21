'use strict';

angular.module('studiorum').controller('StudentsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function ($scope, Restangular, $uibModal, $log, _) {

    // Initialization
	$scope.maxSize = 5;
	
	//Max page
	$scope.bigTotalItems = 0;
	
	//Check in local storage for page number first
	var pageNumber = localStorage.getItem('pageNumber');
	$scope.bigCurrentPage = 1;
	if(typeof pageNumber == "undefined"){
		localStorage.setItem('pageNumber', 0);
	}else{
		$scope.bigCurrentPage = eval(pageNumber) + 1;
	}
	
	
    $scope.user = {};
    $scope.user.isStudent = true;
    
    //Load list of students on current page
    loadListOfStudents($scope.bigCurrentPage - 1);
    
    //Load count of all students
    loadStudentsCount();

    // On Click Events
    $scope.clickDeleteUser = function (id) {
        if (confirm("Are you sure?")) {
            Restangular.one("students", id).remove().then(function () {
                loadListOfStudents();
            });
        }
    }
    
    function loadStudentsCount(){
    	Restangular.one("students/count", "").get().then(function(count){
    		$scope.count = (count / 20) * 10;
    		$scope.bigTotalItems = $scope.count;
    	});
    }

    function loadListOfStudents(pageNumber) {
        Restangular.all("students").customGETLIST('', {
            page: pageNumber, 
            size: "20"
        }).then(function (students) {
            $scope.students = students;
        });
    }
    $scope.pageChanged = function(){
    	localStorage.setItem('pageNumber', $scope.bigCurrentPage - 1);
    	loadListOfStudents($scope.bigCurrentPage - 1);
    }

    $scope.openModal = function (user) {
        user.isStudent = true;
        var modalInstance = $uibModal.open({
            templateUrl: '/static/views/modals/uniUser.html',
            controller: StudentModalCtrl,
            scope: $scope,
            resolve: {
                user: function () {
                    return user;
                }
            }
        });
        modalInstance.result.then(function (value) {
            $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
        }, function (value) {
            $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
        });
    };


    var StudentModalCtrl = ['$scope', '$uibModalInstance', 'user', 'Restangular', '$log', '_',
        function ($scope, $uibModalInstance, user, Restangular, $log, _) {
            $scope.user = user;
            console.log($scope.user);
            $scope.ok = function () {
                if ($scope.user.id) {
                    Restangular.all('students').customPUT($scope.user).then(function (data) {
                        loadListOfStudents();
                    });
                } else {
                    Restangular.all('students').post($scope.user).then(function (data) {
                            loadListOfStudents();
                        },
                        // callback za gresku sa servera
                        function () {
                            $log.info('something went wrong!');
                        });
                }
                $uibModalInstance.close('ok');
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };


        }];

}]);
