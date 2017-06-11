import angular from 'angular';
import Search from './search';

const pages = angular.module('PubMedBrowser.pages', [Search]).name;

export default pages;
