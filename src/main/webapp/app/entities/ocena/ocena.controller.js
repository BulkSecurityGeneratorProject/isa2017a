(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('OcenaController', OcenaController);

    OcenaController.$inject = ['Ocena'];

    function OcenaController(Ocena) {

        var vm = this;

        vm.ocenas = [];

        loadAll();

        function loadAll() {
            Ocena.query(function(result) {
                vm.ocenas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
