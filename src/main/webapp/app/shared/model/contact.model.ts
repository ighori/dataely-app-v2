import { IEnvironment } from '@/shared/model/environment.model';

import { EContactType } from '@/shared/model/enumerations/e-contact-type.model';
export interface IContact {
  id?: number;
  firstName?: string;
  lastName?: string;
  name?: string;
  role?: EContactType;
  email?: string;
  phone?: string | null;
  extension?: string | null;
  mobile?: string | null;
  location?: string | null;
  addressLine1?: string | null;
  addressLine2?: string | null;
  city?: string | null;
  country?: string | null;
  imageContentType?: string | null;
  image?: string | null;
  environments?: IEnvironment[] | null;
}

export class Contact implements IContact {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public name?: string,
    public role?: EContactType,
    public email?: string,
    public phone?: string | null,
    public extension?: string | null,
    public mobile?: string | null,
    public location?: string | null,
    public addressLine1?: string | null,
    public addressLine2?: string | null,
    public city?: string | null,
    public country?: string | null,
    public imageContentType?: string | null,
    public image?: string | null,
    public environments?: IEnvironment[] | null
  ) {}
}
