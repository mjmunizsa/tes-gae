'use strict';

angular.module('bookstore')
    .controller('ListCtrl', function ($scope, bookstore) {

        $scope.load = function() {
        	bookstore.list(function (list) {
                $scope.list = list.data;
            });
        }

        $scope.save = function() {
        	$scope.tituloInvalido = false;
        	$scope.autorInvalido = false;
        	$scope.generoInvalido = false;
        	$scope.anioInvalido = false;

        	if (!$scope.form.title.$valid) {
        		$scope.tituloInvalido = true;
        	}
        	if (!$scope.form.author.$valid) {
        		$scope.autorInvalido = true;
        	}
        	
        	if (!$scope.form.genre.$valid) {
        		$scope.generoInvalido = true;	
        	} 
        	
        	if (!$scope.form.year.$valid) {
        		$scope.anioInvalido = true;
        	} 
        	
        	if ($scope.form.$valid) {
	        	bookstore.save($scope.book, function() {
	        		$scope.load();
	        		$scope.book = {};
	        	});
        	}
        	
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
