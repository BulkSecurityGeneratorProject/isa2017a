(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('RasporedSmenaZaSankereController', RasporedSmenaZaSankereController);

    RasporedSmenaZaSankereController.$inject = ['RasporedSmenaZaSankere'];

    function RasporedSmenaZaSankereController(RasporedSmenaZaSankere) {

        var vm = this;

        vm.rasporedSmenaZaSankeres = [];

        loadAll();

        function loadAll() {
            RasporedSmenaZaSankere.query(function(result) {
                vm.rasporedSmenaZaSankeres = result;
                vm.searchQuery = null;
            });
        }
    }
})();
