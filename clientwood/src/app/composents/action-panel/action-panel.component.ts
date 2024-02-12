import {Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation,} from '@angular/core';

@Component({
  selector: 'app-action-panel',
  templateUrl: './action-panel.component.html',
  styleUrls: ['./action-panel.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class ActionPanelComponent implements OnInit {
  @Input() public isDisabled?: boolean;
  @Input() public isDisabledCreate?: boolean;
  @Input() public signEnabled?: boolean;

  @Output() public createEvent = new EventEmitter<string>();
  @Output() public deleteEvent = new EventEmitter<string>();
  @Output() public signEvent = new EventEmitter<string>();
  @Output() public revisionEvent = new EventEmitter<string>();

  constructor() {
  }

  public ngOnInit() {
  }

  public onCreateClick(event: any) {
    if (event instanceof MouseEvent && event.type === 'click') {
      this.createEvent.emit('create');
    }
  }

  public onDeleteClick(event: any) {
    if (event instanceof MouseEvent && event.type === 'click') {
      this.deleteEvent.emit('update');
    }
  }

  public onSingClick(event: any) {
    if (event instanceof MouseEvent && event.type === 'click') {
      this.signEvent.emit('sign');
    }
  }

  public onRevisionClick(event: any) {
    if (event instanceof MouseEvent && event.type === 'click') {
      this.revisionEvent.emit('revision');
    }
  }
}
