 'use strict';

angular.module('studiorum')
    .controller('StudentController', ['$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams', 'Upload', '$timeout', '$http', '$location', 'pdfDelegate',
        function ($scope, Restangular, $uibModal, $log, _, $routeParams, Upload, $timeout, $http, $location, pdfDelegate) {
    		
            // Initialization
            $scope.user = {};
            $scope.loading = false;
            $scope.user.isStudent = true;
          

            $scope.getFiles = function () {
                Restangular.one("students/" + $routeParams.id + "/files").get().then(function (files) {
                    $scope.files = files;
                    for(var i = 0; i < $scope.files.length; i++){
                    	if($scope.files[i].name.indexOf("pdf") > - 1){
                    		$scope.files[i].isPdf = true;
                    	}
                    }
                });
            };
            
            $scope.getSubjects = function() {
            	Restangular.one("students/" + $routeParams.id + "/subjects").get().then(function (subjects) {
                    $scope.subjects = subjects;
                });
            }
            $scope.goTo = function(id){
            	$location.path("/subjects/" + id);
            }

            $scope.getStudent = function () {
                Restangular.one("students", $routeParams.id).get().then(function (user) {
                    $scope.user = user;
                });
            };


            $scope.previewFile = function (fileId) {
                var path = '/api/students/' + $routeParams.id + "/files/" + fileId;
                $http.get(path, {responseType: 'arraybuffer'})
                .success(function (data) {
                    var file = new Blob([data], {type: 'application/pdf'});
                    var fileURL = URL.createObjectURL(file);
                    window.open(fileURL);
             });
            };

            $scope.uploadFile = function (file) {
                $scope.file = file;
                if (file) {
                    $scope.loading = true;
                    Upload.upload({
                        url: '/api/students/' + $scope.user.id + '/files',
                        data: {file: file}
                    }).then(function (response) {
                        $timeout(function () {
                            $scope.getFiles();
                            $scope.loading = false;
                        });
                    }, function (response) {
                        $scope.loading = false;
                    }, function (evt) {
                        $scope.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                    });
                }
            };

            $scope.openModal = function (user) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/studentModal.html',
                    controller: 'UserModalController',
                    scope: $scope,
                    resolve: {
                        user: function () {
                            return user;
                        }
                    }
                });
                modalInstance.result.then(function (value) {
                }, function (value) {
                });
            };
            
            $scope.deleteModal = function (id) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/delete.html',
                    controller: DocumentDeleteCtrl,
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
            };
            
            var DocumentDeleteCtrl = ['$scope', '$uibModalInstance', 'id', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, id, Restangular, $log, _) {
                    $scope.ok = function () {
                    	Restangular.one('students', $routeParams.id).one('files', id).remove().then(function () {
                            $scope.getFiles();
                        });
                        $uibModalInstance.close('ok');
                    };

                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };


                }];

            // Pull data
            $scope.getStudent();
            $scope.getFiles();
            $scope.getSubjects();

        }]);
