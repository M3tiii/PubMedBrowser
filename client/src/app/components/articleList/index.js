import angular from 'angular';

import template from './articleList.template.html';
import controller from './articleList.controller';

export const articleListModule = 'articleList';

/** @ngInject */
const ArticleListComponent = {
  template,
  controller: ['articleService', controller]
};

angular
  .module(articleListModule, [])
  .component('articleList', ArticleListComponent);
