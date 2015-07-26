'use strict';

angular.module('jobvacancyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jobOffer', {
                parent: 'entity',
                url: '/jobOffer',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jobvacancyApp.jobOffer.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobOffer/jobOffers.html',
                        controller: 'JobOfferController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobOffer');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jobOfferDetail', {
                parent: 'entity',
                url: '/jobOffer/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jobvacancyApp.jobOffer.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobOffer/jobOffer-detail.html',
                        controller: 'JobOfferDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobOffer');
                        return $translate.refresh();
                    }]
                }
            });
    });
