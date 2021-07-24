/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import AnalyzerJobService from '@/entities/analyzer-job/analyzer-job.service';
import { AnalyzerJob } from '@/shared/model/analyzer-job.model';
import { EJobStatus } from '@/shared/model/enumerations/e-job-status.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('AnalyzerJob Service', () => {
    let service: AnalyzerJobService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new AnalyzerJobService();
      currentDate = new Date();
      elemDefault = new AnalyzerJob(
        123,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        EJobStatus.SUCCEEDED,
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            endTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            startTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            creationDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lastUpdated: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a AnalyzerJob', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            endTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            startTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            creationDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lastUpdated: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            endTime: currentDate,
            startTime: currentDate,
            creationDate: currentDate,
            lastUpdated: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a AnalyzerJob', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a AnalyzerJob', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            detail: 'BBBBBB',
            endTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            startTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            previousRunTime: 'BBBBBB',
            creationDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lastUpdated: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endTime: currentDate,
            startTime: currentDate,
            creationDate: currentDate,
            lastUpdated: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a AnalyzerJob', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a AnalyzerJob', async () => {
        const patchObject = Object.assign(
          {
            endTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            startTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            previousRunTime: 'BBBBBB',
            creationDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          new AnalyzerJob()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            endTime: currentDate,
            startTime: currentDate,
            creationDate: currentDate,
            lastUpdated: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a AnalyzerJob', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of AnalyzerJob', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            detail: 'BBBBBB',
            endTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            startTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            previousRunTime: 'BBBBBB',
            creationDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lastUpdated: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            endTime: currentDate,
            startTime: currentDate,
            creationDate: currentDate,
            lastUpdated: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of AnalyzerJob', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a AnalyzerJob', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a AnalyzerJob', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
