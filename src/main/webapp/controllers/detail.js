'use strict';

angular.module('bookstore')
    .controller('DetailCtrl', function ($scope, $routeParams, bookstore) {
    	$scope.getById=function() {
        	bookstore.getById($routeParams.id,function(resultBook) {
        		 $scope.book = resultBook.data;
        	});
    	}
    	$scope.getById();
    });
