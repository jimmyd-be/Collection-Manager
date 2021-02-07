import {Component, OnDestroy, OnInit} from '@angular/core';
import {NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService} from '@nebular/theme';

import {UserService} from '../../../Services/user.service';
import {User} from '../../../Entities/user';
import {map, takeUntil} from 'rxjs/operators';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  userPictureOnly = false;
  user: User;
  userMenu = [{title: 'Profile', link: '/pages/profile'}, {title: 'Log out', link: '/auth/logout'}];
  private destroy$: Subject<void> = new Subject<void>();

  constructor(private sidebarService: NbSidebarService,
              private menuService: NbMenuService,
              private userService: UserService,
              private breakpointService: NbMediaBreakpointsService,
              private themeService: NbThemeService) {
  }

  ngOnInit() {
    this.userService.getUser()
      .subscribe((user: User) => {
        this.user = user;
        this.themeService.changeTheme(user.theme);
      });

    const {xl} = this.breakpointService.getBreakpointsMap();
    this.themeService.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');

    return false;
  }

  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }
}
