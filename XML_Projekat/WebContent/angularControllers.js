var app = angular.module('XML_App', []).controller(
		'mainControler',
		function($scope) {
			$scope.sadrzajAkta = "";

			$scope.listaAkata = [ "akt o oduzimanju radne dozvole", "akt2",
					"akt3" ];
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
		});
