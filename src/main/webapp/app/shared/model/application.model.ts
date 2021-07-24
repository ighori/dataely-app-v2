import { IEnvironment } from '@/shared/model/environment.model';
import { IBusinessUnit } from '@/shared/model/business-unit.model';

import { EAppType } from '@/shared/model/enumerations/e-app-type.model';
export interface IApplication {
  id?: number;
  name?: string;
  detail?: string | null;
  version?: string | null;
  appType?: EAppType;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  environments?: IEnvironment[] | null;
  businessUnit?: IBusinessUnit | null;
}

export class Application implements IApplication {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public version?: string | null,
    public appType?: EAppType,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public environments?: IEnvironment[] | null,
    public businessUnit?: IBusinessUnit | null
  ) {}
}
