import React, {
    memo,
    Fragment,
    useCallback,
    useState
} from 'react';
import './style.scss';
import { isEqual } from 'lodash';
import { useHistory } from "react-router-dom";
import {
    makeStyles,
    createStyles,
    Theme,
    Grid,
    Card,
    CardMedia,
    CardActions,
    CardContent,
    Typography,
    Button,
    Tooltip,
    Chip
} from '@material-ui/core';
import { Detail } from 'components'
import { CommonUtil } from 'utils';

const useStyles = makeStyles((theme: Theme) => 
    createStyles({
        passbook: {
            padding: theme.spacing(2)
        },
        tag: {
            margin: theme.spacing(0.5)
        }
    })
);

type PassbookProps = {
    info?: {
        id: string,
        name: string,
        desc?: string,
        thumbnail?: string,
        tags?: Array<string>
    }
};

const Passbook = memo((props: PassbookProps) => {
    const classes = useStyles();
    const history = useHistory();
    const [opener, setOpener] = useState(false);

    const onOpened = useCallback((): void => {
        // history.push(`${CommonUtil.getPresentLocationPath()}/${props.info?.id}`);
        setOpener(true);
    }, [history, props.info]);

    const onClosed = useCallback((): void => {
        setOpener(false);
    }, []);

    return (
        <Fragment>
            <Grid xs={12} sm={6} md={4} lg={3} xl={2} className={classes.passbook} item>
                <Card>
                    <CardMedia component="img" height="140" image={props.info?.thumbnail || 'https://via.placeholder.com/150'} />
                    <CardContent>
                        <Typography color="textSecondary" component="p" gutterBottom>Template</Typography>
                        {props.info?.tags && props.info?.tags.map((tag, index) => <Chip key={CommonUtil.generateKey(`${index}_${tag}`)} size="small" label={tag} className={classes.tag} />)}
                        <Typography variant="h5" component="h5">{props.info?.name}</Typography>
                        <Tooltip title={props.info?.desc || ''} arrow>
                            <Typography color="textSecondary" component="p" noWrap paragraph>{props.info?.desc || '-'}</Typography>
                        </Tooltip>
                    </CardContent>
                    <CardActions>
                        <Button size="small" onClick={() => { onOpened(); }}>LEARN MORE</Button>
                    </CardActions>
                </Card>
            </Grid>
            <Detail opener={opener} onClosed={onClosed} passbookId={props.info?.id}/>
        </Fragment>
    );
}, (prevProps, nextProps) => {
    return isEqual(prevProps.info, nextProps.info);
});

export default Passbook;
