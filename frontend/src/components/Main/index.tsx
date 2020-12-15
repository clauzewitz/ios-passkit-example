import React, {
    memo,
    Fragment,
    useEffect,
    useState,
    useCallback
} from 'react';
import './style.scss';
import { useDispatch, useSelector } from 'react-redux';
import { isEqual } from 'lodash';
import {
    makeStyles,
    Container,
    Grid
} from '@material-ui/core';
import { Passbook } from 'components';
import { CommonUtil, HttpClient } from 'utils';
import { rootState } from 'reducers';
import { initTemplateList, setTemplateList } from 'reducers/template/templateActions';

const useStyles = makeStyles({
    root: {
        flexGrow: 1
    }
});

type MainProps = {};

const Main = memo((pros: MainProps) => {
    const classes = useStyles();
    const dispatch = useDispatch();
    const passbookTemplateList = useSelector((state: rootState) => state.TemplateReducer.templateList);

    const componentMountAndUnmount = () => {
        HttpClient.get('/templates').then(response => {
            dispatch(setTemplateList(response.data));
        }).catch(error => console.error(error));

        return () => {
            dispatch(initTemplateList());
        };
    };

    useEffect(componentMountAndUnmount, []);

    return (
        <Fragment>
            <Container className={classes.root} maxWidth={'xl'}>
                <Grid container>
                    {passbookTemplateList && passbookTemplateList.map(passbookTemplate => <Passbook key={CommonUtil.generateKey(passbookTemplate.id)} info={passbookTemplate}/>)}
                </Grid>
            </Container>
        </Fragment>
    );
}, (prevProps, nextProps) => {
    return isEqual(prevProps, nextProps);
});

export default Main;
