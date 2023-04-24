import type { Comments } from '../comments/comments.interface';
export interface PostList {
  list: Posts[];
  totalCount: number;
  totalElements: number;
}

export interface Posts {
  id: number;
  title: string;
  content: string;
  viewCount: number;
  likeCount: number;
  thumbnailImage: string;
  comments: Comments[];
  createdAt: string;
  lastModifiedDate: string;
}
