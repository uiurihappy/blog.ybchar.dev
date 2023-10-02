export interface Comments {
  id: number;
  password: string | null;
  username: string;
  secretPassword: number;
  content: string;
  createdAt: string | null;
  lastModifiedDate: string | null;
  createdBy: string | null;
  lastModifiedBy: string | null;
}
