class SearchController {
  constructor($scope, $stateParams, $location, articleService) {
    this.articleService = articleService;
    this.advanceName = '';
    this.advance1 = false;
    this.advance2 = false;

    this.searchResult = [];
    // this.searchResult = this.articleService.get();

    const searchParams = $location.search();
    this.location = $location;
    this.searchText = searchParams.search ? searchParams.search : '';
    this.articleId = searchParams.article ? searchParams.article : null;

    this.scope = $scope;
    $scope.updateEmit = function() {
      $scope.$broadcast('update');
    };
  }

  search() {
    this.searchResult = [];
    this.articleService.data = [];
    this.scope.updateEmit();

    const searchData = {
      text: this.searchText,
      author: this.advanceName,
      isAdvance: this.advance1
    };

    // console.log('search: ', this.searchText, this.articleId);
    this.articleService.search(searchData).then(res => {
      // this.location.search('search', this.searchText);
      this.searchResult = this.searchResult.concat(this.searchResult, res);
      this.scope.updateEmit();
    });
  }
}

export default SearchController;
