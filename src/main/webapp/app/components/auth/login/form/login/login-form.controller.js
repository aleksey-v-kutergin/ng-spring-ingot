(function () {
    'use strict';

    angular
        .module('authModule')
        .controller('loginFormController', function ($scope, userService) {

            $scope.onRegisterClick = function (event) {
                $scope.$emit('event:register-btn-click', event);
            };

            $scope.onSubmitClick = function (event) {
                userService.authenticateUser($scope.credentials).then(function (success) {

                }, function (error) {

                });
            };

        });

})();