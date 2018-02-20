(function () {
    'use strict';

    angular
        .module('sideBarModule')
        .controller('sideBarController', function ($scope, $rootScope) {
            $scope.isOpened = true;

            $scope.toggleSideBar = function () {
                $scope.isOpened = !$scope.isOpened;
                $rootScope.$broadcast('side-bar-toggled', {})
            }
            
        });

})();