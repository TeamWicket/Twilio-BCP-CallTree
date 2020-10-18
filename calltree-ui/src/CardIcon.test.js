import CardIcon from './CardIcon';
import React from 'react';
import { shallow } from 'enzyme';
describe("Card Icon", () => {
    it("should match snapshot of CardIcon", () => {
        expect(shallow(<CardIcon />)).toMatchSnapshot();
    })
})
