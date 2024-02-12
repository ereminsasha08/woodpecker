import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Good} from "./model/goods-model";
import {GoodsService} from "./service/goods.service";
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'goods-modal',
  templateUrl: './goods-modal.component.html'
})
export class GoodsModalComponent implements OnInit {
  @Input()
  good: Good | any;
  @Input()
  goodDisplay: Boolean = false;
  @Output()
  closeEmitter = new EventEmitter();
  submitted: boolean = false;
  constructor(private goodsService: GoodsService, private confirmationService: ConfirmationService, private messageService: MessageService,) {
  }

  showDialog() {
    this.goodDisplay = true;
  }

  ngOnInit(): void {
  }

  openNew() {
    this.good = null;
    this.submitted = false;
    this.goodDisplay = true;
  }

  hideDialog() {
    this.goodDisplay = false;
    this.closeEmitter.emit();
  }

  saveGood() {
    if (this.good.name.trim()) {
      if (this.good.id) {
        this.goodsService.updateGood(this.good.id, this.good).subscribe(() => {
          this.messageService.add({severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000});
        })
      } else {
        this.goodsService.createGood(this.good).subscribe(() => {
          this.messageService.add({severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000});
        });
      }
    }
    this.hideDialog();
  }
}
