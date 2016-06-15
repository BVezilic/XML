var user = "";
var odabraniAmandmani = [];

var app = angular.module('XML_App', [ 'ui.router' ]).controller(
		'mainControler',
		function($scope, $http) {
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
			
			$scope.login = function(username,password){
				var req = {
						 method: 'GET',
						 url: 'localhost:8080/XML_Projekat/rest/services/login/'+username+'/'+password,
						 
						}

						$http(req).then(function(response){
							console.log(response.data);
							
							
						});
			};

			$scope.persist = function(xml) {
				console.log(xml);
				$http({
					  method: 'POST',
					  url: 'http://localhost:8080/XML_Projekat/rest/services/persist?tip=akt',
					  data: xml
					}).then(function successCallback(response) {
					    console.log("uspeo");
					  }, function errorCallback(response) {
						  console.log("fail");
					  });
			}
			$scope.persistAmandman = function(amandman) {
				console.log(amandman);
				$http({
					  method: 'POST',
					  url: 'http://localhost:8080/XML_Projekat/rest/services/persist?tip=amandman',
					  data: amandman
					}).then(function successCallback(response) {
					    console.log("uspeo");
					  }, function errorCallback(response) {
						  console.log("fail");
					  });
			}
			$scope.search = function(criteria) {
				console.log(criteria);
				$http({
					  method: 'POST',
					  url: 'http://localhost:8080/XML_Projekat/rest/services/singleFieldSearch',
					  data: criteria
					}).then(function successCallback(response) {
					    console.log("uspeo");
					    console.log(response.data);
					    $scope.page = response.data;
					    $scope.rezultat = response.data;
					  }, function errorCallback(response) {
						  console.log("fail");
					  });
			}
			$scope.searchMeta = function(dateFrom, dateTo) {
				console.log(console.log(new Date(dateFrom.getTime() + dateFrom.getTimezoneOffset()*60000)));
				$http({
					  method: 'GET',
					  url: 'http://localhost:8080/XML_Projekat/rest/services/metaSearch',
					  params: {"dateFrom":dateFrom, "dateTo":dateTo}
					}).then(function successCallback(response) {
					    console.log("uspeo");
					    console.log(response.data);
					    $scope.rezultat = response.data;
					  }, function errorCallback(response) {
						  console.log("fail");
					  });
			}
			$scope.passAmandman = function() {
				$http({
					  method: 'GET',
					  url: 'http://localhost:8080/XML_Projekat/rest/services/amandman?amd=zakon_o_izvršenju_i_obezbeđenju/Prvi/1916572645506940001.xml',
					}).then(function successCallback(response) {
					    console.log("uspeo");
					  }, function errorCallback(response) {
						  console.log("fail");
					  });
			}
			$scope.passAkt = function() {
				$http({
					  method: 'GET',
					  url: 'http://localhost:8080/XML_Projekat/rest/services/passAkt?akt=zakon_o_izvršenju_i_obezbeđenju',
					}).then(function successCallback(response) {
					    console.log("uspeo");
					  }, function errorCallback(response) {
						  console.log("fail");
					  });
			}
			$scope.test = "";
			$scope.submit = function() {
				console.log($scope.file);
			}
		}).config(function($stateProvider, $urlRouterProvider) {
	
	
	if (user == ""){
		$stateProvider.state('logIn', {// naziv stanja!
			url : '/logIn',
			templateUrl : 'login.html',
		});
		$urlRouterProvider.otherwise('/logIn');
	}
	
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
			controller : 'amandmaniController'
		}).state('prVlad.glasanje', {
			url : '/glasanje',
			templateUrl : 'glasanje.html',
			controller : 'glasanjeController'
		});
		$urlRouterProvider.otherwise('/vl/akt');
	}
	if (user == "gradjanin") {
		$stateProvider.state('gradjanin', {// naziv stanja!
			url : '/gr',
			templateUrl : 'gradjanin.html'		
		});
		$urlRouterProvider.otherwise('/gr');
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
