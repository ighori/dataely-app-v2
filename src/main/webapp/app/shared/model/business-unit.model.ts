import { IApplication } from '@/shared/model/application.model';
import { IOrganization } from '@/shared/model/organization.model';

export interface IBusinessUnit {
  id?: number;
  name?: string;
  detail?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  applications?: IApplication[] | null;
  organization?: IOrganization | null;
}

export class BusinessUnit implements IBusinessUnit {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public applications?: IApplication[] | null,
    public organization?: IOrganization | null
  ) {}
}
