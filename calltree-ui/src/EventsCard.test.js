import EventsCard from './EventsCard';
import React from 'react';
import { shallow } from 'enzyme';

describe("Event Card", () => {
    it("should match snapshot of Events Card Component", () => {
        expect(shallow(<EventsCard />)).toMatchSnapshot();
    })
})
