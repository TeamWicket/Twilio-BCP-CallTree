import React, { FC } from 'react';
import Card from '@material-ui/core/Card';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import DialpadIcon from '@material-ui/icons/Dialpad';

import CardIcon from './CardIcon';

interface Props {
    serverStats?: {
        status: number
    };
}

const useStyles = makeStyles({
    main: {
        flex: '1',
        marginTop: 20,
        minWidth: '30%',
        // marginLeft: '5%'
    },
    card: {
        overflow: 'inherit',
        textAlign: 'right',
        padding: 16,
        minHeight: 140,
    },
    title: {
        fontWeight: 'bold',
    },
    paragraph: {
        // fontWeight: 'bold',
        textAlign: 'right',
        fontSize: 'x-large',
    }
});

const StatusCard: FC<Props> = ({ serverStats }) => {
    const classes = useStyles();
    return (
        <div className={classes.main}>
            <CardIcon Icon={DialpadIcon} bgColor="#20bab5" />
            <Card className={classes.card}>
                <Typography className={classes.title} color="textSecondary">
                    {"Service status"}
                </Typography>
                <br/>
                <br/>
                <br/>
                <Typography className={classes.paragraph} component="h2">
                    {serverStats?.status}
                </Typography>
            </Card>
        </div>
    );
};

export default StatusCard;
