class SearchController {
  constructor($stateParams, $location, articleService) {
    this.articleService = articleService;
    this.advanceName = '';
    this.advance1 = false;
    this.advance2 = false;

    this.searchResult = [];
    this.searchResult = this.articleService.get();

    const searchParams = $location.search();
    this.location = $location;
    this.searchText = searchParams.search ? searchParams.search : '';
    this.articleId = searchParams.article ? searchParams.article : null;
  }

  search() {
    const searchData = {
      text: this.searchText,
      author: this.advanceName,
      isAdvance: this.advance1
    };

    console.log('search: ', this.searchText, this.articleId);
    this.location.search('search', this.searchText);
    this.articleService.search(searchData);
  }
}

export default SearchController;
