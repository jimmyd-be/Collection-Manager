import { Component } from '@angular/core';
import { faBars } from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'gui';
  hamburgerIcon = faBars;

  showMenu: boolean = true;


  collapseMenuHandler(){
    this.showMenu = !this.showMenu;
    }
}
