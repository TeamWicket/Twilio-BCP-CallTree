import Menu from './Menu';
import React from 'react';
import { shallow } from 'enzyme';
import { Admin } from 'react-admin';

describe("Menu", () => {
    it("should match snapshot of ConfigurationMenu", () => {
        expect(shallow(<Admin><Menu /></Admin>)).toMatchSnapshot();
    })
})
