var app = angular
		.module('XML_App', [ 'ngRoute' ])
		.controller(
				'mainControler',
				function($scope) {
					$scope.sadrzajAkta = "";

					$scope.listaAkata = [ "akt o oduzimanju radne dozvole",
							"akt2", "akt3" ];
					$scope.listaOdabranihAkata = [];

					$scope.addAkt = function(name) {
						var index = $scope.listaOdabranihAkata.indexOf(name);
						if (index == -1) {
							$scope.listaOdabranihAkata.push(name);
						}
					};

					$scope.removeAkt = function(name) {
						var index = $scope.listaOdabranihAkata.indexOf(name);
						$scope.listaOdabranihAkata.splice(index, 1);
					};

					$scope.prikazSadrzajaAkta = function(id) {
						$scope.sadrzajAkta = id;
					};
				})
		.config(function($routeProvider) {
			$routeProvider.when('/v1', {
				templateUrl : 'odabirAkata.html'
			}).when('/v2', {
				templateUrl : 'kreiranjeAmandmana.html',
				controller : 'amandmaniController'
			});
		})
		.controller(
				'amandmaniController',
				function($scope) {
					$scope.selectedAkt = "";
					$scope.selectAkt = function(akt) {
						$scope.selectedAkt = akt;

					};
					
					$scope.test="";
					
					
					

				});
