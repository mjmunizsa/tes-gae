'use strict';

angular.module('bookstore')
    .service('bookstore', function ($http) {
        return {
            list: function (success) {
                return $http.get("/rest/bookstore").then(success);
            },
            save: function (bookstore, success) {
                return $http.post("/rest/bookstore", bookstore).then(success);
            },
            search: function (strsearch, success) {
            	console.log("Servicio " + strsearch);
                return $http.get("/rest/bookstore/search/"+strsearch).then(success);
            },
            delete: function (id, success) {
            	console.log("Servicio " + id);
                return $http.delete("/rest/bookstore/"+id).then(success);
            },
            getById: function(id,success) {
            	return $http.get("/rest/bookstore/"+id).then(success);
            }
        };
    });
