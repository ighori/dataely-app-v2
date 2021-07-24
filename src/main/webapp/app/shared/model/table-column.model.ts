import { ITablesDefinition } from '@/shared/model/tables-definition.model';

export interface ITableColumn {
  id?: number;
  columnName?: string;
  columnType?: string | null;
  columnSize?: number | null;
  isNullable?: boolean | null;
  isPrimaryKey?: boolean | null;
  isForeignKey?: boolean | null;
  isIndexed?: boolean | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  tablesDefinition?: ITablesDefinition | null;
}

export class TableColumn implements ITableColumn {
  constructor(
    public id?: number,
    public columnName?: string,
    public columnType?: string | null,
    public columnSize?: number | null,
    public isNullable?: boolean | null,
    public isPrimaryKey?: boolean | null,
    public isForeignKey?: boolean | null,
    public isIndexed?: boolean | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public tablesDefinition?: ITablesDefinition | null
  ) {
    this.isNullable = this.isNullable ?? false;
    this.isPrimaryKey = this.isPrimaryKey ?? false;
    this.isForeignKey = this.isForeignKey ?? false;
    this.isIndexed = this.isIndexed ?? false;
  }
}
