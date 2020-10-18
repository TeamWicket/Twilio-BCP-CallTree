import React from 'react';
import { shallow } from 'enzyme';
import App from './App';

describe("App", () => {
  it("should match snapshot of App Component", () => {
    expect(shallow(<App />)).toMatchSnapshot();
  })
})
