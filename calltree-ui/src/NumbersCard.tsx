import React, { FC } from 'react';
import Card from '@material-ui/core/Card';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import DialpadIcon from '@material-ui/icons/Dialpad';

import CardIcon from './CardIcon';

interface Props {
    value?: number;
}

const useStyles = makeStyles({
    main: {
        flex: '1',
        marginLeft: '1em',
        marginTop: 20,
    },
    card: {
        overflow: 'inherit',
        textAlign: 'right',
        padding: 16,
        minHeight: 52,
    },
    title: {},
});

const NumbersCard: FC<Props> = ({ value }) => {
    const classes = useStyles();
    return (
        <div className={classes.main}>
            <CardIcon Icon={DialpadIcon} bgColor="#31708f" />
            <Card className={classes.card}>
                <Typography className={classes.title} color="textSecondary">
                    {"Total Twilio Numbers"}
                </Typography>
                <Typography variant="h5" component="h2">
                    {value}
                </Typography>
            </Card>
        </div>
    );
};

export default NumbersCard;