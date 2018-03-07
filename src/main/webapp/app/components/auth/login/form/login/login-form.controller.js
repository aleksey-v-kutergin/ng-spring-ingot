(function () {
    'use strict';

    angular
        .module('authModule')
        .controller('loginFormController', function ($scope) {

            $scope.onRegisterClick = function (event) {
                $scope.$emit('event:register-btn-click', event);
            }

        });

})();