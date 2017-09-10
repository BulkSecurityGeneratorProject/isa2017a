'use strict';

describe('Controller Tests', function() {

    describe('Rezervacija Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRezervacija, MockStol, MockPorudzbina, MockPoziv, MockRestoran, MockGost;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRezervacija = jasmine.createSpy('MockRezervacija');
            MockStol = jasmine.createSpy('MockStol');
            MockPorudzbina = jasmine.createSpy('MockPorudzbina');
            MockPoziv = jasmine.createSpy('MockPoziv');
            MockRestoran = jasmine.createSpy('MockRestoran');
            MockGost = jasmine.createSpy('MockGost');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Rezervacija': MockRezervacija,
                'Stol': MockStol,
                'Porudzbina': MockPorudzbina,
                'Poziv': MockPoziv,
                'Restoran': MockRestoran,
                'Gost': MockGost
            };
            createController = function() {
                $injector.get('$controller')("RezervacijaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isaApp:rezervacijaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
