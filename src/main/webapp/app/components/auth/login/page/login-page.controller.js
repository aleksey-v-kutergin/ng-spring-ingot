(function () {
    'use strict';

    angular
        .module('authModule')
        .controller('loginPageController', function ($scope) {

            $scope.needRegister = false;

            var _bind = function () {
                $scope.$on('event:register-btn-click', function (event) {
                    $scope.needRegister = true;
                });

                $scope.$on('event:register-cancel-btn-click', function (event) {
                    $scope.needRegister = false;
                });
            };

            _bind();

        });

})();