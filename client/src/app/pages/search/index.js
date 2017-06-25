// import angular from 'angular';
//
// import SearchController from './search.controller';
// import SearchTemplate from './search.template.html';
//
// const search = angular.module('PubMedBrowser.pages.search', [])
//   .controller('SearchController', SearchController)
//   .config(config)
//   .name;
//
// /** @ngInject */
// function config($stateProvider) {
//   $stateProvider
//     .state('search', {
//       url: '/?search&article',
//       template: SearchTemplate,
//       controller: 'SearchController',
//       controllerAs: 'ctrl'
//     });
// }
//
// export default search;
import angular from 'angular';

import template from './search.template.html';
import controller from './search.controller';

export const searchModule = 'search';

/** @ngInject */
const SearchComponent = {
  template,
  controller: ['$scope', '$stateParams', '$location', 'articleService', controller]
};

angular
  .module(searchModule, [])
  .component('search', SearchComponent);
