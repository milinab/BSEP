import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RolePermission} from "../model/role-permission.model";

@Injectable({
  providedIn: 'root'
})
export class RolePermissionService {

  private apiUrl = 'https://localhost:8082/api/role-permissions';

  constructor(private http: HttpClient) { }

  getAllRolePermissions(): Observable<RolePermission[]> {
    return this.http.get<RolePermission[]>(this.apiUrl);
  }

  createRolePermission(rolePermission: RolePermission): Observable<RolePermission> {
    return this.http.post<RolePermission>(this.apiUrl, rolePermission);
  }

  deleteRolePermission(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
