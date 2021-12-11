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


  enableUser(userId: number): Observable<any> {
    return this.http.patch<object>('/admin/user/enable/' + userId, null);
  }

  disableUser(userId: number): Observable<any> {
    return this.http.patch<object>('/admin/user/disable/' + userId, null);
  }

  setAdmin(userId: number, admin: boolean): Observable<any> {
    if (!admin) {
      return this.http.patch('/admin/user/remove/admin/' + userId, null);
    }
    return this.http.patch<object>('/admin/user/set/admin/' + userId, null);
  }

  deleteUserOnId(userId: number): Observable<any> {
    return this.http.delete<object>('/admin/user/' + userId);
  }
}
