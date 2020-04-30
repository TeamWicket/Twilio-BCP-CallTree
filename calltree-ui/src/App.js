import React from 'react';
import { Admin, Resource} from 'react-admin';
import './App.css';
import Dashboard from './Dashboard';
import AuthProvider from './AuthProvider';
import { ContactList, ContactEdit, ContactCreate } from './Contacts';
import { NumberList, NumberCreate } from './Numbers';
import customRoutes from './routes';
import myDataProvider from './dataProvider';
import themeReducer from './themeReducer';
import { Layout } from './layout';
import { EventList, EventCreate } from './Events';

const App = () => (
    <Admin 
        dashboard={Dashboard} 
        layout={Layout}
        authProvider={AuthProvider} 
        dataProvider={myDataProvider} 
        customReducers={{ theme: themeReducer }}
        customRoutes={customRoutes}>
        <Resource name="contacts" list={ContactList} edit={ContactEdit} create={ContactCreate}/>
        <Resource name="numbers" list={NumberList} create={NumberCreate}/>
        <Resource name="events" list={EventList} create={EventCreate}/>
    </Admin>
);

export default App;
