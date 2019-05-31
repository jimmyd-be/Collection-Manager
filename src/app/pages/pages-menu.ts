import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'Dashboard',
    icon: 'nb-home',
    link: '/pages/dashboard',
    home: true,
  },
  {
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
       link: '/pages/item/addManual',
    },
    ],
  },
  {
    title: 'Collections',
    group: true,
  },

];
