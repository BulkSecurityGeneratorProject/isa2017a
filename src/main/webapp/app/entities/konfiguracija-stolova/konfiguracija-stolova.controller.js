(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('KonfiguracijaStolovaController', KonfiguracijaStolovaController);

    KonfiguracijaStolovaController.$inject = ['KonfiguracijaStolova'];

    function KonfiguracijaStolovaController(KonfiguracijaStolova) {

        var vm = this;

        vm.konfiguracijaStolova = [];

        loadAll();

        function loadAll() {
            KonfiguracijaStolova.query(function(result) {
                vm.konfiguracijaStolova = result;
                vm.searchQuery = null;
            });
        }
    }
})();
