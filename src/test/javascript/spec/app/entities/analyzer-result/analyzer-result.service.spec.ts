/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import AnalyzerResultService from '@/entities/analyzer-result/analyzer-result.service';
import { AnalyzerResult } from '@/shared/model/analyzer-result.model';
import { EAnalyzerObjectType } from '@/shared/model/enumerations/e-analyzer-object-type.model';

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
  describe('AnalyzerResult Service', () => {
    let service: AnalyzerResultService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new AnalyzerResultService();
      currentDate = new Date();
      elemDefault = new AnalyzerResult(
        123,
        'AAAAAAA',
        'AAAAAAA',
        0,
        EAnalyzerObjectType.FILE,
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
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

      it('should create a AnalyzerResult', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            creationDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lastUpdated: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
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

      it('should not create a AnalyzerResult', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a AnalyzerResult', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            detail: 'BBBBBB',
            objectId: 1,
            objectType: 'BBBBBB',
            fieldId: 1,
            fieldType: 'BBBBBB',
            entityType: 'BBBBBB',
            start: 1,
            end: 1,
            score: 1,
            recognizer: 'BBBBBB',
            patternName: 'BBBBBB',
            patternExpr: 'BBBBBB',
            originalScore: 'BBBBBB',
            textualExplanation: 'BBBBBB',
            supportiveContextWord: 'BBBBBB',
            validationResult: 'BBBBBB',
            creationDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lastUpdated: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should not update a AnalyzerResult', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a AnalyzerResult', async () => {
        const patchObject = Object.assign(
          {
            detail: 'BBBBBB',
            objectType: 'BBBBBB',
            entityType: 'BBBBBB',
            start: 1,
            patternName: 'BBBBBB',
            patternExpr: 'BBBBBB',
            supportiveContextWord: 'BBBBBB',
            validationResult: 'BBBBBB',
            lastUpdated: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          new AnalyzerResult()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
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

      it('should not partial update a AnalyzerResult', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of AnalyzerResult', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            detail: 'BBBBBB',
            objectId: 1,
            objectType: 'BBBBBB',
            fieldId: 1,
            fieldType: 'BBBBBB',
            entityType: 'BBBBBB',
            start: 1,
            end: 1,
            score: 1,
            recognizer: 'BBBBBB',
            patternName: 'BBBBBB',
            patternExpr: 'BBBBBB',
            originalScore: 'BBBBBB',
            textualExplanation: 'BBBBBB',
            supportiveContextWord: 'BBBBBB',
            validationResult: 'BBBBBB',
            creationDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            lastUpdated: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
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

      it('should not return a list of AnalyzerResult', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a AnalyzerResult', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a AnalyzerResult', async () => {
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
