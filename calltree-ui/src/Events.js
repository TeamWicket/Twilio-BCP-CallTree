import React from 'react';
import { List, Datagrid, TextField, 
    DateField, BooleanField,
    SimpleForm, ReferenceField,
    TextInput, SelectInput, Create,
    Toolbar, SaveButton, ReferenceInput, } from 'react-admin';

const EventCreateToolbar = props => (
    <Toolbar {...props} >
        <SaveButton
            label="Start Event"
            redirect="list"
            submitOnEnter={true}
        />
    </Toolbar>
);

export const EventList = props => (
    <List {...props}>
        <Datagrid rowClick="edit">
            <TextField source="eventName" />
            <DateField source="timestamp" />
            <ReferenceField source="twilioNumber.id" label="Twilio Number" reference="numbers"><TextField source="twilioNumber" /></ReferenceField>
            <BooleanField source="isActive" />
        </Datagrid>
    </List>
);

export const EventCreate = props => (
    <Create {...props}>
        <SimpleForm toolbar={<EventCreateToolbar />} redirect ="list">
            <TextInput source="text" />
            <SelectInput source="toRoles" choices={[
                { id: 'MANAGER', name: 'Managers' },
                { id: 'LEADER', name: 'Leaders' },
                { id: 'REPORTER', name: 'Reporters'},
            ]}  />
            <TextInput source="eventName"  />
            <ReferenceInput
                source="twilioNumberId"
                reference="numbers"
                filter={{ _getAvail: true }}>
                <SelectInput optionText="twilioNumber" />
            </ReferenceInput>
        </SimpleForm>
    </Create>
);