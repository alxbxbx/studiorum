'use strict';

angular.module('studiorum').controller('SubjectsController', function () {

	Restangular.all("subjects").getList().then(function(entries) {
		$scope.subjects = subjects;
    });

});
