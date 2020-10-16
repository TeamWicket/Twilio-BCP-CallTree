import { NumberList, NumberCreate, NumberEdit } from './Numbers';
import React from 'react';
import { shallow } from 'enzyme';

describe("Number Component", () => {
    it("should match snapshot of Number List Component", () => {
        expect(shallow(<NumberList />)).toMatchSnapshot();
    })
    it("should match snapshot of Number Create Component", () => {
        expect(shallow(<NumberCreate />)).toMatchSnapshot();
    })
    it("should match snapshot of NumberEdit Component", () => {
        expect(shallow(<NumberEdit />)).toMatchSnapshot();
    })
})
