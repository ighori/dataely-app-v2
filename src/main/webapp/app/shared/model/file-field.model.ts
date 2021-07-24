import { IFileInfo } from '@/shared/model/file-info.model';

export interface IFileField {
  id?: number;
  fieldName?: string;
  fieldType?: string | null;
  fieldSize?: number | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  fileInfo?: IFileInfo | null;
}

export class FileField implements IFileField {
  constructor(
    public id?: number,
    public fieldName?: string,
    public fieldType?: string | null,
    public fieldSize?: number | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public fileInfo?: IFileInfo | null
  ) {}
}
