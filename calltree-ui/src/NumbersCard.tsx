import React, { FC } from 'react';
import Card from '@material-ui/core/Card';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import DialpadIcon from '@material-ui/icons/Dialpad';

import CardIcon from './CardIcon';

interface Props {
    serverStats?: {
        twilioNumbers: number
    };
}

const useStyles = makeStyles({
    main: {
        flex: '1',
        marginTop: 20,
        minWidth: '30%',
        marginRight: '1rem',
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

const NumbersCard: FC<Props> = ({ serverStats }) => {
    const classes = useStyles();
    return (
        <div className={classes.main}>
            <CardIcon Icon={DialpadIcon} bgColor="#114cf0" />
            <Card className={classes.card}>
                <Typography className={classes.title} color="textSecondary">
                    {"Services numbers"}
                </Typography>
                <br/>
                <br/>
                <br/>
                <Typography className={classes.paragraph} component="h2">
                    {serverStats?.twilioNumbers}
                </Typography>
            </Card>
        </div>
    );
};

export default NumbersCard;
