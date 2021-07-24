import { ITablesDefinition } from '@/shared/model/tables-definition.model';

export interface ITableRelationship {
  id?: number;
  source?: string | null;
  target?: string | null;
  sourceKey?: string | null;
  targetKey?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  tablesDefinition?: ITablesDefinition | null;
}

export class TableRelationship implements ITableRelationship {
  constructor(
    public id?: number,
    public source?: string | null,
    public target?: string | null,
    public sourceKey?: string | null,
    public targetKey?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public tablesDefinition?: ITablesDefinition | null
  ) {}
}
