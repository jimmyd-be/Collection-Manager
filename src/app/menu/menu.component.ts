import { Component, OnInit } from '@angular/core';
import { faBars, faFolderPlus, faHome, faPlusCircle, faCogs, faPhone } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

 
  homeIcon = faHome;
  addCollectionIcon = faFolderPlus;
  addItemIcon = faPlusCircle;
  adminIcon = faCogs;
  contactIcon = faPhone;

  constructor() { }

  ngOnInit() {
  }

}
