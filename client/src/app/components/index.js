import angular from 'angular';

import ArticleComponents from './article/article.component';
import ArticleListComponents from './articleList/articleList.component';

const components = angular.module('PubMedBrowser.component', ['ui.bootstrap', 'ngAnimate', 'ngTouch', 'ngSanitize'])
  .component('article', ArticleComponents)
  .component('articleList', ArticleListComponents)
  .name;

export default components;
