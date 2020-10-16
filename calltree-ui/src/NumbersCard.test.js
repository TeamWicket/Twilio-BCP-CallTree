import NumbersCard from './NumbersCard';
import React from 'react';
import { shallow } from 'enzyme';

describe("Numbers Card", () => {
    it("should match snapshot of Numbers Card Component", () => {
        expect(shallow(<NumbersCard />)).toMatchSnapshot();
    })
});
