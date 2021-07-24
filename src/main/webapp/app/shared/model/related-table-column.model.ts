import { IRelatedTable } from '@/shared/model/related-table.model';

export interface IRelatedTableColumn {
  id?: number;
  columnName?: string;
  columnType?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  relatedTable?: IRelatedTable | null;
}

export class RelatedTableColumn implements IRelatedTableColumn {
  constructor(
    public id?: number,
    public columnName?: string,
    public columnType?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public relatedTable?: IRelatedTable | null
  ) {}
}
