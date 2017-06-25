class ArticleList {
  constructor($scope, articleService) {
    this.articleService = articleService;
    this.articles = [];
    this.init = true;
    this.maxScore = 1;
    $scope.$on('update', () => {
      this.articles = this.articleService.get();
      this.maxScore = this.articles.length > 0 ? this.articles[0].score : 1;
      this.articles.forEach(this.transform.bind(this, this.maxScore));
      this.init = false;
      console.log('result', [this.articles]);
    });
  }

  transform(max, article) {
    article.lScore = article.score;
    article.score = Math.round((article.score / max) * 100);
    return article;
  }
}

export default ArticleList;
