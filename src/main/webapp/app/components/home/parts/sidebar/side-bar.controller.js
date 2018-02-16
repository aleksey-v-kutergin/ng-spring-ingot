(function () {
    'use strict';

    angular
        .module('sideBarModule')
        .controller('sideBarController', function ($scope) {
            $scope.isOpened = true;

            $scope.toggleSideBar = function () {
                $scope.isOpened = !$scope.isOpened;
            }
            
        });

})();