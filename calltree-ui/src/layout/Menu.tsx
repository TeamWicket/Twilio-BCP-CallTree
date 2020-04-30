import React, { FC, useState } from 'react';
import { useSelector } from 'react-redux';
import { useMediaQuery, Theme } from '@material-ui/core';
import { DashboardMenuItem, MenuItemLink } from 'react-admin';
import UserIcon from '@material-ui/icons/Group';
import DialpadIcon from '@material-ui/icons/Dialpad';
import EventIcon from '@material-ui/icons/Event';

import SubMenu from './submenu';
import { AppState } from '../types';

type MenuName = 'menuContacts' | 'menuNumbers';

interface Props {
    dense: boolean;
    logout: () => void;
    onMenuClick: () => void;
}

const Menu: FC<Props> = ({ onMenuClick, dense, logout }) => {
    const [state, setState] = useState({
        menuContacts: false,
        menuNumbers: false,
    });
    const isXSmall = useMediaQuery((theme: Theme) =>
        theme.breakpoints.down('xs')
    );
    const open = useSelector((state: AppState) => state.admin.ui.sidebarOpen);
    useSelector((state: AppState) => state.theme);

    const handleToggle = (menu: MenuName) => {
        setState(state => ({ ...state, [menu]: !state[menu] }));
    };

    return (
        <div>
            {' '}
            <DashboardMenuItem onClick={onMenuClick} sidebarIsOpen={open} />
            <MenuItemLink
                to={`/events`}
                primaryText={'Events'}
                leftIcon={<EventIcon />}
                onClick={onMenuClick}
                sidebarIsOpen={open}
                dense={dense}
            />
            <MenuItemLink
                to={`/contacts`}
                primaryText={'Contacts'}
                leftIcon={<UserIcon />}
                onClick={onMenuClick}
                sidebarIsOpen={open}
                dense={dense}
            />
            <MenuItemLink
                to={`/numbers`}
                primaryText={'Numbers'}
                leftIcon={<DialpadIcon />}
                onClick={onMenuClick}
                sidebarIsOpen={open}
                dense={dense}
            />
        </div>
    );
};

export default Menu;