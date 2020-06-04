import React, { FC } from 'react';
import Card from '@material-ui/core/Card';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import AttachMoneyIcon from '@material-ui/icons/AttachMoney';

import CardIcon from './CardIcon';

interface Props {
    serverStats?: {
        balance: number
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

const BalanceCard: FC<Props> = ({ serverStats }) => {
    const classes = useStyles();
    return (
        <div className={classes.main}>
            <CardIcon Icon={AttachMoneyIcon} bgColor="#f70b02" />
            <Card className={classes.card}>
                <Typography className={classes.title} color="textSecondary">
                    {"Account balance"}
                </Typography>
                <br/>
                <br/>
                <br/>
                <Typography className={classes.paragraph} component="h2">
                    {serverStats?.balance}
                </Typography>
            </Card>
        </div>
    );
};

export default BalanceCard;
