import {Component, OnInit} from '@angular/core';
import {EditUser} from '../../Entities/EditUser';
import {UserService} from '../../Services/user.service';
import {Router} from '@angular/router';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss'],
})
export class EditUserComponent implements OnInit {

  model: EditUser = new EditUser('', '', '', 'default');
  themes = [
    {
      value: 'default',
      name: 'Default',
    },
    {
      value: 'dark',
      name: 'Dark',
    },
  ];
  currentTheme = 'default';
  private destroy$: Subject<void> = new Subject<void>();

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit() {
    //this.currentTheme = this.themeService.currentTheme;

    this.userService.getUser().subscribe(data => {
      this.model = new EditUser('', data.username, data.mail, data.theme);
      this.currentTheme = data.theme == null ? 'default' : data.theme;
    });

    /*this.themeService.onThemeChange()
      .pipe(
        map(({name}) => name),
        takeUntil(this.destroy$),
      )
      .subscribe(themeName => this.currentTheme = themeName);*/
  }

  changeTheme(themeName: string) {
    //this.themeService.changeTheme(themeName);
  }

  onSubmit() {

    this.userService.editUser(this.model).subscribe(data => {
        this.router.navigate(['/pages/profile']);
      },
      error => {
        this.model.password = '';
      });

  }

}
