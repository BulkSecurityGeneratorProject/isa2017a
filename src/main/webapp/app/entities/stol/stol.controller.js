(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('StolController', StolController);

    StolController.$inject = ['Stol'];

    function StolController(Stol) {

        var vm = this;

        vm.stols = [];

        loadAll();

        function loadAll() {
            Stol.query(function(result) {
                vm.stols = result;
                vm.searchQuery = null;
            });
        }
    }
})();
