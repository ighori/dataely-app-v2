import { IDsSchema } from '@/shared/model/ds-schema.model';
import { ITableColumn } from '@/shared/model/table-column.model';

export interface ITablesDefinition {
  id?: number;
  tableName?: string;
  value?: number | null;
  symbolSize?: number | null;
  category?: number | null;
  colCnt?: number | null;
  colCntNbr?: number | null;
  colCntTB?: number | null;
  colCntSTR?: number | null;
  colCntBL?: number | null;
  colCntPK?: number | null;
  colCntFK?: number | null;
  colCntIX?: number | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  dsSchema?: IDsSchema | null;
  tableColumns?: ITableColumn[] | null;
}

export class TablesDefinition implements ITablesDefinition {
  constructor(
    public id?: number,
    public tableName?: string,
    public value?: number | null,
    public symbolSize?: number | null,
    public category?: number | null,
    public colCnt?: number | null,
    public colCntNbr?: number | null,
    public colCntTB?: number | null,
    public colCntSTR?: number | null,
    public colCntBL?: number | null,
    public colCntPK?: number | null,
    public colCntFK?: number | null,
    public colCntIX?: number | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public dsSchema?: IDsSchema | null,
    public tableColumns?: ITableColumn[] | null
  ) {}
}
