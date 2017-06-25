import angular from 'angular';

// import Pages from './app/pages';
// import Services from './app/services';
// import Components from './app/components';
import {
  articleListModule
} from './app/components/articleList/index';

import {
  searchModule
} from './app/pages/search/index';
import articleService from './app/services/articleService';
import uiRouter from 'angular-ui-router';

import 'angular-ui-bootstrap';
import 'angular-animate';
import 'angular-touch';
import 'angular-sanitize';

import './index.scss';

const app = angular.module('app', ['ui.bootstrap', 'ngAnimate', 'ngTouch', 'ngSanitize', uiRouter, searchModule, articleListModule])
  .service('articleService', ['$http', articleService])
  // .config(routesConfig)
  .name;

// function routesConfig($stateProvider, $urlRouterProvider, $locationProvider) {
//   $locationProvider.html5Mode(true).hashPrefix('!');
//   $urlRouterProvider.otherwise('/');
// }

export default app;
