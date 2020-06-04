import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardHeader from '@material-ui/core/CardHeader';

import ServerData from './serverData'

const styles = {
  flex: {display: 'flex'},
  flexColumn: {display: 'flex', flexDirection: 'row', flexWrap: 'wrap'},
  leftCol: {display: 'flex', marginRight: '1em', maxWidth: '100%'},
  rightCol: {flex: 1, marginLeft: '1em'},
  singleCol: {marginTop: '2em', marginBottom: '2em'},
};

export default () => (
  <React.Fragment>
    <Card>
      <CardHeader title="Welcome to the Twilio BCP Dashboard"/>
      <CardContent>In here you will be able to create SMS broadcast events, you can also manage your contacts and
        numbers as well</CardContent>
    </Card>
    <br/>
    <div style={styles.flexColumn}>
      <ServerData />
    </div>
  </React.Fragment>
);
