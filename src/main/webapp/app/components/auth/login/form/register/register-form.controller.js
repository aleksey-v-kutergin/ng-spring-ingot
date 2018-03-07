(function () {
    'use strict';

    angular
        .module('authModule')
        .controller('registerFormController', function ($scope) {

            $scope.onCancelClick = function (event) {
                $scope.$emit('event:register-cancel-btn-click', event);
            }

        });

})();