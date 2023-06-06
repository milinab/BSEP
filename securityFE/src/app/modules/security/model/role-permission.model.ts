export interface RolePermission {
  id: number;
  role: Role;
  permission: Permission;
}

export interface Role {
  id: number;
  name: string;
}

export interface Permission {
  id: number;
  name: string;
}
