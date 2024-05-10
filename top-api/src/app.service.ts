import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';

@Injectable()
export class AppService {
  constructor(public readonly httpService: HttpService) {
  }
  getHello(): string {
    return 'Hello World!';
  }
}
