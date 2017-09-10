(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('MenadzerRestoranaController', MenadzerRestoranaController);

    MenadzerRestoranaController.$inject = ['MenadzerRestorana'];

    function MenadzerRestoranaController(MenadzerRestorana) {

        var vm = this;

        vm.menadzerRestoranas = [];

        loadAll();

        function loadAll() {
            MenadzerRestorana.query(function(result) {
                vm.menadzerRestoranas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
