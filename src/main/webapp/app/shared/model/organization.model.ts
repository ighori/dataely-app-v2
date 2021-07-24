import { IUser } from '@/shared/model/user.model';
import { IBusinessUnit } from '@/shared/model/business-unit.model';

export interface IOrganization {
  id?: number;
  name?: string;
  detail?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  user?: IUser;
  businessUnits?: IBusinessUnit[] | null;
}

export class Organization implements IOrganization {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public user?: IUser,
    public businessUnits?: IBusinessUnit[] | null
  ) {}
}
