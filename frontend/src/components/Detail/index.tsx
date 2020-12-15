import React, {
    memo,
    Fragment,
    useState,
    useCallback,
    forwardRef
} from 'react';
import './style.scss';
import { isNil, isEqual } from 'lodash';
import {
    makeStyles,
    Dialog,
    DialogContent,
    Slide,
    AppBar,
    Toolbar,
    IconButton,
    List,
    ListItem,
    TextField,
    FormControl,
    FormHelperText,
    FormLabel,
    FormGroup,
    FormControlLabel,
    Switch,
    InputLabel,
    Select,
    MenuItem
} from '@material-ui/core';
import { TransitionProps } from '@material-ui/core/transitions';
import withWidth, { isWidthUp } from '@material-ui/core/withWidth';
import {
    Close,
    SaveAlt
} from '@material-ui/icons';
import {
    BARCODE_TYPE,
    QR_TYPE
} from 'enums';
import {
    iPassbook
} from 'interfaces';
import { CommonUtil, HttpClient } from 'utils';

const useStyles = makeStyles({
    appBar: {
        position: 'relative'
    },
    grow: {
        flex: 1
    },
    iconButton: {
        color: 'lightgray'
    },
    select: {
        flex: 1
    }
});

type DetailProps = {
    opener: boolean,
    onClosed?: Function,
    width: any,
    passbookId: any
};

const Transition = forwardRef((
    props: TransitionProps & { children?: React.ReactElement },
    ref: React.Ref<unknown>): JSX.Element => <Slide direction="up" ref={ref} {...props} />);

const Detail = memo((props: DetailProps) => {
    const classes = useStyles();
    const [name, setName] = useState('');
    const [membershipNum, setMembershipNum] = useState('');
    const [isMembershipNumSpaces, setMembershipNumSpaces] = useState(true);
    const [barcodeType, setBarcodeType] = useState(BARCODE_TYPE.CODE_128);
    const [qrType, setQRType] = useState(QR_TYPE.NONE);

    const isFullScreen = useCallback((): boolean => {
        return !isWidthUp('sm', props.width);
    }, [props]);

    const fileDownload = useCallback((response: any): void => {

        if (isNil(response)) {
            return;
        }

        const blob = new Blob([response.data], { type: response.headers['content-type'] });
        const blobUrl = window.URL.createObjectURL(blob);
        const fileLink = document.createElement('a');
        fileLink.href = blobUrl;
        fileLink.download = CommonUtil.getUnixTimestamp().toString();
        fileLink.click();
        fileLink.remove();
        window.URL.revokeObjectURL(blobUrl);
    }, []);

    const generatePassbook = useCallback((): void => {
        const passbookReq: iPassbook = {
            id: props.passbookId,
            name: name,
            membershipNum: membershipNum,
            displayMembershipNum: isMembershipNumSpaces ? CommonUtil.toNumberWithSpace(membershipNum) : membershipNum.toString(),
            barcodeType: barcodeType,
            qrType: qrType
        };

        HttpClient.post('/passbook', passbookReq, {responseType: 'blob'}).then(response => {
            fileDownload(response);
        }).catch(error => console.error(error));
    }, [fileDownload, props, name, membershipNum, isMembershipNumSpaces, barcodeType, qrType]);

    return (
        <Fragment>
            <Dialog open={props.opener} fullScreen={isFullScreen()} TransitionComponent={Transition} scroll="paper">
                <AppBar className={classes.appBar}>
                    <Toolbar>
                        <IconButton edge="start" className={classes.iconButton} aria-label="close" onClick={() => { props?.onClosed?.(); }}>
                            <Close />
                        </IconButton>
                        <div className={classes.grow}></div>
                        <IconButton edge="start" className={classes.iconButton} aria-label="save" onClick={() => { generatePassbook(); }}>
                            <SaveAlt />
                        </IconButton>
                    </Toolbar>
                </AppBar>
                <DialogContent>
                    <List>
                        <ListItem>
                            <TextField
                                required
                                fullWidth
                                InputLabelProps={{
                                    shrink: true
                                }}
                                label="이름"
                                placeholder="이름을 입력하세요"
                                value={name}
                                onChange={(event: React.ChangeEvent<{ value: string }>) => { setName(event.target.value); }}
                                data-type="NAME"
                                variant="outlined" />
                        </ListItem>
                        <ListItem>
                            <TextField
                                required
                                fullWidth
                                InputLabelProps={{
                                    shrink: true
                                }}
                                label="멤버십 번호"
                                placeholder="멤버십 번호를 입력하세요"
                                type="number"
                                value={membershipNum}
                                onChange={(event: React.ChangeEvent<{ value: string }>) => { setMembershipNum(event.target.value); }}
                                data-type="MEMBERSHIP_NUM"
                                variant="outlined" />
                        </ListItem>
                        <ListItem>
                            <FormControl>
                                <FormLabel>멤버십 번호 표시방법</FormLabel>
                                <FormGroup>
                                    <FormControlLabel
                                        control={<Switch
                                                    color="primary"
                                                    checked={isMembershipNumSpaces}
                                                    onChange={(event: React.ChangeEvent<{ checked: boolean }>) => { setMembershipNumSpaces(event.target.checked); }}/>}
                                        label="네자리씩 띄어쓰기"
                                    />
                                </FormGroup>
                                <FormHelperText>멤버십 번호 표시방법을 선택합니다.</FormHelperText>
                            </FormControl>
                        </ListItem>
                        <ListItem>
                            <FormControl className={classes.select} variant="outlined">
                                <InputLabel id="demo-simple-select-outlined-label">바코드 유형</InputLabel>
                                <Select
                                    label="바코드 유형"
                                    value={barcodeType}
                                    onChange={(event: React.ChangeEvent<{ value: unknown }>) => { setBarcodeType(event.target.value as BARCODE_TYPE); }}>
                                    <MenuItem value={BARCODE_TYPE.CODE_128}>{BARCODE_TYPE.CODE_128}</MenuItem>
                                    <MenuItem value={BARCODE_TYPE.PDF_417}>{BARCODE_TYPE.PDF_417}</MenuItem>
                                    <MenuItem value={BARCODE_TYPE.CODA_BAR}>{BARCODE_TYPE.CODA_BAR}</MenuItem>
                                </Select>
                                <FormHelperText>기본값 설정을 권장합니다.</FormHelperText>
                            </FormControl>
                        </ListItem>
                        <ListItem>
                            <FormControl className={classes.select} variant="outlined">
                                <InputLabel id="demo-simple-select-outlined-label">하단 코드 유형</InputLabel>
                                <Select
                                    label="QR 코드 유형"
                                    value={qrType}
                                    onChange={(event: React.ChangeEvent<{ value: unknown }>) => { setQRType(event.target.value as QR_TYPE); }}>
                                    <MenuItem value={QR_TYPE.NONE}>선택안함</MenuItem>
                                    <MenuItem value={QR_TYPE.QR}>{QR_TYPE.QR}</MenuItem>
                                    <MenuItem value={QR_TYPE.CODE_128}>{QR_TYPE.CODE_128}</MenuItem>
                                    <MenuItem value={QR_TYPE.PDF_417}>{QR_TYPE.PDF_417}</MenuItem>
                                    <MenuItem value={QR_TYPE.AZTEC}>{QR_TYPE.AZTEC}</MenuItem>
                                </Select>
                                <FormHelperText>하단 또는 애플워치에서 보여질 바코드 유형입니다.</FormHelperText>
                            </FormControl>
                        </ListItem>
                    </List>
                </DialogContent>
            </Dialog>
        </Fragment>
    );
}, (prevProps, nextProps) => {
    return isEqual(prevProps, nextProps);
});

export default withWidth()(Detail);
