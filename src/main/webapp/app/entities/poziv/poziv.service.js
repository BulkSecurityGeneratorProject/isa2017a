(function() {
    'use strict';
    angular
        .module('isaApp')
        .factory('Poziv', Poziv);

    Poziv.$inject = ['$resource'];

    function Poziv ($resource) {
        var resourceUrl =  'api/pozivs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
