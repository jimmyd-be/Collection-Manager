import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'Dashboard',
    icon: 'home-outline',
    link: '/pages/dashboard',
    home: true,
  },
  {
    title: 'Add Collection',
    icon: 'plus-outline',
    link: '/pages/collection/add',
  },
  {
    title: 'Add items',
    icon: 'plus-circle-outline',
    children: [
      {
       title: 'Manual',
       icon: 'edit-outline',
       link: '/pages/item/addManual',
    },
    ],
  },
  {
    title: 'Collections',
    group: true,
  },

];
