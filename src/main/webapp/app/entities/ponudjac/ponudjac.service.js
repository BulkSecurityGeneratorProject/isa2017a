(function() {
    'use strict';
    angular
        .module('isaApp')
        .factory('Ponudjac', Ponudjac);

    Ponudjac.$inject = ['$resource'];

    function Ponudjac ($resource) {
        var resourceUrl =  'api/ponudjacs/:id';

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
