import React from 'react';
import { Route } from 'react-router-dom';
import Configuration from './configuration/configuration';

export default [
    <Route exact path="/configuration" render={() => <Configuration />} />,
];
