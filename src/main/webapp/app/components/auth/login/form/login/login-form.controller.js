(function () {
    'use strict';

    angular
        .module('authModule')
        .controller('loginFormController', function ($scope, userService) {

            $scope.onRegisterClick = function (event) {
                $scope.$emit('event:register-btn-click', event);
            };

            $scope.onSubmitClick = function (event) {
                console.log($scope.credentials);
                userService.authenticateUser(credentials)
            };

        });

})();