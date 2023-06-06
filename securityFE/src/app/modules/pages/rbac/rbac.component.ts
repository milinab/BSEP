import { Component, OnInit } from '@angular/core';
import { RolePermission } from '../../security/model/role-permission.model';
import { RolePermissionService } from '../../security/service/role-permission.service';

@Component({
  selector: 'app-rbac',
  templateUrl: './rbac.component.html',
  styleUrls: ['./rbac.component.css']
})
export class RbacComponent implements OnInit {

  rolePermissions: RolePermission[] = [];
  newRolePermission: RolePermission = {
    id: 0,
    role: { id: 0, name: '' },
    permission: { id: 0, name: '' }
  };
  showPopup: boolean = false;

  constructor(private rolePermissionService: RolePermissionService) { }

  ngOnInit() {
    this.getRolePermissions();
  }

  getRolePermissions() {
    this.rolePermissionService.getAllRolePermissions()
      .subscribe((rolePermissions: RolePermission[]) => {
        this.rolePermissions = rolePermissions;
      });
  }

  createRolePermission(newRolePermission: RolePermission) {
    // Assign the role and permission values manually
    newRolePermission.role = { id: 0, name: (document.getElementById('role') as HTMLInputElement).value };
    newRolePermission.permission = { id: 0, name: (document.getElementById('permission') as HTMLInputElement).value };

    this.rolePermissionService.createRolePermission(newRolePermission)
      .subscribe((createdRolePermission: RolePermission) => {
        // Add the created role permission to the array
        this.rolePermissions.push(createdRolePermission);
        // Reset the newRolePermission object
        this.newRolePermission = {
          id: 0,
          role: { id: 0, name: '' },
          permission: { id: 0, name: '' }
        };
        // Hide the popup
        this.showPopup = false;
      });
  }

  deleteRolePermission(id: number) {
    this.rolePermissionService.deleteRolePermission(id)
      .subscribe(() => {
        // Remove the deleted role permission from the array
        this.rolePermissions = this.rolePermissions.filter(rp => rp.id !== id);
      });
  }

  openPopup() {
    // Show the popup
    this.showPopup = true;
  }

  closePopup() {
    // Hide the popup
    this.showPopup = false;
  }

}
