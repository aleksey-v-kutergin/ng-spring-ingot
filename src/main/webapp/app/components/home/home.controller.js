(function () {
    'use strict';

    angular
        .module('homeModule', [
                'ngMaterial',
                'ngMdIcons',
                'appCommons',
                'headerBarModule',
                'sideBarModule',
                'statusBarModule'
        ])
        .controller('homeController', function ($scope, $http) {
            $http.get('api/user/all').then(function (response) {
                var msg = 'Database users:';
                response.data.forEach(function (user) {
                    msg += "\n\t" + JSON.stringify(user);
                });
                $scope._message = msg;
            }, function (error) {
                console.log(error);
            });
        });

})();