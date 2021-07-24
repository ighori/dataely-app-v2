import { IDsSchema } from '@/shared/model/ds-schema.model';

export interface IDsSchemaRelationship {
  id?: number;
  source?: string | null;
  target?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  dsSchema?: IDsSchema | null;
}

export class DsSchemaRelationship implements IDsSchemaRelationship {
  constructor(
    public id?: number,
    public source?: string | null,
    public target?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public dsSchema?: IDsSchema | null
  ) {}
}
