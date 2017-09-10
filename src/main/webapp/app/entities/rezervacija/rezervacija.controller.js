(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('RezervacijaController', RezervacijaController);

    RezervacijaController.$inject = ['Rezervacija'];

    function RezervacijaController(Rezervacija) {

        var vm = this;

        vm.rezervacijas = [];

        loadAll();

        function loadAll() {
            Rezervacija.query(function(result) {
                vm.rezervacijas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
