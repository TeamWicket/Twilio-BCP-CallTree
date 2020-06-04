import {makeStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import TrendingUpIcon from '@material-ui/icons/TrendingUp';
import React, {FC} from 'react';
import Card from '@material-ui/core/Card';
import CardIcon from "./CardIcon";

const HighLevel: FC<Props> = ({serverStats}) => {
    const classes = useStyles();
    return (
        <div className={classes.main}>
            <CardIcon Icon={TrendingUpIcon} bgColor="#32a852"/>
            <Card className={classes.card}>
                <Typography className={classes.title} color="textSecondary">
                    {"Latest event"}
                    <br/>
                    <br/>
                </Typography>
                <Typography className={classes.paragraph} component="h2">
                    {"Name: " + serverStats?.eventName}
                </Typography>
                <Typography className={classes.paragraph} component="h2">
                    {"Date: " + serverStats?.eventDate}
                </Typography>
                <Typography className={classes.paragraph} component="h2">
                    {"SMS sent " + serverStats?.messagesSent + ", received " + serverStats?.messagesReceived}
                </Typography>
            </Card>
        </div>
    );
};

interface Props {
    serverStats?: {
        totalAverage: string,
        messagesSent: number,
        messagesReceived: number,
        replyPercentageWithinXMinutes: number,
        eventDate: string,
        eventName: string
    };
}

const useStyles = makeStyles({
    main: {
        flex: '1',
        marginRight: '1rem',
        marginTop: 20,
        minWidth: '30%'
    },
    card: {
        overflow: 'inherit',
        textAlign: 'right',
        padding: 16,
        minHeight: 140
    },
    title: {
        fontWeight: 'bolder',
    },
    paragraph: {
        // fontWeight: 'bold',
        textAlign: 'right',
        fontSize: 'large',
    }
});


export default HighLevel;
