import SubMenu from './submenu';
import React from 'react';
import { shallow } from 'enzyme';

describe("SubMenu", () => {
    it("should match snapshot of SubMenu", () => {
        expect(shallow(<SubMenu />)).toMatchSnapshot();
    })
})
