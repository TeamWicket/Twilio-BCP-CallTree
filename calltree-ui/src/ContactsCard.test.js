import ContactsCard from './ContactsCard';
import React from 'react';
import { shallow } from 'enzyme';

describe("Contracts Card", () => {
    it("should match snapshot of Contracts Card Component", () => {
        expect(shallow(<ContactsCard />)).toMatchSnapshot();
    })
})
