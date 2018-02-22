<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>

<!-- Точка входа в приложение -->
<html lang="ru" ng-app="ngSpringIngot" ng-controller="AppController">

    <head>
        <meta charset="utf-8">
        <base href="/ng-spring-ingot/">
        <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>NgSpringIngot</title>

        <!-- Third party libs  -->

        <!-- jQuery -->
        <script src="vendor/jquery/dist/jquery.js"></script>
        <script src="vendor/jquery-ui/jquery-ui.min.js"></script>

        <!-- AngularJS  -->
        <script src="vendor/angular/angular.min.js"></script>
        <script src="vendor/angular-ui-router/release/angular-ui-router.min.js"></script>
        <script src="vendor/angular-modal-service/dst/angular-modal-service.min.js"></script>

        <!-- Ui-grid  -->
        <script src="vendor/angular-ui-grid/ui-grid.min.js"></script>
        <link rel="stylesheet" type="text/css" href="vendor/angular-ui-grid/ui-grid.css">

        <!-- App-wide scripts and app css -->
        <script src="assets/js/script.js"></script>
        <link rel="stylesheet" type="text/css" href="assets/css/normalize.css">
        <link rel="stylesheet" type="text/css" href="assets/css/main.css">

        <!-- App module -->
        <script src="app/app.js"></script>

        <!-- Core module -->
        <script src="app/components/services/core.module.js"></script>
        <script src="app/components/services/user.service.js"></script>
        <script src="app/components/services/app-module.service.js"></script>

        <!-- Auth module -->
        <script src="app/components/auth/auth.module.js"></script>

        <!-- User module -->
        <script src="app/components/user/user.module.js"></script>
        <script src="app/components/user/grids/users/user-grid.directive.js"></script>
        <script src="app/components/user/grids/users/user-grid.controller.js"></script>

        <!-- App navigation module -->
        <script src="app/components/navigation/app-navigation.module.js"></script>
        <script src="app/components/navigation/appmodulelist/app-module-list.directive.js"></script>
        <script src="app/components/navigation/appmodulelist/app-module-list.controller.js"></script>

        <!-- Home module -->
        <script src="app/components/home/home.module.js"></script>
        <script src="app/components/home/home.controller.js"></script>

        <script src="app/components/home/parts/header/header-bar.directive.js"></script>
        <script src="app/components/home/parts/header/parts/help/help-bar.directive.js"></script>
        <script src="app/components/home/parts/header/parts/logout/logout-bar.directive.js"></script>
        <script src="app/components/home/parts/header/parts/settings/settings-bar.directive.js"></script>
        <script src="app/components/home/parts/header/parts/toolbar/header-toolbar.directive.js"></script>
        <script src="app/components/home/parts/header/parts/user/user-bar.directive.js"></script>

        <script src="app/components/home/parts/sidebar/side-bar.directive.js"></script>
        <script src="app/components/home/parts/sidebar/side-bar.controller.js"></script>

        <script src="app/components/home/parts/footer/footer-bar.directive.js"></script>

        <!-- Geo module -->
        <script src="app/components/geo/geo.module.js"></script>

        <!-- Analytics module -->
        <script src="app/components/analytics/analytics.module.js"></script>

    </head>

    <body>
        <ui-view ui-i18n="{{_lang}}" name="main"></ui-view>
    </body>

</html>