'use strict';

angular.module('rdvApp.version', [
  'rdvApp.version.interpolate-filter',
  'rdvApp.version.version-directive'
])

.value('version', '0.2');
