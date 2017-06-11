import template from './articleList.template.html';
import controller from './articleList.controller';

/** @ngInject */
const ArticleListComponent = {
  template,
  controller: [controller]
};

export default ArticleListComponent;
