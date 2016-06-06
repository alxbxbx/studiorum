'use strict';

angular.module('studiorum').controller('LanguagesModalController', ['$translate', '$rootScope', '$scope', '$uibModalInstance',
  function ($translate, $rootScope, $scope, $uibModalInstance) {

    $scope.ok = function () {
      $uibModalInstance.close('ok');
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss('cancel');
    };

    $scope.changeLanguage = function (languageKey) {
      $translate.use(languageKey);
      $rootScope.lang = languageKey;
      localStorage.setItem('languageKey', languageKey);
    };

  }]);
