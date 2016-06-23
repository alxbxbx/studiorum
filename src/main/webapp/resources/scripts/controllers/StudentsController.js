'use strict';

angular.module('studiorum').controller('StudentsController', ['$scope', 'Restangular', '$uibModal', '$log', '_', function ($scope, Restangular, $uibModal, $log, _) {

    // Initialization
	$scope.maxSize = 5;
	$scope.searchText = "";
	var isSearch = false;
	
	//Max page
	$scope.bigTotalItems = null;
	
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
    
    loadSearchResults();

    // On Click Events
    $scope.clickDeleteUser = function (id) {
        if (confirm("Are you sure?")) {
            Restangular.one("students", id).remove().then(function () {
                loadListOfStudents($scope.bigCurrentPage - 1);
            });
        }
    }
    
    function loadStudentsCount(){
    	//helper variable so we have actual page remembered
    	var hesoyam = $scope.bigCurrentPage;
    	Restangular.one("students/count", "").get().then(function(count){
    		$scope.count = (count / 20) * 10;
    		$scope.bigTotalItems = $scope.count;
    		
    		$scope.bigCurrentPage = hesoyam;
    		//We have to reload students on current page because when loadStudentsCount() method is finished
    		//$scope.pageChanged is triggered because $scope.bigTotalItems is changed, and it automatically sets
    		//$scope.bigCurrentPage to 1, so we needed to prevent that so our current page is always
    		//page where we were before browser reloaded
    		$scope.pageChanged();
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
    function loadSearchResults(searchText, page) {
        Restangular.all("students/search").customGETLIST('', {
            page: page, 
            size: "20",
            searchText: searchText
        }).then(function (students) {
        	$scope.students = students;
        });
    }
    function loadSearchResultsCount(){
    	//helper variable so we have actual page remembered
    	var hesoyam = $scope.bigCurrentPage;
    	Restangular.one("students/searchCount", "").get().then(function(count){
    		$scope.count = (count / 20) * 10;
    		$scope.bigTotalItems = $scope.count;
    		
    		$scope.bigCurrentPage = hesoyam;
    		//We have to reload students on current page because when loadSearchResultsCount() method is finished
    		//$scope.pageChanged is triggered because $scope.bigTotalItems is changed, and it automatically sets
    		//$scope.bigCurrentPage to 1, so we needed to prevent that so our current page is always
    		//page where we were before browser reloaded
    		$scope.pageChanged();
    	});
    }
    $scope.pageChanged = function(){
    	if(isSearch == false){
    		localStorage.setItem('pageNumber', $scope.bigCurrentPage - 1);
        	loadListOfStudents($scope.bigCurrentPage - 1);
    	}else{
    		var page = eval($scope.bigCurrentPage) - 1;
    		loadSearchResults($scope.searchText, page);
    	}
    	
    }
    
    $scope.search = function(){
    	isSearch = true;
    	if($scope.searchText.length > 2){
    		loadSearchResults($scope.searchText);
    		$scope.bigCurrentPage = 1;
    		loadSearchResultsCount();
    	}
    	if($scope.searchText.length == 0){
    		isSearch = false;
    		var page = localStorage.getItem('pageNumber');
    		loadListOfStudents(page);
    		$scope.bigCurrentPage = eval(page) + 1;
    		loadStudentsCount();
    	}
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
            $scope.ok = function () {
                if ($scope.user.id) {
                    Restangular.all('students').customPUT($scope.user).then(function (data) {
                        loadListOfStudents($scope.bigCurrentPage - 1);
                    });
                } else {
                    Restangular.all('students').post($scope.user).then(function (data) {
                            loadListOfStudents($scope.bigCurrentPage - 1);
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
