(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('JelovnikController', JelovnikController);

    JelovnikController.$inject = ['Jelovnik'];

    function JelovnikController(Jelovnik) {

        var vm = this;

        vm.jelovniks = [];

        loadAll();

        function loadAll() {
            Jelovnik.query(function(result) {
                vm.jelovniks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
