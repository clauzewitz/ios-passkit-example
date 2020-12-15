import { createAction } from 'redux-actions';
import { TemplatePayload } from './templatePayloads';

export const initTemplateList = createAction('template/INIT');
export const setTemplateList = createAction<Array<TemplatePayload>>('template/SET');