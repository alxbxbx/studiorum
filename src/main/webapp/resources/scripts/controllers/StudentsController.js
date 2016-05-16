'use strict';

angular.module('studiorum').controller('StudentsController', function () {

	Restangular.all("students").getList().then(function(entries) {
		$scope.students = students;
    });

});
