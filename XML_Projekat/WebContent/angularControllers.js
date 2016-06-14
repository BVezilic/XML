var user = "";
var odabraniAmandmani = [];

var app = angular
		.module('XML_App', [ 'ui.router' ])
		.controller(
				'mainControler',
				function($scope, $http, $window) {
					$scope.user = "predsednikVlade";

					//HTTP FUNKCIJE
					var loadAkt = function() {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/XML_Projekat/rest/services/akti'
						}
						$http(req).then(function successCallback(response) {
							$scope.listaAkata = response.data;
							
						});
					};
					
					var loadOdabaniAkt = function(){
						var req = {
								method : 'GET',
								url : 'http://localhost:8080/XML_Projekat/rest/services/odabraniAkti'
							}
							$http(req).then(function successCallback(response) {
								$scope.listaOdabranihAkata = response.data;								
							});
					};
					
					var addAkt = function(naslov){
						var req = {
								method : 'PUT',
								url : 'http://localhost:8080/XML_Projekat/rest/services/odabraniAkti/add/'+naslov
							}
							$http(req).then(function successCallback(response) {
								$scope.listaOdabranihAkata = response.data;								
							});
					};
					
					var removeAkt = function(naslov){
						var req = {
								method : 'DELETE',
								url : 'http://localhost:8080/XML_Projekat/rest/services/odabraniAkti/remove/'+naslov
							}
							$http(req).then(function successCallback(response) {
								$scope.listaOdabranihAkata = response.data;								
							});
					};
					
					
					//INIT FUNKCIJE
					loadAkt();
					loadOdabaniAkt();
					
					$scope.sadrzajAkta = "";
					$scope.viewAkt = "";
					// 'gen/html/zakon_o_izvrsenju_i_obezbedjenju.html'
					$scope.listaAkata = [];
					$scope.listaOdabranihAkata = [];

					$scope.addAkt = function(name) {
						addAkt(name);
					};

					$scope.removeAkt = function(name) {
						removeAkt(name);
					};

					$scope.prikazSadrzajaAkta = function(id) {
						$scope.viewAkt = 'gen/html/' + id;
					};

					$scope.proba = "";

					$scope.selectedAkt = "";
					$scope.selectedAmandman = "";
					$scope.listaAmandmana = [];
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
				                return $templateFactory.fromUrl('kreiranjeAmandmana2.html', $stateParams);
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
				                return $templateFactory.fromUrl('odabirAkata2.html', $stateParams);
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
		}).controller('amandmaniController', function($scope, $http) {

			var addAmandman = function(naslovAkta,naslovAmandmana,content){
				var req = {
						method : 'PUT',
						url : 'http://localhost:8080/XML_Projekat/rest/services/amandman/add/'+naslovAkta+'/'+naslovAmandmana+'/'+content
					}
					$http(req).then(function successCallback(response) {
						$scope.$parent.listaAmandmana = response.data;								
					});
			};
			
			
			$scope.selectAkt = function(akt) {
				$scope.$parent.selectedAkt = akt;

			};

			$scope.createAmandman = function(akt,name, content) {
				addAmandman(akt, name, content);

			};

			$scope.selectAmandman = function(amandman) {
				$scope.$parent.selectedAmandman = amandman;
			};
			$scope.test = "";

		}).controller('glasanjeController', function($scope,$http) {
			
			var removeAmandman = function(naslovAmandmana){
				var req = {
						method : 'DELETE',
						url : 'http://localhost:8080/XML_Projekat/rest/services/amandman/add/'+naslovAmandmana
					}
					$http(req).then(function successCallback(response) {
						$scope.$parent.listaAmandmana = response.data;								
					});
			};
			
			
			$scope.selectAmandman = function(amandman) {
				$scope.$parent.selectedAmandman = amandman;
			};
			
			$scope.izvediGlasanje = function(amandman,za,protiv,uzdrzani){
			
				var req = {
						method : 'GET',
						url : 'http://localhost:8080/XML_Projekat/rest/services/vote/'+za+'/'+protiv+'/'+uzdrzani+'/'+amandman
					};
					$http(req).then(function successCallback(response) {
						$scope.$parent.listaAmandmana = response.data;
						$scope.$parent.selectedAmandman = "";
					});
			}
		});
