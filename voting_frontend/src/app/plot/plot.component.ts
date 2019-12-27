import { Component, Input, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts';

import { StatData } from '../shared/stat-data.model';

/*declare var require: any; // Custom themes
require('highcharts/themes/avocado')(Highcharts);*/

@Component({
  selector: 'app-plot',
  templateUrl: './plot.component.html',
  styleUrls: ['./plot.component.css']
})
export class PlotComponent implements OnInit {

  @Input() plotData: StatData;
  @Input() containerId: number;
  public highcharts = Highcharts;
  private plotOptions;

  constructor() {
  }

  ngOnInit() {
    this.plotOptions = {
      title: {
        text: this.plotData.title
      },
      series: []
    };
    switch (this.plotData.type) {
      case 'radio':
        this.plotOptions.chart = { type: 'column' };
        this.plotOptions.xAxis = {
          categories: ['']
        };
        this.plotOptions.yAxis = {
          min: 0,
          title: {
            text: 'Number of votes'
          },
          allowDecimals: false
        };
        Object.keys(this.plotData.stats).forEach(key => {
          this.plotOptions.series.push({ name: key,
                                data: [ this.plotData.stats[key] ]});
        });
        break;
      case 'select':
        this.plotOptions.chart = {
          plotBackgroundColor: null,
          plotBorderWidth: null,
          plotShadow: false,
          type: 'pie'
        };
        this.plotOptions.plotOptions = {
          pie: {
            allowPointSelect: true,
              cursor: 'pointer',
              dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %'
              }
            }
          };
        let pieSeries = {
          name: 'User votes',
          colorByPoint: true,
          data: []
        };
        let dataInfo;
        Object.keys(this.plotData.stats)
          .sort((a, b) => this.plotData.stats[b] - this.plotData.stats[a])
          .forEach((key, idx) => {
            dataInfo = {
              name: key,
              y: this.plotData.stats[key]
            };
            if (idx === 0) {
              dataInfo.sliced = true;
              dataInfo.selected = true;
            }
            pieSeries.data.push(dataInfo);
        });
        this.plotOptions.series.push(pieSeries);
        break;
    }
  }
}
