import React, { FC } from 'react';
import Card from '@material-ui/core/Card';
import EventIcon from '@material-ui/icons/Event';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';

import CardIcon from './CardIcon';

interface Props {
    serverStats?: {
        activeEvents: number,
        terminatedEvents: number
    };
}

const useStyles = makeStyles({
    main: {
        flex: '1',
        marginTop: 20,
        marginBottom: 20,
        minWidth: '30%'
    },
    card: {
        overflow: 'inherit',
        textAlign: 'right',
        padding: 16,
        minHeight: 140
    },
    title: {
        fontWeight: 'bold',
    },
    paragraph: {
        // fontWeight: 'bold',
        textAlign: 'right',
        fontSize: 'large',
    }
});

const EventsCard: FC<Props> = ({ serverStats }) => {
    const classes = useStyles();
    return (
        <div className={classes.main}>
            <CardIcon Icon={EventIcon} bgColor="#ff9800" />
            <Card className={classes.card}>
                <Typography className={classes.title} color="textSecondary">
                    {"Total Active Events"}
                </Typography>
                <br/>
                <br/>
                <br/>
                <Typography className={classes.paragraph} component="h2">
                    {"Active events: " + serverStats?.activeEvents}
                </Typography>
                <Typography className={classes.paragraph} component="h2">
                    {"Terminated events: " + serverStats?.terminatedEvents}
                </Typography>
            </Card>
        </div>
    );
};

export default EventsCard;
