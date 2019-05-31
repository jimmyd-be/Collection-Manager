import { Component, OnInit } from '@angular/core';
import { Collection } from '../../Entities/collection';
import { CollectionService } from '../../Services/collection.service';
import { faTrash, faEdit, faEye } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';


@Component({
  selector: 'ngx-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  removeIcon = faTrash;
  editIcon = faEdit;
  viewIcon = faEye;

  collections: Collection[];

  constructor(private collectionService: CollectionService, private router: Router) { }

  ngOnInit() {
    this.loadCollections();
  }

  loadCollections()
  {
    this.collectionService.getUserCollections().subscribe(data => {

      this.collections = data;
    });
  }

  editCollection(id: number)
  {
    this.router.navigate(['/pages/collection/edit', id]);

  }

  deleteCollection(id: number)
  { 
    this.collectionService.deleteCollection(id).subscribe(data => {
      this.router.navigate(['/pages/dashboard']);      

      this.loadCollections();
    });
  }

  viewCollection(id: Number)
  {
    this.router.navigate(['/pages/collection/view', id]);
  }
}
