'use strict';

angular.module('studiorum')
    .controller('SubjectController', ['$scope', 'Restangular', '$uibModal', '$log', '_', '$routeParams',
        function ($scope, Restangular, $uibModal, $log, _, $routeParams) {

            $scope.subject = {};
            $scope.duty = {};
            $scope.exam = {};
            $scope.searchText = "";
            $scope.dnd = {
                selectedStudent: null,
                listOfAllStudents: [],
                listOfAttendingStudents: [],
                // prevent user to add student on one exam more than once
                availableStudents: []
            };

            $scope.getSubject = function () {
                Restangular.one("subjects", $routeParams.id).get().then(function (subject) {
                    $scope.subject = subject;
                });
            };

            $scope.getDuties = function () {
                Restangular.one("subjects", $routeParams.id).all("duties").getList().then(function (duties) {
                    $scope.duties = duties;
                });
            };

            $scope.getStudents = function () {
                Restangular.one("subjects", $routeParams.id).all("students").getList().then(function (entries) {
                    $scope.dnd.listOfAttendingStudents = entries;
                });
                Restangular.all("students").getList().then(function (entries) {
                    var students = entries;
                    for (var i = 0, len = $scope.dnd.listOfAttendingStudents.length; i < len; i++) {
                        var index = null;
                        for (var j = 0, len2 = students.length; j < len2; j++) {
                            if (students[j].id == $scope.dnd.listOfAttendingStudents[i].id) {
                                index = j;
                                break;
                            }
                        }
                        if (index != null) {
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
                }, function (response) {
                    console.log(response);
                });

            };

            $scope.search = function () {
                if ($scope.searchText.length > 2) {
                    Restangular.all("students/search").customGETLIST('', {
                        page: "1",
                        size: "1000",
                        searchText: $scope.searchText
                    }).then(function (entries) {
                        var students = entries;
                        for (var i = 0, len = $scope.dnd.listOfAttendingStudents.length; i < len; i++) {
                            var index = null;
                            for (var j = 0, len2 = students.length; j < len2; j++) {
                                if (students[j].id == $scope.dnd.listOfAttendingStudents[i].id) {
                                    index = j;
                                    break;
                                }
                            }
                            if (index != null) {
                                students.splice(index, 1);
                            }
                        }
                        $scope.dnd.listOfAllStudents = students;
                    });
                }
                if ($scope.searchText.length == 0) {
                    Restangular.all("students").getList().then(function (entries) {
                        var students = entries;
                        for (var i = 0, len = $scope.dnd.listOfAttendingStudents.length; i < len; i++) {
                            var index = null;
                            for (var j = 0, len2 = students.length; j < len2; j++) {
                                if (students[j].id == $scope.dnd.listOfAttendingStudents[i].id) {
                                    index = j;
                                    break;
                                }
                            }
                            if (index != null) {
                                students.splice(index, 1);
                            }
                        }
                        $scope.dnd.listOfAllStudents = students;
                    });
                }
            };

            $scope.getProfessors = function () {
                Restangular.all("professors").getList().then(function (professors) {
                    $scope.professors = professors;
                });
            };

            $scope.getProfessorRoles = function () {
                Restangular.all("professorRoles/subjects/" + $routeParams.id).getList().then(function (data) {
                    $scope.professorRoles = data;
                });
            };

            $scope.openModalAddProfessor = function (subject) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/addProfessor.html',
                    controller: OpenModalAddProfessorCtrl,
                    scope: $scope,
                    resolve: {
                        subject: function () {
                            return subject;
                        }
                    }
                });
                modalInstance.result.then(function (value) {
                    $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
                }, function (value) {
                    $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
                });
            };

            var OpenModalAddProfessorCtrl = ['$scope', 'subject', '$uibModalInstance', 'Restangular', '$log', '_',
                function ($scope, subject, $uibModalInstance, Restangular, $log, _) {

                    $scope.professorRole = {};
                    $scope.professorRole.professorDTO = {};
                    $scope.professorRole.subjectDTO = subject;

                    $scope.ok = function () {
                        Restangular.all('professorRoles').post($scope.professorRole).then(function (data) {
                            $scope.getProfessorRoles();
                            $uibModalInstance.close('ok');
                        });
                    };

                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };
                }];

            $scope.deleteProfessorRole = function (id) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/delete.html',
                    controller: DeleteProfessorRoleModal,
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

            var DeleteProfessorRoleModal = ['$scope', '$uibModalInstance', 'id', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, id, Restangular, $log, _) {
                    $scope.ok = function () {
                        Restangular.one("professorRoles", id).remove().then(function () {
                            $scope.getProfessorRoles();
                        });
                        $uibModalInstance.close('ok');
                    };
                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };

                }];

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
            };

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
                    resolve: {}
                });
                modalInstance.result.then(function (value) {
                    $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
                }, function (value) {
                    $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
                });
            };

            var SuccessCtrl = ['$scope', '$uibModalInstance', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, Restangular, $log, _) {
                    $scope.ok = function () {
                        $uibModalInstance.close('ok');
                    };
                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };
                }];

            $scope.dutyExam = function (duty) {
                $scope.exam.dutyDTO = duty;
                $scope.examModal($scope.exam);
            };

            $scope.examModal = function (exam) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/exam.html',
                    controller: ExamModalCtrl,
                    scope: $scope,
                    resolve: {
                        exam: function () {
                            return exam;
                        }
                    }
                });
                modalInstance.result.then(function (value) {
                    $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
                }, function (value) {
                    $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
                });
            };

            var ExamModalCtrl = ['$scope', '$uibModalInstance', 'exam', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, exam, Restangular, $log, _) {
                    $scope.exam = exam;

                    $scope.ok = function () {
                        if (!$scope.exam.pass) {
                            $scope.exam.pass = false;
                        }
                        if ($scope.exam.id) {
                            Restangular.all('exams').customPUT($scope.exam).then(function (data) {

                            });
                        } else {
                            console.log("Broj studenta je: " + $scope.studentId);
                            var index = _.findIndex($scope.dnd.listOfAttendingStudents, function (student) {
                                return student.id == parseInt($scope.studentId)
                            });
                            console.log("INDEKS JE: " + index);
                            $scope.exam.studentDTO = $scope.dnd.listOfAttendingStudents[index];
                            Restangular.all('exams').post($scope.exam).then(function (data) {
                                    $scope.exam.pass = null;
                                    $scope.exam.points = null;
                                    $scope.getStudents();
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


                }
            ];

            $scope.findExams = function (duty) {
                var path = "/exams/duty/" + duty.id;
                Restangular.all(path).customGETLIST('', {}).then(function (exams) {
                    $scope.exams = exams;
                    $scope.examStudents($scope.exams);
                });

            };

            $scope.examStudents = function (exams) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/examStudents.html',
                    controller: ExamStudentModalCtrl,
                    scope: $scope,
                    resolve: {
                        exams: function () {
                            return exams;
                        }
                    }
                });
                modalInstance.result.then(function (value) {
                    $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
                }, function (value) {
                    $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
                });
            };

            var ExamStudentModalCtrl = ['$scope', '$uibModalInstance', 'exams', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, exams, Restangular, $log, _) {
                    $scope.ok = function () {
                        $uibModalInstance.close('ok');
                    };
                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };
                }];

            $scope.deleteExam = function (id) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/delete.html',
                    controller: ExamDeleteCtrl,
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

            var ExamDeleteCtrl = ['$scope', '$uibModalInstance', 'id', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, id, Restangular, $log, _) {
                    $scope.ok = function () {
                        Restangular.one("exams", id).remove().then(function () {
                            _.remove($scope.exams, {
                                id: id
                            });
                        });
                        $uibModalInstance.close('ok');
                    };
                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };

                }];

            $scope.editSubject = function (subject) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/static/views/modals/subject.html',
                    controller: EditSubjectCtrl,
                    scope: $scope,
                    resolve: {
                        subject: function () {
                            return subject;
                        }
                    }
                });
                modalInstance.result.then(function (value) {
                    $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
                }, function (value) {
                    $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
                });
            };

            var EditSubjectCtrl = ['$scope', '$uibModalInstance', 'subject', 'Restangular', '$log', '_',
                function ($scope, $uibModalInstance, subject, Restangular, $log, _) {
                    $scope.subject = subject;
                    $scope.ok = function () {
                        Restangular.all('subjects').customPUT($scope.subject).then(function (data) {

                        });
                        $uibModalInstance.close('ok');
                    };
                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };

                }];

            $scope.getSubject();
            $scope.getStudents();
            $scope.getDuties();
            $scope.getProfessors();
            $scope.getProfessorRoles();

        }]);
