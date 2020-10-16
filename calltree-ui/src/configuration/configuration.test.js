import React from 'react';
import { mount, shallow } from 'enzyme';
import { Admin } from 'react-admin';
import Configuration from '../../../calltree-ui/src/configuration/configuration';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store'

describe("Configuration", () => {
    let middlewares;
    let mockStore;
    let initialState;
    let store;
    beforeAll(() => {
        middlewares = [];
        mockStore = configureStore(middlewares);
        initialState = { theme: "light" };
        store = mockStore(initialState);
    })

    it("should match snapshot of Configuration Component", () => {
        expect(shallow(<Admin><Configuration /></Admin>)).toMatchSnapshot();
    })

    it("should check the action being called on button click", () => {
        const wrapper = mount(
            <Provider store={store}>
                <Configuration />
            </Provider >);
        const buttons = wrapper.find("button");
        buttons.at(1).simulate("click");
        expect(store.getActions().pop()).toEqual({ type: 'CHANGE_THEME', payload: 'dark' })
        buttons.at(0).simulate("click");
        expect(store.getActions().pop()).toEqual({ type: 'CHANGE_THEME', payload: 'light' })
    })
})
