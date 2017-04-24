class SearchController {
  constructor($stateParams, $location) {
    const searchParams = $location.search();
    this.location = $location;
    this.searchText = searchParams.search ? searchParams.search : '';
    this.articleId = searchParams.article ? searchParams.article : null;
  }

  search() {
    console.log('search: ', this.searchText, this.articleId);
    this.location.search('search', this.searchText);
  }
}

export default SearchController;
