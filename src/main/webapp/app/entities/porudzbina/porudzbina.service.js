(function() {
    'use strict';
    angular
        .module('isaApp')
        .factory('Porudzbina', Porudzbina);

    Porudzbina.$inject = ['$resource'];

    function Porudzbina ($resource) {
        var resourceUrl =  'api/porudzbinas/:id';

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
