import { EventList, EventCreate } from './Events';
import React from 'react';
import { shallow } from 'enzyme';


describe("Events", () => {
    it("should match snapshot of EventList Component", () => {
        expect(shallow(<EventList />)).toMatchSnapshot();
    })
    it("should match snapshot of EventCreate Component", () => {
        expect(shallow(<EventCreate />)).toMatchSnapshot();
    })
})
