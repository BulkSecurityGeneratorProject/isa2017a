(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('ZahtevZaPrijateljstvoController', ZahtevZaPrijateljstvoController);

    ZahtevZaPrijateljstvoController.$inject = ['ZahtevZaPrijateljstvo'];

    function ZahtevZaPrijateljstvoController(ZahtevZaPrijateljstvo) {

        var vm = this;

        vm.zahtevZaPrijateljstvos = [];

        loadAll();

        function loadAll() {
            ZahtevZaPrijateljstvo.query(function(result) {
                vm.zahtevZaPrijateljstvos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
