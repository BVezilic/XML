var user = "predsednikVlade";
var odabraniAmandmani = [];

var app = angular.module('XML_App', [ 'ui.router' ]).controller(
		'mainControler',
		function($scope) {
			$scope.user = "predsednikVlade";

			$scope.sadrzajAkta = "";
			$scope.viewAkt = "";
			// 'gen/html/zakon_o_izvrsenju_i_obezbedjenju.html'
			$scope.listaAkata = [ "zakon_o_izvrsenju_i_obezbedjenju.html",
					"akt", "akt3" ];
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
				$scope.viewAkt = 'gen/html/' + id;
			};

			$scope.proba = "";

			$scope.selectedAkt = "";
			$scope.selectedAmandman = "";
			$scope.listaAmandmana = [ 'amandman1' ];
			$scope.selectAkt = function(akt) {
				$scope.selectedAkt = akt;

			};

			
			$scope.test = "";
		}).config(function($stateProvider, $urlRouterProvider) {

	if (user == "predsednikVlade") {
		$stateProvider.state('prVlad', {// naziv stanja!
			url : '/vl',
			templateUrl : 'predsednikVlade.html',
		}).state('prVlad.akti', {
			url : '/akt',
			templateUrl : 'odabirAkata.html'

		}).state('prVlad.amandmani', {
			url : '/amandmani',
			templateUrl : 'kreiranjeAmandmana.html',
			controller: 'amandmaniController'
		}).state('prVlad.glasanje', {
			url : '/glasanje',
			templateUrl : 'glasanje.html',
			controller: 'glasanjeController'
		});
	}
	if (user == "gradjanin") {
		$stateProvider.state('gradjanin', {// naziv stanja!
			url : '/gr',
			templateUrl : 'gradjanin.html'
		});
	}

}).controller('amandmaniController', function($scope) {
	
	$scope.selectAkt = function(akt) {
		$scope.$parent.selectedAkt = akt;

	};

	$scope.createAmandman = function(name, content) {
		$scope.$parent.listaAmandmana.push(name);
		
	};

	$scope.selectAmandman = function(amandman) {
		$scope.$parent.selectedAmandman = amandman;
	};
	$scope.test = "";

}).controller('glasanjeController', function($scope) {
	$scope.selectAmandman = function(amandman) {
		$scope.$parent.selectedAmandman = amandman;
	};
});
