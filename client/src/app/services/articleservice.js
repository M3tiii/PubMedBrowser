export default function articleService($http) {
  this.data = [];

  this.get = function() {
    return this.data;
  };

  this.search = function(data) {
    return $http({
      method: 'POST',
      url: 'http://0.0.0.0:4567/search',
      data
    }).then(response => {
      this.data = response.data;
      return this.data;
    });
  };
}
