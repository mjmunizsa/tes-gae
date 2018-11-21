'use strict';

angular.module('bookstore')
    .controller('ListCtrl', function ($scope, bookstore) {

        $scope.load = function() {
        	bookstore.list(function (list) {
                $scope.list = list.data;
            });
        }

        $scope.save = function() {
        	bookstore.save($scope.form, function() {
                $scope.load();
                $scope.form = {};
            });
        }
        
        $scope.search = function() {
        	if ($scope.strsearch != '') {
        		console.log($scope.strsearch);
        		bookstore.search($scope.strsearch,function(list) {
           	 $scope.list = list.data;
        	}
        	)} else {
        		$scope.load();
        	};
        }
        
        $scope.delete = function(id) {
        	bookstore.delete(id,function() {
        		$scope.load();
        	});
        }

        $scope.strsearch = '';
        $scope.form = {};

        $scope.load();
    });
