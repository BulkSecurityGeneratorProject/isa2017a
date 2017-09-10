(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('ZaposleniController', ZaposleniController);

    ZaposleniController.$inject = ['Zaposleni'];

    function ZaposleniController(Zaposleni) {

        var vm = this;

        vm.zaposlenis = [];

        loadAll();

        function loadAll() {
            Zaposleni.query(function(result) {
                vm.zaposlenis = result;
                vm.searchQuery = null;
            });
        }
    }
})();
