(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PrijateljiController', PrijateljiController);

    PrijateljiController.$inject = ['Prijatelji'];

    function PrijateljiController(Prijatelji) {

        var vm = this;

        vm.prijateljis = [];

        loadAll();

        function loadAll() {
            Prijatelji.query(function(result) {
                vm.prijateljis = result;
                vm.searchQuery = null;
            });
        }
    }
})();
