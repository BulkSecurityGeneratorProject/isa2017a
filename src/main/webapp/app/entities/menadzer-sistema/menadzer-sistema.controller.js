(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('MenadzerSistemaController', MenadzerSistemaController);

    MenadzerSistemaController.$inject = ['MenadzerSistema'];

    function MenadzerSistemaController(MenadzerSistema) {

        var vm = this;

        vm.menadzerSistemas = [];

        loadAll();

        function loadAll() {
            MenadzerSistema.query(function(result) {
                vm.menadzerSistemas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
