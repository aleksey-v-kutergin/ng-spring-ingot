<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>

<!-- Точка входа в приложение -->
<html lang="ru" ng-app="ngSpringIngot" ng-controller="AppController">

    <head>
        <meta charset="utf-8">
        <base href="/ng-spring-ingot/">
        <title>NgSpringIngot</title>

        <!-- jQuery -->
        <script src="vendor/jquery/dist/jquery.js"></script>
        <script src="vendor/jquery-ui/jquery-ui.min.js"></script>

        <link rel="stylesheet" type="text/css" href="vendor/angular-material/angular-material.css">
        <link rel="stylesheet" type="text/css" href="vendor/angular-ui-grid/ui-grid.css">

        <!-- AngularJS  -->
        <script src="vendor/angular/angular.min.js"></script>
        <script src="vendor/angular-aria/angular-aria.js"></script>
        <script src="vendor/angular-animate/angular-animate.js"></script>
        <script src="vendor/angular-material/angular-material.js"></script>
        <script src="vendor/angular-material-icons/angular-material-icons.min.js"></script>
        <script src="vendor/angular-ui-router/release/angular-ui-router.min.js"></script>
        <!-- <script src="vendor/angular-modal-service/dst/angular-modal-service.min.js"></script> -->

        <!-- Либы  -->
        <script src="vendor/angular-ui-grid/ui-grid.min.js"></script>


        <!-- Скрипты приложения -->
        <script src="app/app.js"></script>
        <script src="app/components/common/user/user-avatar.directive.js"></script>
        <script src="app/components/home/home.controller.js"></script>
        <script src="app/components/home/parts/header/header-bar.directive.js"></script>
        <script src="app/components/home/parts/sidebar/side-bar.directive.js"></script>
        <script src="app/components/home/parts/footer/status-bar.directive.js"></script>

        <!-- Дизайн -->
        <script src="assets/js/script.js"></script>
        <link rel="stylesheet" type="text/css" href="vendor/angular-ui-grid/ui-grid.css">
        <link rel="stylesheet" type="text/css" href="assets/css/main.css">

    </head>

    <body ng-cloak>
        <ui-view ui-i18n="{{_lang}}" name="main"></ui-view>
    </body>

</html>