import React from 'react';
import { List, Edit, Datagrid, 
    SimpleForm, BooleanField,
    TextInput, Create, NumberField,
    BooleanInput, required, regex } from 'react-admin';

const validateNumber = [required(),regex(/\+[0-9]/, 'Must be in format +9999999')];

export const NumberList = props => (
    <List {...props}>
        <Datagrid rowClick="edit">
            <NumberField source="twilioNumber" />
            <BooleanField source="isAvailable" />
        </Datagrid>
    </List>
);

export const NumberCreate = props => (
    <Create {...props}>
        <SimpleForm>
            <TextInput source="twilioNumber" validate={validateNumber}/>
            <BooleanInput source="isAvailable" />
        </SimpleForm>
    </Create>
);

export const NumberEdit = props => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput source="id" />
            <TextInput source="twilioNumber" validate={validateNumber}/>
            <BooleanInput source="isAvailable" />
        </SimpleForm>
    </Edit>
);