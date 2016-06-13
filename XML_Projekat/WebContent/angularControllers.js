var user = "";
var odabraniAmandmani = [];

var app = angular
		.module('XML_App', [ 'ui.router' ])
		.controller(
				'mainControler',
				function($scope, $http, $window) {
					$scope.user = "predsednikVlade";

					$scope.sadrzajAkta = "";
					$scope.viewAkt = "";
					// 'gen/html/zakon_o_izvrsenju_i_obezbedjenju.html'
					$scope.listaAkata = [
							"zakon_o_izvrsenju_i_obezbedjenju.html", "akt",
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
						$scope.viewAkt = 'gen/html/' + id;
					};

					$scope.proba = "";

					$scope.selectedAkt = "";
					$scope.selectedAmandman = "";
					$scope.listaAmandmana = [ 'amandman1' ];
					$scope.selectAkt = function(akt) {
						$scope.selectedAkt = akt;

					};

					$scope.login = function(username, password) {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/XML_Projekat/rest/services/login/'
									+ username + '/' + password

						};
						$http(req).then(function successCallback(response) {
							var dobijeno = response.data;
							if (dobijeno != null) {
								user = dobijeno.uloga;
								if(user == 'gradjanin'){
									$window.location.href = 'http://localhost:8080/XML_Projekat/#/gr';
								}
								if(user == 'predsednikVlade'){
									$window.location.href = 'http://localhost:8080/XML_Projekat/#/vl';
								}
							}

							console.log(user);
						});
					};

					$scope.test = "";
				})
		.config(
				function($stateProvider, $urlRouterProvider) {

					$stateProvider.state('logIn', {// naziv stanja!
						url : '/logIn',
						templateUrl : 'login.html',
					});

					$urlRouterProvider.otherwise('/logIn');

					/*
					 * $stateProvider.state('prVlad', {// naziv stanja! url :
					 * '/vl', templateUrl : 'predsednikVlade.html',
					 * }).state('prVlad.akti', { url : '/akt', templateUrl :
					 * 'odabirAkata.html'
					 * 
					 * }).state('prVlad.amandmani', { url : '/amandmani',
					 * templateUrl : 'kreiranjeAmandmana.html', controller :
					 * 'amandmaniController' }).state('prVlad.glasanje', { url :
					 * '/glasanje', templateUrl : 'glasanje.html', controller :
					 * 'glasanjeController' });
					
					 */
					$stateProvider.state('prVlad', {// naziv stanja! 
						url : '/vl',
						templateProvider : function(session, $stateParams, $templateFactory){
							session.loadRole();      
							if(session.role == 'predsednikVlade'){
					                return $templateFactory.fromUrl('predsednikVlade.html', $stateParams);
					              } else {
					                return $templateFactory.fromUrl('nisteUlogovani.html', $stateParams);
					              }
					        }
					}).state('prVlad.amandmani', { 
						url : '/amandmani',
						templateProvider : function(session, $stateParams, $templateFactory){
							session.loadRole();  
							if(session.role == 'predsednikVlade'){
				                return $templateFactory.fromUrl('kreiranjeAmandmana.html', $stateParams);
				              } else {
				                return $templateFactory.fromUrl('nisteUlogovani.html', $stateParams);
				              }
						},
						controller : 'amandmaniController' 
					}).state('prVlad.akt', { 
						url : '/akt',
						templateProvider : function(session, $stateParams, $templateFactory){
							session.loadRole();  
							if(session.role == 'predsednikVlade'){
				                return $templateFactory.fromUrl('odabirAkata.html', $stateParams);
				              } else {
				                return $templateFactory.fromUrl('nisteUlogovani.html', $stateParams);
				              }
						}
					}).state('prVlad.glasanje', { 
						url : '/glasanje',
						templateProvider : function(session, $stateParams, $templateFactory){
							session.loadRole();  
							if(session.role == 'predsednikVlade'){
								
				                return $templateFactory.fromUrl('glasanje.html', $stateParams);
				              } else {
				                return $templateFactory.fromUrl('nisteUlogovani.html', $stateParams);
				              }
						},
						controller: 'glasanjeController'
					});
					
					$stateProvider.state('gradjanin',{// naziv stanja!
						url : '/gr',
						templateProvider : function(session, $stateParams, $templateFactory){
					         	session.loadRole();	
					              if(session.role == 'gradjanin'){
					                return $templateFactory.fromUrl('gradjanin.html', $stateParams);
					              } else {
					                return $templateFactory.fromUrl('nisteUlogovani.html', $stateParams);
					              }
					        }
					      });
					
}).service('session', function($timeout, $q) {
			this.role = "";

			this.loadRole = function() {
				console.log("radi loadRole "+user);
				this.role = user;
				return this.role;
			};
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
