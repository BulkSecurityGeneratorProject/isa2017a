(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('ZaposleniDialogController', ZaposleniDialogController);

    ZaposleniDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Zaposleni', 'User', 'KonfiguracijaStolova', 'RasporedSmenaZaSankere', 'RasporedSmenaZaKonobare', 'RasporedSmenaZaKuvare', 'Racun', 'Restoran'];

    function ZaposleniDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Zaposleni, User, KonfiguracijaStolova, RasporedSmenaZaSankere, RasporedSmenaZaKonobare, RasporedSmenaZaKuvare, Racun, Restoran) {
        var vm = this;

        vm.zaposleni = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.konfiguracijastolova = KonfiguracijaStolova.query({filter: 'zaposleni-is-null'});
        $q.all([vm.zaposleni.$promise, vm.konfiguracijastolova.$promise]).then(function() {
            if (!vm.zaposleni.konfiguracijaStolova || !vm.zaposleni.konfiguracijaStolova.id) {
                return $q.reject();
            }
            return KonfiguracijaStolova.get({id : vm.zaposleni.konfiguracijaStolova.id}).$promise;
        }).then(function(konfiguracijaStolova) {
            vm.konfiguracijastolova.push(konfiguracijaStolova);
        });
        vm.rasporedsmenazasankeres = RasporedSmenaZaSankere.query();
        vm.rasporedsmenazakonobares = RasporedSmenaZaKonobare.query();
        vm.rasporedsmenazakuvares = RasporedSmenaZaKuvare.query();
        vm.racuns = Racun.query();
        vm.restorans = Restoran.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.zaposleni.id !== null) {
                Zaposleni.update(vm.zaposleni, onSaveSuccess, onSaveError);
            } else {
                Zaposleni.save(vm.zaposleni, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isaApp:zaposleniUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datumRodjenja = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
