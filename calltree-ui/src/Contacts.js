import React from 'react';
import { List, Edit, Datagrid, TextField, 
    SimpleForm, ReferenceField, required,
    TextInput, SelectInput, Create,
    regex, ReferenceInput } from 'react-admin';


const validateNumber = [required(),regex(/\+[0-9]/, 'Must be in format +9999999')];

export const FullNameField = ({ record = {} }) => <span>{record.firstName} {record.lastName}</span>;
FullNameField.defaultProps = { label: 'Name' };

export const ContactList = props => (
    <List {...props}>
        <Datagrid rowClick="edit">
            <TextField source="firstName" />
            <TextField source="lastName" />
            <TextField source="phoneNumber" />
            <TextField source="role" />
            <ReferenceField source="pointOfContactId" reference="contacts"><FullNameField source="firstName" /></ReferenceField>
        </Datagrid>
    </List>
);

export const ContactEdit = props => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput source="firstName" />
            <TextInput source="lastName" />
            <TextInput source="phoneNumber" validate={validateNumber} />
            <SelectInput source="role" choices={[
                { id: 'CHAMPION', name: 'Champion' },
                { id: 'MANAGER', name: 'Manager' },
                { id: 'LEADER', name: 'Leader' },
                { id: 'REPORTER', name: 'Reporter'},
            ]}/>
            <ReferenceInput
                source="pointOfContactId"
                reference="contacts">
                <SelectInput optionText="firstName"/>
            </ReferenceInput>
        </SimpleForm>
    </Edit>
);

export const ContactCreate = props => (
    <Create {...props}>
        <SimpleForm redirect ="list">
            <TextInput source="firstName" validate={[required()]} />
            <TextInput source="lastName"  validate={[required()]} />
            <TextInput source="phoneNumber" validate={validateNumber} />
            <SelectInput source="role" choices={[
                { id: 'CHAMPION', name: 'Champion' },
                { id: 'MANAGER', name: 'Manager' },
                { id: 'LEADER', name: 'Leader' },
                { id: 'REPORTER', name: 'Reporter'},
            ]} validate={[required()]} />
            <ReferenceInput
                source="pointOfContactId"
                reference="contacts">
                <SelectInput optionText="firstName"/>
            </ReferenceInput>
        </SimpleForm>
    </Create>
);