'use strict';

angular.module('studiorum')
    .controller('StudentController', ['$rootScope', '$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams', 'Upload', '$timeout', '$http', '$location',
        function ($rootScope, $scope, Restangular, $uibModal, $log, _, $routeParams, Upload, $timeout, $http, $location) {

            // Initialization
            $scope.user = {};
            $scope.loading = false;
            $scope.user.isStudent = true;
            $scope.studentSubjectList = [];
            $scope.subjects = [];
            $scope.payments = [];

            $scope.getPayments = function () {
                Restangular.one("students/" + $routeParams.id + "/payments").get().then(function (payments) {
                    $scope.payments = payments;
                });
            };

            $scope.getFiles = function () {
                Restangular.one("students/" + $routeParams.id + "/files").get().then(function (files) {
                    $scope.files = files;
                    for (var i = 0; i < $scope.files.length; i++) {
                        if ($scope.files[i].name.indexOf("pdf") > -1) {
                            $scope.files[i].isPdf = true;
                        }
                    }
                });
            };

            $scope.getSubjects = function () {
                Restangular.one("students/" + $routeParams.id + "/subjects").get().then(function (subjects) {
                    $scope.subjects = subjects;
                });
            };
            
            $scope.getStudentSubjectList = function() {
            	var path = "studentsubject/student/" + $routeParams.id;
            	Restangular.all(path).getList().then(function (studentsubjects){
            		$scope.studentSubjectList = studentsubjects;
            	});
            }

            $scope.goTo = function (id) {
                $location.path("/subjects/" + id);
            };

            $scope.getStudent = function () {
                Restangular.one("students", $routeParams.id).get().then(function (user) {
                    $scope.user = user;
                });
            };

            $scope.getExams = function () {
                Restangular.one("students/" + $routeParams.id + "/exams").get().then(function (exams) {
                    $scope.exams = exams;
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

            $scope.downloadFile = function (fileId) {
                var path = "/download/" + $routeParams.id + "/" + fileId;
                window.open(path);
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
            $scope.uploadPicture = function (file) {
                $scope.file = file;
                if (file) {
                    $scope.loading = true;
                    Upload.upload({
                        url: '/api/students/' + $scope.user.id + '/pictures',
                        data: {file: file}
                    }).then(function (response) {
                        $timeout(function () {
                            $scope.getStudent();
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
                    templateUrl: '/static/views/modals/editStudent.html',
                    controller: StudentEditCtrl,
                    scope: $scope,
                    resolve: {
                        user: function () {
                            return user;
                        }
                    }
                });
                modalInstance.result.then(function (value) {
                    $scope.getStudent();
                }, function (value) {
                    $scope.getStudent();
                });
            };
            
            var StudentEditCtrl = ['$scope', '$uibModalInstance', 'user', 'Restangular', '$log', '_',
             function ($scope, $uibModalInstance, user, Restangular, $log, _) {
                 $scope.ok = function () {
                 	if($scope.user.password != $scope.user.passwordAgain){
                 		$scope.passwordModal();
                 	}else if(($scope.user.name == "") || ($scope.user.lastName == "") || ($scope.user.username == "")){
                 		$scope.wrongModal();
                 	}
                 	else{
                 		if ($scope.user.id) {
                             Restangular.all('students').customPUT($scope.user).then(function (data) {
                             });
                         }
                 		$uibModalInstance.close('ok');  
                         
                 	}
                 };

                 $scope.cancel = function () {
                     $uibModalInstance.dismiss('cancel');
                 };


             }];
            $scope.passwordModal = function () {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/noPassword.html',
                    controller: 'PasswordModalController',
                    scope: $scope,
                    resolve: {
                    }
                });
                modalInstance.result.then(function (value) {
                }, function (value) {
                });
            };
            $scope.wrongModal = function () {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/wrongData.html',
                    controller: 'PasswordModalController',
                    scope: $scope,
                    resolve: {
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

            $scope.paymentModal = function (payment) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/payment.html',
                    controller: PaymentModalCtrl,
                    scope: $scope,
                    resolve: {
                        payment: function () {
                            return payment;
                        }
                    }
                });
                modalInstance.result.then(function (value) {
                }, function (value) {
                });
            };
            var PaymentModalCtrl = ['$scope', '$uibModalInstance', 'payment', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, payment, Restangular, $log, _) {
                    $scope.payment = payment;
                    if ($scope.payment == null) {
                        $scope.payment = {};
                    }
                    $scope.payment.studentDTO = $scope.user;
                    $scope.ok = function () {
                        if ($scope.payment.id) {
                            Restangular.all('payments').customPUT($scope.payment).then(function (data) {
                                $scope.getPayments();
                            });
                        } else {
                            Restangular.all('payments').post($scope.payment).then(function (data) {
                                    $scope.getPayments();
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

            $scope.deletePayment = function (id) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/delete.html',
                    controller: PaymentDeleteCtrl,
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
            var PaymentDeleteCtrl = ['$scope', '$uibModalInstance', 'id', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, id, Restangular, $log, _) {
                    $scope.ok = function () {
                        Restangular.one("payments", id).remove().then(function () {
                            $scope.getPayments();
                        });
                        $uibModalInstance.close('ok');
                    };

                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };


                }];

            $scope.getStudent();
            $scope.getStudentSubjectList();
            $scope.getFiles();
            $scope.getSubjects();
            $scope.getPayments();
            $scope.getExams();

        }]);
