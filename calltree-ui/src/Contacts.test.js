import { ContactList, ContactEdit, ContactCreate } from './Contacts';
import React from 'react';
import { shallow } from 'enzyme';
describe("Contacts", () => {
    it("should match snapshot of ContactList", () => {
        expect(shallow(<ContactList />)).toMatchSnapshot();
    })
    it("should match snapshot of ContactEdit", () => {
        expect(shallow(<ContactEdit />)).toMatchSnapshot();
    })
    it("should match snapshot of ContactCreate", () => {
        expect(shallow(<ContactCreate />)).toMatchSnapshot();
    })
})
