import {Component, OnInit} from '@angular/core';
import {Good} from "./model/goods-model";
import {GoodsService} from "./service/goods.service";
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'goods',
  templateUrl: './goods.component.html'
})
export class GoodsComponent implements OnInit {

  goods: Good[] = [];
  selectedGood: Good | any;
  goodDialog: Boolean = false;
  selectedGoods: Good[] = [];

  constructor(private goodsService: GoodsService, private confirmationService: ConfirmationService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.goodsService.getAllGoods()
      .subscribe(goods => this.goods = goods);
  }

  createNew() {
      this.selectedGood = {};
      this.goodDialog = true;
  }

  deleteSelectedGoods() {

    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected products?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedGoods.map(g => this.goodsService.deleteGood(g.id).subscribe(
          () => this.goodsService.getAllGoods().subscribe((goods) => {
            this.goods = goods;
          })
        ));
        this.selectedGoods = [];
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Products Deleted',
          life: 3000
        });
      }
    });
  }

  public editGood(good: Good) {
    this.selectedGood = good;
    this.goodDialog = true;
  }

  public deleteGood(good: Good) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + good.name + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.goodsService.deleteGood(good.id).subscribe(() => {
          this.messageService.add({severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000});
          this.goodsService.getAllGoods().subscribe((goods) => {
            this.goods = goods;
          });
        });
      }
    });
  }

  hideModal() {
    this.goodDialog = false;
    this.goodsService.getAllGoods().subscribe((goods) => {
      this.goods = goods;
    });
  }
}

