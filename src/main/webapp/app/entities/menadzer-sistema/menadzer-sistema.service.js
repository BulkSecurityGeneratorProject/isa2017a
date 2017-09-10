(function() {
    'use strict';
    angular
        .module('isaApp')
        .factory('MenadzerSistema', MenadzerSistema);

    MenadzerSistema.$inject = ['$resource'];

    function MenadzerSistema ($resource) {
        var resourceUrl =  'api/menadzer-sistemas/:id';

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
