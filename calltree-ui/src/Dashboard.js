import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardHeader from '@material-ui/core/CardHeader';

import EventsCard from './EventsCard';
import ContactsCard from './ContactsCard';
import NumbersCard from './NumbersCard';

const styles = {
    flex: { display: 'flex' },
    flexColumn: { display: 'flex', flexDirection: 'column' },
    leftCol: { flex: 1, marginRight: '1em' },
    rightCol: { flex: 1, marginLeft: '1em' },
    singleCol: { marginTop: '2em', marginBottom: '2em' },
};


export default () => (
    <React.Fragment>
    <Card>
        <CardHeader title="Welcome to the Twilio BCP Dashboard" />
        <CardContent>In here you will be able to create SMS broadcast events, you can also manage your contacts and numbers as well</CardContent>
    </Card>
    <br></br>
    <div style={styles.flex}>
        <EventsCard value={2} />
        <ContactsCard value={7} />
        <NumbersCard value={2} />
    </div>
    </React.Fragment>
);