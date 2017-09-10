(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PonudjacController', PonudjacController);

    PonudjacController.$inject = ['Ponudjac'];

    function PonudjacController(Ponudjac) {

        var vm = this;

        vm.ponudjacs = [];

        loadAll();

        function loadAll() {
            Ponudjac.query(function(result) {
                vm.ponudjacs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
