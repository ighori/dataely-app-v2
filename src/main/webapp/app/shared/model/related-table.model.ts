import { ITablesDefinition } from '@/shared/model/tables-definition.model';

export interface IRelatedTable {
  id?: number;
  tableName?: string;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  tablesDefinition?: ITablesDefinition | null;
}

export class RelatedTable implements IRelatedTable {
  constructor(
    public id?: number,
    public tableName?: string,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public tablesDefinition?: ITablesDefinition | null
  ) {}
}
