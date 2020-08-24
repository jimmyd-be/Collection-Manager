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
    {
      title: 'Search',
      icon: 'search-outline',
      link: '/pages/item/addExternally',
    },
    ],
  },
  {
    title: 'Admin',
    icon: 'alert-circle-outline',
    children: [
      {
       title: 'Settings',
       icon: 'settings-outline',
       link: '/pages/admin/settings',
    },
    {
      title: 'Users',
      icon: 'people-outline',
      link: '/pages/admin/users',
    },
    ],
  },
  {
    title: 'Collections',
    group: true,
  },

];
