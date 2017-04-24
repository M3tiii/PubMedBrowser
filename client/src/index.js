import angular from 'angular';

import Pages from './app/pages';
// import Services from './app/services';
import Components from './app/components';
import uiRouter from 'angular-ui-router';

import './index.scss';

const app = angular.module('app', [uiRouter, Pages, Components])
  .config(routesConfig)
  .name;

function routesConfig($stateProvider, $urlRouterProvider, $locationProvider) {
  $locationProvider.html5Mode(true).hashPrefix('!');
  $urlRouterProvider.otherwise('/');
}

export default app;
