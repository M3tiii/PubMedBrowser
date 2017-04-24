import angular from 'angular';

import ArticleComponents from './article/article.component';

const components = angular.module('PubMedBrowser.component', [])
  .component('article', ArticleComponents)
  .name;

export default components;
