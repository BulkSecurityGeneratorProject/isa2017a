(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PorudzbinaController', PorudzbinaController);

    PorudzbinaController.$inject = ['Porudzbina'];

    function PorudzbinaController(Porudzbina) {

        var vm = this;

        vm.porudzbinas = [];

        loadAll();

        function loadAll() {
            Porudzbina.query(function(result) {
                vm.porudzbinas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
