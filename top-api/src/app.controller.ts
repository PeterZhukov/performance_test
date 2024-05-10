import { Controller, Get, Param, Query } from '@nestjs/common';
import { AppService } from './app.service';
import { firstValueFrom } from 'rxjs';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getHello(): string {
    return this.appService.getHello();
  }

  @Get('/request/:id')
  async getRequest(@Param('id') id): Promise<any> {
    id = parseInt(id);
    if (id >= 10) {
      return {
        id: id,
      };
    }
    id = id + 1;
    const { data } = await firstValueFrom(
      this.appService.httpService.get<{ id: string }>(
        `http://localhost:3000/request/${id}`,
      ),
    );
    return data;
  }
}
