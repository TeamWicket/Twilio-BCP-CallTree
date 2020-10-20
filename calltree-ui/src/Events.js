import React from 'react';
import { List, Datagrid, TextField, 
    DateField, BooleanField,
    SimpleForm, ReferenceField,
    TextInput, SelectInput, Create,
    Toolbar, SaveButton, ReferenceInput} from 'react-admin';
import {Step, StepLabel, Button, Typography, Stepper} from '@material-ui/core/';


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
            <Breadcrumb {...props} />
        </SimpleForm>
    </Create>
)

const Breadcrumb = props => {
    const [activeStep, setActiveStep] = React.useState(0);
    const steps = getSteps();
  
    const handleNext = () => {
      setActiveStep((prevActiveStep) => prevActiveStep + 1);
    };
  
    const handleBack = () => {
      setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };
  
    const handleReset = () => {
      setActiveStep(0);
    };
  
    return (
      <div>
        <Stepper activeStep={activeStep} alternativeLabel>
          {steps.map((label) => (
            <Step key={label}>
              <StepLabel>{label}</StepLabel>
            </Step>
          ))}
        </Stepper>
        <div>
          {activeStep === steps.length ? (
            <div>
              <h1>All steps completed</h1>
              <Button onClick={handleReset}>Reset</Button>
            </div>
          ) : (
            <div>
              <Typography>{getStepContent(activeStep)}</Typography>
              <div>
                <Button
                  disabled={activeStep === 0}
                  onClick={handleBack}>
                  Back
                </Button>
                <Button variant="contained" color="primary" onClick={handleNext}>
                  {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
                </Button>
              </div>
            </div>
          )}
        </div>
      </div>
    );
  }
  

function getSteps() {
    return ['Define event title', 'Create message body', 'Select roles','Twilio number'];
}

function getStepContent(stepIndex) {
    switch (stepIndex) {
        case 0:
            return (
            <div>
                <h1>Select a title for the event</h1>
                <TextInput source="eventName"  />
            </div>)
        case 1:
            return (
                <div>
                    <h1>Message</h1>
                    <p>Enter the message you wish to send.</p>
                    <TextInput multiline source="text" />
                </div>)
        case 2:
            return (
                <div>
                    <h1>Select the roles you wish to send this message to.</h1>
                    <p>The message system is hierarchical<br />
                        To send to everyone select reporter and it will be propogated up the chain.<br />
                        If you wish for Leaders and everyone above then select this and so forth.<br />
                        The Champion will not recieve a message as they will kick off the event.
                    </p>
                    <SelectInput source="toRoles" choices={[
                        { id: 'MANAGER', name: 'Managers' },
                        { id: 'LEADER', name: 'Leaders' },
                        { id: 'REPORTER', name: 'Reporters'},
                    ]}  />
                </div>)
        case 3:
            return (
                <div>
                    <h1>Select a phone number</h1>
            <ReferenceInput 
                source="twilioNumberId" 
                reference="numbers" 
                filter={{ _getAvail: true}}> 
                    <SelectInput optionText="twilioNumber" />
            </ReferenceInput>
        </div>
            )
        default:
        return 'Unknown stepIndex';
    }
}