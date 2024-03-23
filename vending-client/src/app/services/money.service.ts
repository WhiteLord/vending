import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MoneyService {
  private overallSum: number = 0;
  private overallSumSubject: Subject<number> = new Subject<number>();
  shouldRefetchSessionCoins: Subject<boolean> = new Subject<boolean>();
  private endpoint = environment.apiEndpoint;

  constructor(private httpClient: HttpClient) { }

  getOverallSum(): number {
    return this.overallSum;
  }

  getOverallSumObservable(): Observable<any> {
     return this.httpClient.get(this.endpoint + '/payment/getSessionCoins');
  }

  addToOverallSum(amount: number) {
    if (amount === 999) {
      const result = window.confirm("Are you sure you want to cash out?");
      if (result) {
        this.httpClient.post(this.endpoint + '/payment/refund', {}).subscribe(value => {
          this.shouldRefetchSessionCoins.next(true);
          console.log(value);
        });
        return;
      } else {
        // The user needs a few more hours to decide...
        return;
      }
    }
    this.overallSum += amount;
    const coinType = this.getCoinType(amount);
    this.postCoin(coinType).subscribe((result: any) => {
      if (result && result.length > 0) {
        this.overallSumSubject.next(this.getSumFromItems(result));
      } else {
        this.overallSumSubject.next(0);
      }
      this.shouldRefetchSessionCoins.next(true);
    });
  }

  private postCoin(coin: string): any {
    return this.httpClient.post(this.endpoint + '/payment/add', { coin });
  }

  private getCoinType(amount: number): string {
    switch (amount) {
      case 10: return 'TENNER';
      case 20: return 'TWENNY';
      case 50: return 'FITTY';
      case 100: return 'LEV';
      case 200: return 'DVA';
      default: return '';
    }
  }

  public getSumFromItems(items: string[]): number {
    let sum = 0;
    items.forEach(item => {
      switch (item.toUpperCase()) {
        case 'TENNER': sum += 10; break;
        case 'TWENNY': sum += 20; break;
        case 'FITTY': sum += 50; break;
        case 'LEV': sum += 100; break;
        case 'DVA': sum += 200; break;
        default: break;
      }
    });
    return sum;
  }
}
