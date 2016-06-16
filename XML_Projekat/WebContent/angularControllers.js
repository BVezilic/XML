var user = "predsednik_skupstine";
var odabraniAmandmani = [];

var app = angular
		.module('XML_App', [ 'ui.router' ])
		.controller(
				'mainControler',
				function($scope, $http, $window) {
					$scope.user = "predsednik_skupstine";
					$scope.dateFrom = "";
					$scope.dateTo = "";
					$scope.searchAktByMeta = function(dateFrom, dateTo) {
						console.log("tekstic " + dateFrom);
						$http({
						  method: 'GET',
						  url: 'http://localhost:8080/XML_Projekat/rest/services/search/akt/meta',
						  params: {"dateFrom":dateFrom, "dateTo":dateTo}	
						}).then(function successCallback(response) {
						    console.log("Usepsna pretraga po datumu");
						    console.log(response.data);
						  }, function errorCallback(response) {
						    console.log("Greska prilikom pretrage po metapodacima");
						  });
					}
					$scope.listaIzglasanihAmandmana = [];
					// HTTP FUNKCIJE
					var loadAmandman = function() {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/XML_Projekat/rest/services/amandmani'
						}
						$http(req).then(function successCallback(response) {
							$scope.listaIzglasanihAmandmana = response.data;

						});
					};

					var loadPredlozeniAmandman = function() {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/XML_Projekat/rest/services/predlozeniAmandmani'
						}
						$http(req).then(function successCallback(response) {
							$scope.listaAmandmana = response.data;
							
						});
					};

					var loadAkt = function() {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/XML_Projekat/rest/services/akti'
						}
						$http(req).then(function successCallback(response) {
							$scope.listaAkata = response.data;

						});
					};

					var loadOdabaniAkt = function() {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/XML_Projekat/rest/services/odabraniAkti'
						}
						$http(req).then(function successCallback(response) {
							$scope.listaOdabranihAkata = response.data;
						});
					};

					var addAkt = function(naslov) {
						var req = {
							method : 'PUT',
							url : 'http://localhost:8080/XML_Projekat/rest/services/odabraniAkti/add/'
									+ naslov
						}
						$http(req).then(function successCallback(response) {
							$scope.listaOdabranihAkata = response.data;
						});
					};

					var removeAkt = function(naslov) {
						var req = {
							method : 'DELETE',
							url : 'http://localhost:8080/XML_Projekat/rest/services/odabraniAkti/remove/'
									+ naslov
						}
						$http(req).then(function successCallback(response) {
							$scope.listaOdabranihAkata = response.data;
						});
					};
					
					
					var removeAmandman = function(uri){
						var req = {
								method : 'POST',
								url : 'http://localhost:8080/XML_Projekat/rest/services/remove',
								data: uri
							}
							$http(req).then(function successCallback(response) {
								loadPredlozeniAmandman();
								$scope.selectedAmandman = {};
							});
					};

					// INIT FUNKCIJE
					loadAkt();
					//loadOdabaniAkt();
					loadAmandman();
					loadPredlozeniAmandman();
					$scope.pullAmandmani = function(){
						loadAmandman();
					};
					
					$scope.removeAmandman = function(uri){
						removeAmandman(uri);
					};
					
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

					
					$scope.viewAkt = "";
					$scope.prikazSadrzajaAkta = function(id) {
						
						/*var req = {
								method : 'GET',
								url : 'http://localhost:8080/XML_Projekat/rest/services/akt/html/'+id.documentURI
								

							};
							$http(req)
									.then(
											function successCallback(response) {
												$scope.viewAkt = response.data;
											});*/
						$scope.viewAkt = 'http://localhost:8080/XML_Projekat/rest/services/akt/html/'+id.documentURI;
						 //$window.open('http://localhost:8080/XML_Projekat/rest/services/akt/html/'+id.documentURI, '_blank');
							
							
					};
					
					$scope.viewAmandman = "";
					
					$scope.prikazSadrzajaAmandmana = function(id){
						
						$scope.viewAmandman = 'http://localhost:8080/XML_Projekat/rest/services/amandman/html?uri='+id.documentURI;
						
					}
					
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
						$http(req)
								.then(
										function successCallback(response) {
											var dobijeno = response.data;
											loadAmandman();
											loadOdabaniAkt();
											loadPredlozeniAmandman();
											if (dobijeno != null) {
												user = dobijeno.uloga;
												if (user == 'gradjanin') {
													$window.location.href = 'http://localhost:8080/XML_Projekat/#/gr';
												}
												if (user == 'predsednik_skupstine') {
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
					 * 
					 */
					$stateProvider.state(
							'prVlad',
							{// naziv stanja!
								url : '/vl',
								templateProvider : function(session,
										$stateParams, $templateFactory) {
									session.loadRole();
									if (session.role == 'predsednik_skupstine') {
										return $templateFactory.fromUrl(
												'predsednikVlade.html',
												$stateParams);
									} else {
										return $templateFactory.fromUrl(
												'nisteUlogovani.html',
												$stateParams);
									}
								}
							}).state(
							'prVlad.amandmani',
							{
								url : '/amandmani',
								templateProvider : function(session,
										$stateParams, $templateFactory) {
									session.loadRole();
									if (session.role == 'predsednik_skupstine') {
										return $templateFactory.fromUrl(
												'kreiranjeAmandmana.html',
												$stateParams);
									} else {
										return $templateFactory.fromUrl(
												'nisteUlogovani.html',
												$stateParams);
									}
								},
								controller : 'amandmaniController'
							}).state(
							'prVlad.akt',
							{
								url : '/akt',
								templateProvider : function(session,
										$stateParams, $templateFactory) {
									session.loadRole();
									if (session.role == 'predsednik_skupstine') {
										return $templateFactory.fromUrl(
												'odabirAkata.html',
												$stateParams);
									} else {
										return $templateFactory.fromUrl(
												'nisteUlogovani.html',
												$stateParams);
									}
								}
							}).state(
							'prVlad.glasanje',
							{
								url : '/glasanje',
								templateProvider : function(session,
										$stateParams, $templateFactory) {
									session.loadRole();
									if (session.role == 'predsednik_skupstine') {

										return $templateFactory.fromUrl(
												'glasanje2.html', $stateParams);
									} else {
										return $templateFactory.fromUrl(
												'nisteUlogovani.html',
												$stateParams);
									}
								},
								controller : 'glasanjeController'
							});

					$stateProvider.state('gradjanin', {// naziv stanja!
						url : '/gr',
						templateProvider : function(session, $stateParams,
								$templateFactory) {
							session.loadRole();
							if (session.role == 'gradjanin') {
								return $templateFactory.fromUrl(
										'gradjanin.html', $stateParams);
							} else {
								return $templateFactory.fromUrl(
										'nisteUlogovani.html', $stateParams);
							}
						}
					});
					$stateProvider.state('gradjanin.akti', {
						url : '/akt',
						templateProvider : function(session, $stateParams,
								$templateFactory) {

							session.loadRole();
							console.log("uloga" + session.role);
							if (session.role == 'gradjanin') {
								return $templateFactory.fromUrl(
										'pregledAkata2.html', $stateParams);
							} else {
								return $templateFactory.fromUrl(
										'nisteUlogovani.html', $stateParams);
							}
						}
					});
					$stateProvider.state('gradjanin.amandmani', {
						url : '/amandman',
						templateProvider : function(session, $stateParams,
								$templateFactory) {
							session.loadRole();
							if (session.role == 'gradjanin') {
								return $templateFactory.fromUrl(
										'pregledAmandmana.html', $stateParams);
							} else {
								return $templateFactory.fromUrl(
										'nisteUlogovani.html', $stateParams);
							}
						}
					});

				})
		.service('session', function($timeout, $q) {
			this.role = "";

			this.loadRole = function() {
				console.log("radi loadRole " + user);
				this.role = user;
				return this.role;
			};
		})
		.controller(
				'amandmaniController',
				function($scope, $http) {
					
					var loadPredlozeniAmandman = function() {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/XML_Projekat/rest/services/predlozeniAmandmani'
						}
						$http(req).then(function successCallback(response) {
							$scope.listaAmandmana = response.data;
							
						});
					};
					
					var addAmandman = function(
							content) {
				
						var req = {
							method : 'POST',
							url : 'http://localhost:8080/XML_Projekat/rest/services/amandman/add',
							data: $scope.sadrzajAmandmana
						}
						$http(req).then(function successCallback(response) {
							loadPredlozeniAmandman();
						});
					};

					$scope.selectAkt = function(akt) {
						$scope.$parent.selectedAkt = akt;

					};

					$scope.createAmandman = function(content) {
						addAmandman(content);

					};

					$scope.selectAmandman = function(amandman) {
						$scope.$parent.selectedAmandman = amandman;
					};
					$scope.test = "";

				})
		.controller(
				'glasanjeController',
				function($scope, $http) {

					var loadAmandman = function() {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/XML_Projekat/rest/services/amandmani'
						}
						$http(req).then(
								function successCallback(response) {
									$scope.$parent.listaIzglasanihAmandmana = response.data;
								});
						
						
					};
					
					var loadPredlozeniAmandman = function() {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/XML_Projekat/rest/services/predlozeniAmandmani'
						}
						$http(req).then(function successCallback(response) {
							$scope.$parent.listaAmandmana = response.data;
							
						});
					};

					var removeAmandman = function(naslovAmandmana) {
						var req = {
							method : 'DELETE',
							url : 'http://localhost:8080/XML_Projekat/rest/services/amandman/add/'
									+ naslovAmandmana
						}
						$http(req).then(function successCallback(response) {
							$scope.$parent.listaAmandmana = response.data;
						});
					};

					$scope.selectAmandman = function(amandman) {
						$scope.$parent.selectedAmandman = amandman;
					};

					$scope.izvediGlasanje = function(amandman, za, protiv,
							uzdrzani) {

						var req = {
							method : 'POST',
							url : 'http://localhost:8080/XML_Projekat/rest/services/vote/'
									+ za
									+ '/'
									+ protiv
									+ '/'
									+ uzdrzani,
							data: amandman
									
									
						};
						$http(req).then(function successCallback(response) {
							loadPredlozeniAmandman();
						});
					}
				});
