'use strict';

angular.module('jobvacancyApp')
    .controller('JobOfferDetailController', function ($scope, $stateParams, JobOffer) {
        $scope.jobOffer = {};
        $scope.load = function (id) {
            JobOffer.get({id: id}, function(result) {
              $scope.jobOffer = result;
            });
        };
        $scope.load($stateParams.id);
    });
