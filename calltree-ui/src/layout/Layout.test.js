import Layout from './Layout';
import React from 'react';
import { shallow } from 'enzyme';
import { Admin } from 'react-admin';

describe("Layout", () => {
    it("should match snapshot of Layout Component", () => {
        expect(shallow(<Admin><Layout /></Admin>)).toMatchSnapshot();
    })
    it("should return something", () => {
        expect(shallow(<Admin><Layout /></Admin>).length).toEqual(1);
    })
})
