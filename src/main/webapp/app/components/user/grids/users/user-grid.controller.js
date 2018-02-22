(function () {
    'use strict';
    
    angular
        .module('userModule')
        .controller('userGridController', function ($scope, $timeout, userService) {
            $scope.columns = [
                { field: 'id',                name: 'id',                 displayName: 'Идентификатор', type: 'number', visible: false },
                { field: 'name',              name: 'name',               displayName: 'ФИО' },
                { field: 'email',             name: 'email',              displayName: 'Электронная почта' },
                { field: 'roleType',          name: 'roleType',           displayName: 'Роль' },
                { field: 'registrationDate',  name: 'registrationDate',   displayName: 'Дата регистрации' },
                { field: 'isPasswordExpired', name: 'isPasswordExpired',  displayName: 'Просрочен ли пароль' }
            ];

            $scope.grid = {
                columnDefs: $scope.columns,
                enableRowSelection: true,
                enableRowHeaderSelection: false,
                multiSelect: false,
                enableHorizontalScrollbar: 0,
                enableVerticalScrollbar: 0,
                onRegisterApi: function (api) {
                    $scope.gridApi = api;
                    bind(api);
                }
            };

            var bind = function (gridApi) {

                gridApi.selection.on.rowSelectionChanged($scope, function (row) {

                });

                $scope.$on('side-bar-toggled', function (event, data) {
                    resize();
                });
            };

            var fetchUsers = function () {
                userService.getAllUsers().then(function (data) {
                    $scope.grid.data = data;
                }, function (error) {
                    console.log(error);
                });
            };

            var resize = function () {
                $timeout(function () {
                    $scope.gridApi.core.handleWindowResize();
                });
            };

            fetchUsers();
        });
    
})();