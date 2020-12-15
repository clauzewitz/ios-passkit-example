import * as actions from './templateActions';
import { handleActions } from 'redux-actions';
import { TemplatePayload } from './templatePayloads';
import iTemplateState from './templateState';

const initialState = {
    templateList: Array<TemplatePayload>()
};

export default handleActions<iTemplateState, Array<TemplatePayload>>({
    [actions.initTemplateList.toString()]: (state): iTemplateState => {
        return {...state, ...{templateList: initialState.templateList}};
    },
    [actions.setTemplateList.toString()]: (state, action): iTemplateState => {
        return {...state, ...{templateList: action.payload}};
    }
}, initialState);