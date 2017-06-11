class ArticleList {
  constructor($scope, articleService) {
    this.articleService = articleService;
    this.articles = [];

    $scope.$on('update', () => {
      this.articles = this.articleService.get();
    });
  }
}

export default ArticleList;
