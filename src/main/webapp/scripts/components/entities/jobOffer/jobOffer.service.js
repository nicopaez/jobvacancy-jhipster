'use strict';

angular.module('jobvacancyApp')
    .factory('JobOffer', function ($resource) {
        return $resource('api/jobOffers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
