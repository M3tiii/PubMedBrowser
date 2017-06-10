class ArticleList {
  constructor(articleService) {
    this.articleService = articleService;
    this.articles = [];
    this.articles = this.articleService.get();
  }
}

export default ArticleList;
