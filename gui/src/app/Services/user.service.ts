import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../Entities/user';
import { Observable } from 'rxjs';
import { EditUser } from '../Entities/EditUser';
import { PasswordUser } from '../Entities/PasswordUser';
import { DeleteUser } from '../Entities/DeleteUser';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) { }

  getUser(): Observable<User> {
    return this.http.get<User>('/user');
  }

  editUser(user: EditUser): Observable<any> {
    return this.http.patch<EditUser>('/user/edit', user);
  }

  editPassword(password: PasswordUser): Observable<any> {
    return this.http.patch<PasswordUser>('/user/edit/password', password);
  }

  deleteUser(model: DeleteUser) {
    return this.http.request<DeleteUser>('delete', '/user/delete', {body: model});
  }


}
