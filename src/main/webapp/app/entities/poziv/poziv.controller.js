(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PozivController', PozivController);

    PozivController.$inject = ['Poziv'];

    function PozivController(Poziv) {

        var vm = this;

        vm.pozivs = [];

        loadAll();

        function loadAll() {
            Poziv.query(function(result) {
                vm.pozivs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
