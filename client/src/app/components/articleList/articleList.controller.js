class ArticleList {
  constructor($scope, articleService) {
    this.articleService = articleService;
    this.articles = [];

    $scope.$on('update', () => {
      this.articles = this.articleService.get();
      this.articles.forEach(this.transform);
    });
  }

  transform(article) {
    article.score = Math.round(article.score * 25);
    return article;
  }
}

export default ArticleList;
