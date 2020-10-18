import themeReducer from './themeReducer';
import { CHANGE_THEME } from './configuration/actions';

describe("theme reducer", () => {
    it("should return initial state", () => {
        expect(themeReducer(undefined, {})).toEqual("light")
    })

    it("should return payload", () => {
        expect(themeReducer(undefined, { type: CHANGE_THEME, payload: "dark" })).toEqual("dark")
    })

    it("should return something", () => {
        expect(themeReducer(undefined, {})).not.toEqual("");
    })
})