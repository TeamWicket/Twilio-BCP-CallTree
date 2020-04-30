import React, { FC } from 'react';
import Card from '@material-ui/core/Card';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import PeopleAltIcon from '@material-ui/icons/PeopleAlt';

import CardIcon from './CardIcon';

interface Props {
    value?: number;
}

const useStyles = makeStyles({
    main: {
        flex: '1',
        marginLeft: '1em',
        marginRight: '1em',
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

const ContactsCard: FC<Props> = ({ value }) => {
    const classes = useStyles();
    return (
        <div className={classes.main} >
            <CardIcon Icon={PeopleAltIcon} bgColor="#ff9800" />
            <Card className={classes.card}>
                <Typography className={classes.title} color="textSecondary">
                    {"Total Contacts"}
                </Typography>
                <Typography variant="h5" component="h2">
                    {value}
                </Typography>
            </Card>
        </div>
    );
};

export default ContactsCard;
