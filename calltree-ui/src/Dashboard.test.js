import Dashboard from './Dashboard';
import React from 'react';
import { shallow } from 'enzyme';

describe("Dashboard", () => {
    it("should match snapshot of Dashboard Component", () => {
        expect(shallow(<Dashboard />)).toMatchSnapshot();
    })
})
