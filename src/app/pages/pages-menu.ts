import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'Dashboard',
    icon: 'nb-home',
    link: '/pages/dashboard',
    home: true,
  },{
    title: 'Add Collection',
    icon: 'nb-plus',
    link: '/pages/collection/add',
  },
  {
    title: 'Add items',
    icon: 'nb-plus-circled',
    children: [
     { 
       title: 'Manual',
       icon: 'nb-compose',
       link: '/pages/item/addManual'
    }
    ]
  },
  {
    title: 'FEATURES',
    group: true,
  },
  {
    title: 'Auth',
    icon: 'nb-locked',
    children: [
      {
        title: 'Login',
        link: '/auth/login',
      },
      {
        title: 'Register',
        link: '/auth/register',
      },
      {
        title: 'Request Password',
        link: '/auth/request-password',
      },
      {
        title: 'Reset Password',
        link: '/auth/reset-password',
      },
    ],
  },
];
