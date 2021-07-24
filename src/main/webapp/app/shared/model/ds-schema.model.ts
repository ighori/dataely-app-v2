import { IDataSource } from '@/shared/model/data-source.model';
import { IDsSchemaRelationship } from '@/shared/model/ds-schema-relationship.model';
import { ITablesDefinition } from '@/shared/model/tables-definition.model';

export interface IDsSchema {
  id?: number;
  name?: string;
  detail?: string | null;
  tableCount?: number | null;
  tableRelCount?: number | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  dataSource?: IDataSource | null;
  dsSchemaRelationships?: IDsSchemaRelationship[] | null;
  tablesDefinitions?: ITablesDefinition[] | null;
}

export class DsSchema implements IDsSchema {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public tableCount?: number | null,
    public tableRelCount?: number | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public dataSource?: IDataSource | null,
    public dsSchemaRelationships?: IDsSchemaRelationship[] | null,
    public tablesDefinitions?: ITablesDefinition[] | null
  ) {}
}
