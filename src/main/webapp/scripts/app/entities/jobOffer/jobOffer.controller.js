'use strict';

angular.module('jobvacancyApp')
    .controller('JobOfferController', function ($scope, JobOffer) {
        $scope.jobOffers = [];
        $scope.loadAll = function() {
            JobOffer.query(function(result) {
               $scope.jobOffers = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            JobOffer.save($scope.jobOffer,
                function () {
                    $scope.loadAll();
                    $('#saveJobOfferModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            JobOffer.get({id: id}, function(result) {
                $scope.jobOffer = result;
                $('#saveJobOfferModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            JobOffer.get({id: id}, function(result) {
                $scope.jobOffer = result;
                $('#deleteJobOfferConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JobOffer.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJobOfferConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.jobOffer = {title: null, description: null, location: null, id: null};
        };
    });
