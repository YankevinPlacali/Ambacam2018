import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges} from '@angular/core';
import {Page} from '../../admin/models/page';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit, OnChanges {

  public pageSet: Page[];
  private _page;
  private _totalPages;
  public offset = 0;
  @Output()
  selectedPage = new EventEmitter<number>();

  constructor() {
  }

  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.totalPages) {
      const totalPages: SimpleChange = changes.totalPages;
      this._totalPages = totalPages.currentValue;
    }

    if (changes.page) {
      const page: SimpleChange = changes.page;
      this._page = page.currentValue;
    }

    this.getPageSet(this._page, this._totalPages, this.offset);
  }

  get page(): number {
    return this._page;
  }

  @Input()
  set page(page: number) {
    this._page = page;
  }

  get totalPages(): number {
    return this._totalPages;
  }

  @Input()
  set totalPages(totalPages) {
    this._totalPages = totalPages;
  }


  getPageSet(page: number, totalPages: number, offset: number) {

    this.pageSet = [];

    if (totalPages > 0) {
      let label;

      this.pageSet.push(new Page('Page 1', 0, false, false));
      if (page - 1 >= 0) {
        this.pageSet.push(new Page('Precedent', page - 1, false, false));
      } else {
        this.pageSet.push(new Page('Precedent', null, null, true));
      }

      for (let i = offset; i > 0; i--) {
        label = page - i;
        if (label >= 0) {
          this.pageSet.push(new Page((label + 1).toString(), label, false, false));
        }
      }

      this.pageSet.push(new Page((page + 1).toString(), page, true, false));
      for (let i = 1; i <= offset; i++) {
        label = page + i;
        if (label < totalPages) {
          this.pageSet.push(new Page((label + 1).toString(), label, false, false));
        }
      }

      if (page + 1 < totalPages) {
        this.pageSet.push(new Page('Suivant', page + 1, false, false));
      } else {
        this.pageSet.push(new Page('Suivant', null, null, true));
      }
      this.pageSet.push(new Page('Page ' + totalPages, totalPages - 1, false, false));
    }
  }

  search(page: number) {
    this.selectedPage.emit(page);
  }

}
