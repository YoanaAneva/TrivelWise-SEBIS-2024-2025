export class Category {
  id?: number;
  name: string;
  description: string;
  icon: string;
  departmentId: number;

  constructor(name: string, description: string, icon: string, departmentId: number) {
    this.name = name;
    this.description = description;
    this.icon = icon;
    this.departmentId = departmentId;
  }
}
