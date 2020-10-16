import CustomAppBar, { CustomUserMenu, ConfigurationMenu } from './AppBar';
import React from 'react';
import { shallow } from 'enzyme';

describe("App bar", () => {
    it("should match snapshot of Custom AppBar", () => {
        expect(shallow(<CustomAppBar />)).toMatchSnapshot();
    })
    it("should match snapshot of Custom User Menu", () => {
        expect(shallow(<CustomUserMenu />)).toMatchSnapshot();
    })
    it("should match snapshot of ConfigurationMenu", () => {
        expect(shallow(<ConfigurationMenu />)).toMatchSnapshot();
    })
})
